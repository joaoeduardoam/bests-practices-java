package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.*;

public record DadosDetalhesAdocao(Long id, Long idPet, Long idTutor, String motivo, StatusAdocao status, String justificativaStatus) {

    public DadosDetalhesAdocao(Adocao adocao) {
        this(adocao.getId(),

                adocao.getPet().getId(), adocao.getTutor().getId(),
                //
                // new DadosMinPet(adocao.getPet()), new DadosMinTutor(adocao.getTutor()),

                adocao.getMotivo(), adocao.getStatus(), adocao.getJustificativaStatus());
    }
}

