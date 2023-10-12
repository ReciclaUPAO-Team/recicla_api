package com.upao.recicla.controller;

import com.upao.recicla.domain.entity.residuo.Residuo;
import com.upao.recicla.domain.service.ResiduoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/residuos")
public class ResiduoController {

    private final ResiduoService residuoService;

    public ResiduoController(ResiduoService residuoService) {
        this.residuoService = residuoService;
    }
    @GetMapping
    public List<Residuo> getAllResiduos(){
        return residuoService.getAllResiduos();
    }
    @GetMapping("/{id}")
    public Residuo getResiduoById(@PathVariable Long id) {
        return residuoService.getResiduoById(id)
                .orElse(new Residuo());
    }
    @PostMapping
    public void addResiduo(@RequestBody Residuo residuo) {
        residuoService.addResiduo(residuo);
    }
    @PutMapping("/{id}")
    public void updateResiduo(@RequestBody Residuo residuo, @PathVariable Long id) {
        residuoService.updateResiduo(residuo, id);
    }
    @DeleteMapping("/{id}")
    public void deleteResiduoById(@PathVariable Long id) {
        residuoService.deleteResiduoById(id);
    }

}
