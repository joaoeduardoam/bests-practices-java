package br.com.alura.adopet.api.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTutor(
        @NotNull
        long id,
        String nome,
        String telefone,
        @Email
        String email

) {
}
