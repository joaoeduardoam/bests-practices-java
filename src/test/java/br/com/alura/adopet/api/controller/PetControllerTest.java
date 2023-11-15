package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.record.CadastrarPetDTO;
import br.com.alura.adopet.api.record.DadosDetalhesPet;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PetService petService;

    @Test
    void deveriaDevolverCodigo200ParaListagemDePetsDisponiveis() throws Exception {

        //ACT
        var response = mockMvc.perform(
                get("/pets")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void deveriaDevolverCodigo200ParaDetalhesPetPorId() throws Exception {

        //ARRANGE
        var dadosDetalhesPet = new DadosDetalhesPet(null, TipoPet.GATO, "Arrupiado", "RasgaSaco", 1, false,1l);

        given(petService.detalharPet(any())).willReturn(dadosDetalhesPet);

        //ACT
        var response = mockMvc.perform(
                get("/pets/7")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        //ASSERT
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

}