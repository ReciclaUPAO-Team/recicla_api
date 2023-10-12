package com.upao.recicla.domain.entity.actividad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upao.recicla.domain.entity.residuo.Residuo;
import com.upao.recicla.domain.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "actividades")
@Entity(name = "Actividad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer puntosAsociados;
    /*@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fecha;*/
    @Column(name = "cantidad_reciclada")
    private Double cantidadReciclada;
    @Column(name = "imagen")
    private String evidencia;
    @ManyToOne
    @JoinColumn(name = "residuo_id")
    private Residuo residuo;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
