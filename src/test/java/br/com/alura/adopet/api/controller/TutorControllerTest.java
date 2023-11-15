package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.record.*;
import br.com.alura.adopet.api.service.PetService;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorService tutorService;

    @Autowired
    private JacksonTester<DadosAtualizacaoTutor> dadosAtualizacaoTutorJson;

    @Autowired
    private JacksonTester<DadosDetalhesTutor> dadosDetalhesTutorJson;


    @Autowired
    private JacksonTester<CadastrarTutorDTO> dadosCadastrarTutorJson;


    @Test
    void deveriaDevolverCodigo400ParaCadastroTutorComErros() throws Exception {
        //ARRANGE

        //ACT
        var response = mockMvc.perform(
                post("/tutores")
                        .content(dadosCadastrarTutorJson.write(     //Telefone Inválido
                                new CadastrarTutorDTO("Xico", "999999999999", "xico@email.com.br")
                        ).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void deveriaDevolverCodigo201ParaCadastroTutorSemErros() throws Exception {
        //ARRANGE
        var dadosDetalhamentoTutor = new DadosDetalhesTutor(null , "Xico", "9999999999", "xico@email.com.br", null);
        given(tutorService.cadastrar(any())).willReturn(dadosDetalhamentoTutor);


        //ACT
        var response = mockMvc.perform(
                post("/tutores")
                        .content(dadosCadastrarTutorJson.write(
                                new CadastrarTutorDTO("Xico", "9999999999", "xico@email.com.br")
                        ).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        ).andReturn().getResponse();

        //ASSERT

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = dadosDetalhesTutorJson.write(dadosDetalhamentoTutor).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


    @Test
    void deveriaDevolverCodigo200ParaListagemDeTutores() throws Exception {

        //ACT
        var response = mockMvc.perform(
                get("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void deveriaDevolverCodigo200ParaAtualizarTutor() throws Exception {

        //ARRANGE,
        var cadastrarTutorDTO = new CadastrarTutorDTO("Xico", "9999999999", "xico@email.com.br");
        var tutor = new Tutor(cadastrarTutorDTO);
        tutor.setId(1l);


        given(tutorService.atualizarInformacoes(any())).willReturn(new DadosDetalhesTutor(tutor));

        //ACT
        var response = mockMvc.perform(
                put("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAtualizacaoTutorJson.write(new DadosAtualizacaoTutor(tutor)).getJson())
                        ).andReturn().getResponse();



        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = dadosDetalhesTutorJson.write(new DadosDetalhesTutor(tutor)).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

    @Test
    void deveriaDevolverCodigo400ParaAtualizarTutorComErros() throws Exception {

        //ARRANGE                                                                               Email Inválido
        var cadastrarTutorDTO = new CadastrarTutorDTO("Xico", "9999999999", "xicoemail.com.br");
        var tutor = new Tutor(cadastrarTutorDTO);
        tutor.setId(1l);

        given(tutorService.atualizarInformacoes(any())).willReturn(new DadosDetalhesTutor(tutor));

        //ACT
        var response = mockMvc.perform(
                put("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAtualizacaoTutorJson.write(new DadosAtualizacaoTutor(tutor)).getJson())
        ).andReturn().getResponse();



        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

}