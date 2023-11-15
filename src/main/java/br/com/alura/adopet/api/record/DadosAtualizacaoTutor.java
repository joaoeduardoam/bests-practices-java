package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Tutor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.stream.Collectors;

public record DadosAtualizacaoTutor(
        @NotNull
        long id,
        String nome,
        String telefone,
        @Email
        String email

) {

        public DadosAtualizacaoTutor(Tutor tutor){

                this (tutor.getId(), tutor.getNome(), tutor.getTelefone(), tutor.getEmail());

        }
}
