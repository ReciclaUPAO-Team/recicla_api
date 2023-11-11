package com.upao.recicla.domain.service;

import com.upao.recicla.domain.entity.Comunidad;
import com.upao.recicla.domain.repository.ComunidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComunidadService {

    @Autowired
    private final ComunidadRepository comunidadRepository;


    public void updateComunidad(Comunidad comunidad, Long id) {
        Comunidad comunidadExists = comunidadRepository.findById(id)
                .orElse(new Comunidad());

        comunidadExists.setNombre(comunidad.getNombre());
        comunidadExists.setDescripcion(comunidad.getDescripcion());
        comunidadExists.setFechaCreacion(comunidad.getFechaCreacion());
        comunidadRepository.save(comunidadExists);
    }


}
