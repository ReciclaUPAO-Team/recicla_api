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

    public Page<Recompensa> getAllRecompensas(Pageable pageable) {
        return recompensaRepository.findAll(pageable);
    }
    public Recompensa getReferenceById(Long id) {
        return recompensaRepository.getReferenceById(id);
    }

}
