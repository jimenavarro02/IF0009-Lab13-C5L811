package com.medpharm.controller;
import com.medpharm.model.Medicamento;
import com.medpharm.service.MedicamentoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medicamentos")
public class MedicamentoController {
    private final MedicamentoService service;
    public MedicamentoController(MedicamentoService service){this.service=service;}
    @GetMapping public List<Medicamento> findAll(){return service.findAll();}
}
