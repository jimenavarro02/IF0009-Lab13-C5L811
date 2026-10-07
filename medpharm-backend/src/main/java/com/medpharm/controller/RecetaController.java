package com.medpharm.controller;

import com.medpharm.dto.*;
import com.medpharm.service.RecetaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recetas")
public class RecetaController {
    private final RecetaService service;
    public RecetaController(RecetaService service){this.service=service;}

    @GetMapping public List<RecetaResponseDTO> findAll(){return service.findAll();}
    @GetMapping("/estado/{estado}") public List<RecetaResponseDTO> findByEstado(@PathVariable String estado){
        return service.findByEstado(estado);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecetaResponseDTO create(@Valid @RequestBody RecetaRequestDTO request){return service.create(request);}
    @PatchMapping("/{id}/estado")
    public RecetaResponseDTO updateEstado(@PathVariable Long id, @Valid @RequestBody EstadoRecetaDTO request){
        return service.updateEstado(id, request.estado());
    }
}
