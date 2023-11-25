package com.upao.recicla.domain.dto.comunidadDto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.upao.recicla.domain.entity.Comunidad}
 */
public record DatosRegistroComunidad(Long id, String nombre, String descripcion) implements Serializable {
}