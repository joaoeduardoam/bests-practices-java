package br.com.alura.adopet.api.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastrarTutorDTO(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank @Email String email) {
}
