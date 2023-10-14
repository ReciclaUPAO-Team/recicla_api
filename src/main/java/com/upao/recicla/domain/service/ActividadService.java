package com.upao.recicla.domain.service;

import com.upao.recicla.domain.entity.actividad.Actividad;
import com.upao.recicla.domain.repository.ActividadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {
    private final ActividadRepository actividadRepository;

    public ActividadService(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }
    public List<Actividad> getAllActividades() {
        return actividadRepository.findAll();
    }
    public Optional<Actividad> getActividadById(Long id) {
        return actividadRepository.findById(id);
    }
    public void addActividad(Actividad actividad) {
        actividadRepository.save(actividad);
    }
    public void updateActividad(Actividad actividad, Long id) {
        Actividad actividadExists = actividadRepository.findById(id)
                .orElse(new Actividad());

        actividadExists.setNombre(actividad.getNombre());
        actividadRepository.save(actividadExists);
    }
    public void deleteActividadById(Long id) {
        actividadRepository.deleteById(id);
    }

}
