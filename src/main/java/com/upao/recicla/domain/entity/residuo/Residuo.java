package com.upao.recicla.domain.entity.residuo;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "residuos")
@Entity(name = "Residuo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Residuo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
}
