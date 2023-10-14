package com.upao.recicla.domain.repository;

import com.upao.recicla.domain.entity.recompensas.Recompensas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecompensasRepository extends JpaRepository<Recompensas, Long> {
}
