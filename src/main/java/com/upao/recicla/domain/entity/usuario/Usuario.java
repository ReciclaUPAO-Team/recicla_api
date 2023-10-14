package com.upao.recicla.domain.entity.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upao.recicla.domain.entity.actividad.Actividad;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true )
public class Usuario {
    @Getter
    @Setter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String correo;


    @Column(name = "puntos_acumulados")
    private Integer puntaje;

    @Column(nullable = false)
    private String dni;

    @Enumerated(EnumType.STRING)
    private Rol rol;

}



