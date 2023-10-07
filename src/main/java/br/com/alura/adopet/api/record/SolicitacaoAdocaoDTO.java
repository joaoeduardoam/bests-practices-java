package br.com.alura.adopet.api.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitacaoAdocaoDTO(@NotNull Long idPet, @NotNull Long idTutor, @NotBlank String motivo) {
}
