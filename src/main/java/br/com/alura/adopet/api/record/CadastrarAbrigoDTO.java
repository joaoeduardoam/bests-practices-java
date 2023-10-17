package br.com.alura.adopet.api.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastrarAbrigoDTO(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank @Email String email) {
}
