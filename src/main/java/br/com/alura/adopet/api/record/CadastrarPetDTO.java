package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarPetDTO(

        @NotNull TipoPet tipo,
        @NotBlank String nome,
        @NotBlank String raca,
        @NotBlank String cor,
        @NotNull Integer idade,
        @NotNull Float peso,

        @NotNull Abrigo abrigo) {
}
