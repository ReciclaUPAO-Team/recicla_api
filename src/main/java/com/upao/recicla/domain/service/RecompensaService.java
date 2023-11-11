package com.upao.recicla.domain.service;

import com.upao.recicla.domain.entity.Recompensa;
import com.upao.recicla.domain.repository.RecompensaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecompensaService {

    @Autowired
    public final RecompensaRepository recompensaRepository;

    public RecompensaService(RecompensaRepository recompensaRepository) {
        this.recompensaRepository = recompensaRepository;
    }
    public Recompensa addRecompensa(Recompensa recompensa) {
        if (recompensa.getValor()<=0){
            throw new IllegalArgumentException("El valor de la recompensa debe ser mayor a 0");
        }
        recompensa.setFechaInicio(LocalDateTime.now());
        recompensa.setFechaCierre(recompensa.getFechaInicio().plusWeeks(4));

        return recompensaRepository.save(recompensa);
    }

}
