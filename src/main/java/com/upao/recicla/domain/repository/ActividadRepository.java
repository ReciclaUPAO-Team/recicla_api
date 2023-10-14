package com.upao.recicla.domain.repository;

import com.upao.recicla.domain.entity.actividad.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}
