               package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.record.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
               class ValidacaoTutorComAdocaoEmAndamentoTest {

                   @InjectMocks
                   private ValidacaoTutorComAdocaoEmAndamento validacao;

                   @Mock
                   private AdocaoRepository adocaoRepository;

                   @Mock
                   private SolicitacaoAdocaoDTO dto;


                   @Test
                   void deveriaPermitirSolicitacaoDeAdocao (){

                       //ARRANGE
                       BDDMockito.given(adocaoRepository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);


                       //ACT + ASSERT
                       Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

                   }

                   @Test
                   void naoDeveriaPermitirSolicitacaoDeAdocao (){

                       //ARRANGE
                       BDDMockito.given(adocaoRepository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);



                       //ACT + ASSERT
                       Assertions.assertThrows(ValidacaoException.class,() -> validacao.validar(dto));

                   }

               }