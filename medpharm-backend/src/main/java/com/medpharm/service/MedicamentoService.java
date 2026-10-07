package com.medpharm.service;

import com.medpharm.model.Medicamento;
import com.medpharm.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicamentoService {
    private final MedicamentoRepository repository;
    public MedicamentoService(MedicamentoRepository repository){this.repository=repository;}
    public List<Medicamento> findAll(){return repository.findAll();}
    public Medicamento findById(Long id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado: " + id));
    }
}
