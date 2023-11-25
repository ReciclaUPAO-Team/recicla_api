package com.upao.recicla.domain.entity;

import com.upao.recicla.domain.dto.usuarioDto.DatosActualizarUsuario;
import com.upao.recicla.domain.dto.usuarioDto.DatosRegistroUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@Entity(name = "Usuario")
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(length = 3)
    private String edad;
    @Column(unique = true, length = 9)
    private String telefono;
    @Column(unique = true, nullable = false, length = 100)
    @Email
    private String correo;
    @Column(nullable = false, length = 100)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(name = "puntos_acumulados", columnDefinition = "double default 0")
    private Double puntos;
    @Column(unique = true, nullable = false, length = 8)
    @Pattern(regexp = "\\d{8}")
    private String dni;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "comunidades_usuarios",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
    )
    private List<Comunidad> comunidades;

    public Usuario() {
        this.rol = Rol.PARTICIPANTE;
        this.puntos = (double) 0;
    }

    public Usuario(Long usuario_id){
        this.id = usuario_id;
    }

    public Usuario(DatosRegistroUsuario datos){
        this.nombre = datos.nombre();
        this.edad = datos.edad();
        this.telefono = datos.telefono();
        this.correo = datos.correo();
        this.dni = datos.dni();
        this.rol = datos.rol();
        this.puntos = (double) 0;
        this.username = datos.username();
        this.password = datos.password();
    }

    public void actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario){
        if (datosActualizarUsuario.nombre() != null){
            this.nombre = datosActualizarUsuario.nombre();
        }
        if (datosActualizarUsuario.correo() != null){
            this.correo = datosActualizarUsuario.correo();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((rol.name())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
