package com.upao.recicla.controller;

import com.upao.recicla.domain.entity.recompensas.Recompensas;
import com.upao.recicla.domain.service.RecompensasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/recompensas")
public class RecompensasController {

    private final RecompensasService recompensasService;

    @Autowired
    public RecompensasController(RecompensasService recompensasService){
        this.recompensasService = recompensasService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> addRecompensas(@Validated @RequestBody Recompensas recompensas, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = "Error";
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorMessage + "" + errors);
        }

        Recompensas newRecompensas = recompensasService.addRecompensas(recompensas);
        return ResponseEntity.status(HttpStatus.OK).body("Recompensa creada Exitosamente");

    }

    @GetMapping("/catalogo")
    public ResponseEntity<?> listarRecompensas(){
        List<Recompensas> recompensas = recompensasService.listarRecompensas();

        if (recompensas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron Recompensas disponibles");
        }

        return new ResponseEntity<List<Recompensas>>(recompensas, HttpStatus.OK);
    }


}
