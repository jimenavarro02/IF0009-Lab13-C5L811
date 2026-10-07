package com.medpharm.dto;
import jakarta.validation.constraints.NotBlank;
public record EstadoRecetaDTO(@NotBlank String estado) {}
