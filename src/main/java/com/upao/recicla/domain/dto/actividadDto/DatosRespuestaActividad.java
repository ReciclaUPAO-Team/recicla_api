package com.upao.recicla.domain.dto.actividadDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.upao.recicla.domain.entity.Actividad}
 */
public record DatosRespuestaActividad(Long id, String nombre, Double cantidad, String imagen,
                                      LocalDateTime fecha, Long residuo_id, String residuoNombre,
                                      String residuoDescripcion, String residuoTipo, Double residuoPuntos,
                                      Long usuario_id, String usuarioNombre, Double usuarioPuntos) implements Serializable {
}