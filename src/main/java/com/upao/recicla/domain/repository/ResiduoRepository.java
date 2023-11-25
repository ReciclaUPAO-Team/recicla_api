package com.upao.recicla.domain.repository;

import com.upao.recicla.domain.entity.Residuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ResiduoRepository extends JpaRepository<Residuo, Long> {
}
