package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.Adocao;
import jakarta.validation.constraints.NotNull;

public record AprovacaoAdocaoDTO (@NotNull Long idAdocao){
}
