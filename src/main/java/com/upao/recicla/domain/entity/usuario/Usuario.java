package com.upao.recicla.domain.entity.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upao.recicla.domain.entity.actividad.Actividad;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer edad;
    private String telefono;
    private String correo;
    @Column(name = "puntos_acumulados")
    private Integer puntaje;
    private String dni;
    @Enumerated(EnumType.STRING)
    private Rol rol;

}



