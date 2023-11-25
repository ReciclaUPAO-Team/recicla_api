package com.upao.recicla.domain.dto.recompensaDto;

import java.io.Serializable;

/**
 * DTO for {@link com.upao.recicla.domain.entity.Recompensa}
 */
public record DatosRegistroRecompensa(Long id,String titulo, String descripcion, String categoria,
                                      Double valor) implements Serializable {
}