package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.*;

public record DadosDetalhesAdocao(Long id, Pet pet, Tutor tutor, String motivo, StatusAdocao status, String justificativaStatus) {

    public DadosDetalhesAdocao(Adocao adocao) {
        this(adocao.getId(), adocao.getPet(), adocao.getTutor(), adocao.getMotivo(), adocao.getStatus(), adocao.getJustificativaStatus());
    }
}

