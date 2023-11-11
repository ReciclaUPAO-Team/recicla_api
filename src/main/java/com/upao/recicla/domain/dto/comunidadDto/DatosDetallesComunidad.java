package com.upao.recicla.domain.dto.comunidadDto;

import com.upao.recicla.domain.entity.Comunidad;

import java.io.Serializable;

/**
 * DTO for {@link Comunidad}
 */
public record DatosDetallesComunidad(Long id, String nombre, String descripcion) implements Serializable {
    public DatosDetallesComunidad(Comunidad comunidad) {
        this(comunidad.getId(), comunidad.getNombre(), comunidad.getDescripcion());
    }

}