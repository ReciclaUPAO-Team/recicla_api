package com.upao.recicla.domain.dto;

import com.upao.recicla.domain.entity.actividad.Actividad;

import java.util.List;

public record ListadoUsuarioActividad(
        Long id,
        String nombre,
        Integer edad,
        String telefono,
        String correo,
        Integer puntaje,
        String dni,
        String rol,
        List<Long> actividad){
}
