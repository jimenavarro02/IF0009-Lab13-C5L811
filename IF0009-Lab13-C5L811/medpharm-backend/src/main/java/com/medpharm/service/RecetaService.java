package com.medpharm.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medpharm.dto.DetalleRecetaDTO;
import com.medpharm.dto.DetalleRecetaResponseDTO;
import com.medpharm.dto.RecetaRequestDTO;
import com.medpharm.dto.RecetaResponseDTO;
import com.medpharm.model.DetalleReceta;
import com.medpharm.model.Medicamento;
import com.medpharm.model.RecetaMedica;
import com.medpharm.model.Usuario;
import com.medpharm.repository.MedicamentoRepository;
import com.medpharm.repository.RecetaRepository;
import com.medpharm.repository.UsuarioRepository;

@Service
public class RecetaService {
    private final RecetaRepository recetas;
    private final MedicamentoRepository medicamentos;
    private final UsuarioRepository usuarios;

    public RecetaService(RecetaRepository recetas, MedicamentoRepository medicamentos, UsuarioRepository usuarios){
        this.recetas=recetas; this.medicamentos=medicamentos; this.usuarios=usuarios;
    }

    @Transactional(readOnly=true)
    public List<RecetaResponseDTO> findAll(){ return recetas.findAll().stream().map(this::toDto).toList(); }

    @Transactional(readOnly=true)
    public List<RecetaResponseDTO> findByEstado(String estado){
        validarEstado(estado);
        return recetas.findByEstado(estado.toUpperCase()).stream().map(this::toDto).toList();
    }

    @Transactional
    public RecetaResponseDTO create(RecetaRequestDTO request){
        if (request.detalles() == null || request.detalles().isEmpty())
            throw new IllegalArgumentException("La receta debe contener al menos un medicamento.");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario medico = usuarios.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("Médico autenticado no encontrado."));
        if (!"MEDICO".equals(medico.getRol()))
            throw new IllegalStateException("Solo un usuario MEDICO puede crear recetas.");

        RecetaMedica receta = new RecetaMedica();
        receta.setCodigoReceta(generarCodigo());
        receta.setPacienteNombre(request.pacienteNombre());
        receta.setMedico(medico);
        receta.setEstado("PENDIENTE");

        for (DetalleRecetaDTO d : request.detalles()) {
            if (d.cantidad() == null || d.cantidad() <= 0)
                throw new IllegalArgumentException("La cantidad debe ser un entero mayor que 0.");
            Medicamento med = medicamentos.findById(d.medicamentoId())
        .orElseThrow(() -> new IllegalArgumentException(
                "Medicamento no encontrado: " + d.medicamentoId()
        ));
            if (med.getStock() < d.cantidad())
                throw new IllegalArgumentException("Stock insuficiente para " + med.getNombre() +
                        ". Disponible: " + med.getStock());
            DetalleReceta detalle = new DetalleReceta();
            detalle.setReceta(receta);
            detalle.setMedicamento(med);
            detalle.setCantidad(d.cantidad());
            detalle.setDosisIndicada(d.dosisIndicada());
            receta.getDetalles().add(detalle);
        }
        return toDto(recetas.save(receta));
    }

    @Transactional
    public RecetaResponseDTO updateEstado(Long id, String estado){
        validarEstado(estado);
        RecetaMedica receta = recetas.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Receta no encontrada: " + id));
        String nuevo = estado.toUpperCase();
        if ("PENDIENTE".equals(nuevo))
            throw new IllegalArgumentException("El estado solo puede cambiar a DESPACHADA o CANCELADA.");
        if ("DESPACHADA".equals(nuevo) && "PENDIENTE".equals(receta.getEstado())) {
            for (DetalleReceta d : receta.getDetalles()) {
                Medicamento m = d.getMedicamento();
                if (m.getStock() < d.getCantidad())
                    throw new IllegalArgumentException("Stock insuficiente para " + m.getNombre());
            }
            for (DetalleReceta d : receta.getDetalles()) {
                Medicamento m = d.getMedicamento();
                m.setStock(m.getStock() - d.getCantidad());
                medicamentos.save(m);
            }
        }
        receta.setEstado(nuevo);
        return toDto(recetas.save(receta));
    }

    private String generarCodigo(){
        return "REC-" + java.time.Year.now().getValue() + "-" +
                String.format("%03d", recetas.count()+1);
    }

    private void validarEstado(String estado){
        if (!Set.of("PENDIENTE","DESPACHADA","CANCELADA").contains(estado.toUpperCase()))
            throw new IllegalArgumentException("Estado inválido: " + estado);
    }

    private RecetaResponseDTO toDto(RecetaMedica r){
        var detalles = r.getDetalles().stream().map(d ->
            new DetalleRecetaResponseDTO(d.getId(), d.getMedicamento().getId(),
                d.getMedicamento().getNombre(), d.getCantidad(), d.getDosisIndicada())).toList();
        return new RecetaResponseDTO(r.getId(), r.getCodigoReceta(), r.getPacienteNombre(),
            r.getMedico().getId(), r.getMedico().getNombreCompleto(), r.getEstado(),
            r.getFechaEmision(), detalles);
    }
}
