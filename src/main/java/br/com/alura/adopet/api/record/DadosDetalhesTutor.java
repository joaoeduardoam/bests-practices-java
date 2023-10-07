package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Tutor;

import java.util.List;

public record DadosDetalhesTutor(Long id, String nome, String email, String telefone, List<Adocao> adocoes) {

    public DadosDetalhesTutor(Tutor tutor){

        this (tutor.getId(), tutor.getNome(), tutor.getEmail(),  tutor.getTelefone(),
                tutor.getAdocoes());

    }

}
