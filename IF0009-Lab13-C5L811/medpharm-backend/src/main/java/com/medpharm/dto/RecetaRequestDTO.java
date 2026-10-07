package com.medpharm.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
public record RecetaRequestDTO(
    @NotBlank @Size(min=5) String pacienteNombre,
    @Valid List<DetalleRecetaDTO> detalles
) {}
