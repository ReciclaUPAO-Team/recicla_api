package com.upao.recicla.controller;

import com.upao.recicla.domain.dto.actividadDto.DatosActualizarActividad;
import com.upao.recicla.domain.dto.actividadDto.DatosDetallesActividad;
import com.upao.recicla.domain.dto.actividadDto.DatosListadoActividad;
import com.upao.recicla.domain.dto.actividadDto.DatosRespuestaActividad;
import com.upao.recicla.domain.entity.Actividad;
import com.upao.recicla.domain.entity.Residuo;
import com.upao.recicla.domain.entity.Usuario;
import com.upao.recicla.domain.service.ActividadService;
import com.upao.recicla.infra.repository.ResiduoRepository;
import com.upao.recicla.infra.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actividad")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ActividadController {

    @Autowired
    private final ActividadService actividadService;

    private static final List<String> FORMATOS_PERMITIDOS = Arrays.asList("image/png", "image/jpeg", "image/jpg");
    private static final long TAMANO_MAXIMO = 5 * 1024 * 1024; // 5MB

    public static String UPLOAD_DIRECTORY =
            System.getProperty("user.dir") + "/src/main/resources/static/actividades";

    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<Page<DatosListadoActividad>> getAllActividades(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(actividadService.getAllActividades(pageable).map(DatosListadoActividad::new));
    }

    @GetMapping("/historial")
    public ResponseEntity<List<DatosRespuestaActividad>> getHistorialPorUsuarioId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Actividad> actividades = actividadService.getActividadesPorUsuarioId(usuario.getId());
        if (actividades.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El historial de actividades está vacío. Empieza a reciclar para ganar puntos." );
        }
        List<DatosRespuestaActividad> datosActividades = actividades.stream().map(actividad ->
                new DatosRespuestaActividad(
                        actividad.getId(),
                        actividad.getNombre(),
                        actividad.getCantidad(),
                        actividad.getImagen(),
                        actividad.getFecha(),
                        actividad.getCantidad() * actividad.getResiduo().getPuntos(),
                        actividad.getResiduo().getId(),
                        actividad.getResiduo().getNombre(),
                        actividad.getResiduo().getDescripcion(),
                        actividad.getResiduo().getTipo(),
                        actividad.getResiduo().getPuntos(),
                        actividad.getUsuario().getId(),
                        actividad.getUsuario().getNombre(),
                        actividad.getUsuario().getPuntos())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(datosActividades);
    }

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity<DatosDetallesActividad> addActividad(@RequestParam("nombre") String nombre,
                                                               @RequestParam("cantidad") Double cantidad,
                                                               @RequestParam("nombreResiduo") String nombreResiduo,
                                                               @RequestParam("image") MultipartFile imagen) throws IOException {

        String imagenPath = guardarImagen(imagen);

        Actividad actividad = new Actividad();
        actividad.setNombre(nombre);
        actividad.setCantidad(cantidad);
        actividad.setImagen(imagenPath);

        actividadService.addActividad(actividad, nombreResiduo);

        DatosDetallesActividad datosDetallesActividad = new DatosDetallesActividad(actividad);
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        builder.path("/{id}").buildAndExpand(actividad.getId());
        return ResponseEntity.created(builder.build().toUri()).body(datosDetallesActividad);
    }

    private String guardarImagen(MultipartFile imagen) throws IOException {
        if (!imagen.isEmpty()) {
            if (!FORMATOS_PERMITIDOS.contains(imagen.getContentType())) {
                throw new IllegalArgumentException("Formato de archivo no permitido.");
            }
            if (imagen.getSize() > TAMANO_MAXIMO) {
                throw new IllegalArgumentException("El archivo excede el tamaño máximo permitido.");
            }
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String imagenNombre = UUID.randomUUID().toString() + "-" + imagen.getOriginalFilename();
            Path path = uploadPath.resolve(imagenNombre).normalize().toAbsolutePath();
            Files.copy(imagen.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return imagenNombre;
        }
        return null;
    }

    @GetMapping("/qr/{actividadId}")
    public ResponseEntity<byte[]> getActividadQRCode(@PathVariable Long actividadId) {
        try {
            byte[] qrCode = actividadService.generateActividadQRCode(actividadId);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> getEstadisticasUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Actividad> actividades = actividadService.getActividadesPorUsuarioId(usuario.getId());
        double totalPuntosActividades = actividades.stream()
                .mapToDouble(a -> a.getCantidad() * a.getResiduo().getPuntos())
                .sum();
        double totalPuntosUsuario = usuario.getPuntos();

        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalActividades", actividades.size());
        estadisticas.put("totalPuntos", totalPuntosActividades);
        estadisticas.put("totalPuntosUsuario", totalPuntosUsuario);

        return ResponseEntity.ok(estadisticas);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @PutMapping
    @Transactional
    public ResponseEntity updateActividad(@RequestBody @Valid DatosActualizarActividad datos) {
        var actividad = actividadService.getReferenceById(datos.id());
        actividad.actualizarActividad(datos);

        return ResponseEntity.ok(new DatosDetallesActividad(actividad));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteActividadById(@PathVariable Long id) {
        var actividad = actividadService.getReferenceById(id);
        actividadService.deleteActividadById(id);
        return ResponseEntity.noContent().build();
    }

}

