package com.medpharm.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
public record DetalleRecetaDTO(
    @NotNull Long medicamentoId,
    @NotNull @Positive Integer cantidad,
    @NotBlank String dosisIndicada
) {}
