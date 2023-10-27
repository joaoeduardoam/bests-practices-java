package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.record.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoTutorLimiteAdocoes implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;


    public void validar(SolicitacaoAdocaoDTO dto){

        int numeroDeAdocoes = adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO);

        if (numeroDeAdocoes == 5) {
            throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
        }


    }




}
