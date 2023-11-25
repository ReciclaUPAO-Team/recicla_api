package com.upao.recicla.domain.repository;

import com.upao.recicla.domain.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {

}
