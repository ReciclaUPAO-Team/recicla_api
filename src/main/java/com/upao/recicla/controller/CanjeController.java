package com.upao.recicla.controller;

import com.upao.recicla.domain.entity.Canje;
import com.upao.recicla.domain.service.CanjeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/canje")
@RequiredArgsConstructor
public class CanjeController {

    @Autowired
    private final CanjeService canjeService;

    @PostMapping("/canjear")
    public void canjearPuntos(@RequestBody Canje canje) throws Exception {
        canjeService.canjearPuntos(canje.getUsuario().getId(), canje.getRecompensa().getId());
    }

}

