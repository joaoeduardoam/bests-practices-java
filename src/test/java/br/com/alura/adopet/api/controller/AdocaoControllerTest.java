package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.record.DadosDetalhesAdocao;
import br.com.alura.adopet.api.record.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdocaoService adocaoService;

    @Autowired
    private JacksonTester<SolicitacaoAdocaoDTO> solicitacaoAdocaoDTOJson;

    @Autowired
    private JacksonTester<DadosDetalhesAdocao> dadosDetalhesAdocaoJson;



    @Test
    void deveriaDevolverCodigo400ParaSolicitacaoComErros() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mockMvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void deveriaDevolverCodigo201ParaSolicitacaoSemErros() throws Exception {
        //ARRANGE
        var dadosDetalhamentoAdocao = new DadosDetalhesAdocao(null, 3l , 2l, "Motivo qualquer", StatusAdocao.AGUARDANDO_AVALIACAO, null);
        given(adocaoService.solicitar(any())).willReturn(dadosDetalhamentoAdocao);


        //ACT
        var response = mockMvc.perform(
                post("/adocoes")
                        .content(solicitacaoAdocaoDTOJson.write(
                                new SolicitacaoAdocaoDTO(3l,2l,"Motivo qualquer")
                        ).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        ).andReturn().getResponse();

        //ASSERT

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = dadosDetalhesAdocaoJson.write(dadosDetalhamentoAdocao).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


}