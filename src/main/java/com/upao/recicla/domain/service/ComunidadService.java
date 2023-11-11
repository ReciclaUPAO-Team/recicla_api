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

    public ComunidadService(ComunidadRepository comunidadRepository) {
        this.comunidadRepository = comunidadRepository;
    }

    public void addComunidad(Comunidad comunidad) {
        comunidadRepository.save(comunidad);
    }

    public Comunidad getReferenceById(Long id) {
        return comunidadRepository.getReferenceById(id);
    }

    public void deleteComunidadById(Long id) {
        comunidadRepository.deleteById(id);
    }

}
