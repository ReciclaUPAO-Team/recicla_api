package com.upao.recicla.domain.entity.recompensas;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "recompensas")
@Entity(name = "Recompensas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Recompensas {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;

    private String titulo;

    private String descripcion;

    private String valor;


}
