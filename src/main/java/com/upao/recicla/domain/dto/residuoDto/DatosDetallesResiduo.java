package com.upao.recicla.domain.dto.residuoDto;

import com.upao.recicla.domain.entity.Residuo;

import java.io.Serializable;

/**
 * DTO for {@link Residuo}
 */
public record DatosDetallesResiduo(Long id, String nombre, String descripcion, String tipo,
                                   Double puntos) implements Serializable {
    public DatosDetallesResiduo(Residuo residuo) {
        this(residuo.getId(), residuo.getNombre(), residuo.getDescripcion(), residuo.getTipo(),
                residuo.getPuntos());
    }
}