package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.record.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;


    public void validar(SolicitacaoAdocaoDTO dto){

        boolean temAdocaoEmAndamento = adocaoRepository.existsByTutorIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);

        if (temAdocaoEmAndamento) {
            throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
        }


    }




}
