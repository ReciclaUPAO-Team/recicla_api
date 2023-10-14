package com.upao.recicla.domain.service;


import com.upao.recicla.domain.entity.recompensas.Recompensas;
import com.upao.recicla.domain.repository.RecompensasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecompensasService {


    public final RecompensasRepository recompensasRepository;

    public RecompensasService(RecompensasRepository recompensasRepository){
        this.recompensasRepository = recompensasRepository;
    }

    public Recompensas addRecompensas(Recompensas recompensas){

        return recompensasRepository.save(recompensas);
    }

}
