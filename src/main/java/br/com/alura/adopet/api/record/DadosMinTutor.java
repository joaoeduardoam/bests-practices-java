package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;

public record DadosMinTutor(Long id, String nome) {

    public DadosMinTutor(Tutor tutor) {
        this(tutor.getId(), tutor.getNome());
    }

}

