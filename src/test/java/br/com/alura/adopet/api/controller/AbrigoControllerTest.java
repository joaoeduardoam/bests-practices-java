package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.record.*;
import br.com.alura.adopet.api.service.AbrigoService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    Abrigo abrigo;

    @MockBean
    private AbrigoService abrigoService;


    @MockBean
    private PetService petService;

    @Autowired
    private JacksonTester<DadosDetalhesAbrigo> dadosDetalhesAbrigoJson;


    @Autowired
    private JacksonTester<CadastrarAbrigoDTO> dadosCadastrarAbrigoJson;


    @Autowired
    private JacksonTester<CadastrarPetDTO> dadosCadastrarPetJson;


    @Autowired
    private JacksonTester<DadosDetalhesPet> dadosDetalhesPetJson;


    @Test
    void deveriaDevolverCodigo400ParaCadastroAbrigoComErros() throws Exception {
        //ARRANGE

        //ACT
        var response = mockMvc.perform(
                post("/abrigos")
                        .content(dadosCadastrarAbrigoJson.write(     //Telefone Inválido
                                new CadastrarAbrigoDTO("APIPA", "999999999999", "apipa@email.com.br")
                        ).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void deveriaDevolverCodigo201ParaCadastroAbrigoSemErros() throws Exception {
        //ARRANGE
        var dadosDetalhamentoAbrigo = new DadosDetalhesAbrigo(null , "APIPA", "9999999999", "apipa@email.com.br", null);
        given(abrigoService.cadastrar(any())).willReturn(dadosDetalhamentoAbrigo);


        //ACT
        var response = mockMvc.perform(
                post("/abrigos")
                        .content(dadosCadastrarAbrigoJson.write(
                                new CadastrarAbrigoDTO("APIPA", "9999999999", "apipa@email.com.br")
                        ).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        ).andReturn().getResponse();

        //ASSERT

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = dadosDetalhesAbrigoJson.write(dadosDetalhamentoAbrigo).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


    @Test
    void deveriaDevolverCodigo200ParaListagemDeAbrigos() throws Exception {

        //ACT
        var response = mockMvc.perform(
                get("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void deveriaDevolverCodigo200ParaListagemDePetsPorAbrigo() throws Exception {

        var pets = abrigo.getPets();
        given(abrigoService.listarPets(any())).willReturn(pets.stream().map(DadosDetalhesPet::new).toList());


        //ACT
        var response = mockMvc.perform(
                get("/abrigos/1/pets")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void deveriaDevolverCodigo201ParaCadastroPetNoAbrigoSemErros() throws Exception {
        //ARRANGE

        Abrigo abrigo = new Abrigo(new CadastrarAbrigoDTO("APIPA", "999999999999", "apipa@email.com.br"));
        abrigo.setId(1l);
        var cadastrarPetDTO = new CadastrarPetDTO(TipoPet.GATO, "Arrupiado", "RasgaSaco", "preto", 1, 1.0f );
        Pet pet = new Pet(cadastrarPetDTO, abrigo);
        DadosDetalhesPet dadosDetalhesPet = new DadosDetalhesPet(pet);
        given(petService.cadastrarPet(any(),any())).willReturn(dadosDetalhesPet);

        //ACT
        var response = mockMvc.perform(
                post("/abrigos/1/pets")
                        .content(dadosCadastrarPetJson.write(
                                cadastrarPetDTO
                        ).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = dadosDetalhesPetJson.write(dadosDetalhesPet).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

    @Test
    void deveriaDevolverCodigo400ParaCadastroPetNoAbrigoComErros() throws Exception {
        //ARRANGE

        Abrigo abrigo = new Abrigo(new CadastrarAbrigoDTO("APIPA", "999999999999", "apipa@email.com.br"));
        abrigo.setId(1l);                                                                           // Cor inválida
        var cadastrarPetDTO = new CadastrarPetDTO(TipoPet.GATO, "Arrupiado", "RasgaSaco", "", 1, 1.0f );
        Pet pet = new Pet(cadastrarPetDTO, abrigo);
        DadosDetalhesPet dadosDetalhesPet = new DadosDetalhesPet(pet);
        given(petService.cadastrarPet(any(),any())).willReturn(dadosDetalhesPet);

        //ACT
        var response = mockMvc.perform(
                post("/abrigos/1/pets")
                        .content(dadosCadastrarPetJson.write(
                                cadastrarPetDTO
                        ).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());


    }

}