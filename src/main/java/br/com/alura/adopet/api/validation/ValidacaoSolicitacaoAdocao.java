package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.record.SolicitacaoAdocaoDTO;

public interface ValidacaoSolicitacaoAdocao {

    void validar(SolicitacaoAdocaoDTO dto);

}
