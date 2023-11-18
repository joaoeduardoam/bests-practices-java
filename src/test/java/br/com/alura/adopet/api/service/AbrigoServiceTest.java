package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.record.*;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    AbrigoService abrigoService;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Captor
    private ArgumentCaptor<Abrigo> adocaoCaptor;



    @Test
    void deveriaRetornarAbrigoCadastrado(){

        //ARRANGE

        var cadastrarAbrigoDTO = new CadastrarAbrigoDTO("APIPA", "9999999999", "apipa@email.com.br");
        var abrigo = new Abrigo(cadastrarAbrigoDTO);

        //ACT

        abrigoService.cadastrar(cadastrarAbrigoDTO);

        //ASSERT
        then(abrigoRepository).should().save(adocaoCaptor.capture());
        Abrigo abrigoSalvo = adocaoCaptor.getValue();
        Assertions.assertEquals(abrigo.getNome(), abrigoSalvo.getNome());
        Assertions.assertEquals(abrigo.getEmail(), abrigoSalvo.getEmail());

    }


    @Test
    void deveriaRetornarTodosAbrigos(){

        var abrigos = abrigoRepository.findAll().stream().map(DadosDetalhesAbrigo::new).toList();

        var abrigosDTO = abrigoService.listar();

        assertThat(abrigosDTO).isEqualTo(abrigos);

    }

    @Test
    void deveriaRetornarTodosOsPetsDoAbrigo(){

        //ARRANGE
        var abrigo = new Abrigo(new CadastrarAbrigoDTO("APIPA", "9999999999", "apipa@email.com.br"));
        abrigo.setId(1l);
        var pet = new Pet(new CadastrarPetDTO(TipoPet.GATO, "Arrupiado", "RasgaSaco", "branco", 1, 1f), abrigo);
        given(abrigoRepository.getReferenceById(any())).willReturn(abrigo);
        abrigo.getPets().add(pet);
        var pets = abrigo.getPets();

        //ACT
        var listPetsService = abrigoService.listarPets(abrigo.getId().toString());

        //ASSERT
        assertThat(pets.stream().map(DadosDetalhesPet::new).toList()).isEqualTo(listPetsService);


    }




}