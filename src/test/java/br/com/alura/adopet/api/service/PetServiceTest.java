package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.record.CadastrarPetDTO;
import br.com.alura.adopet.api.record.DadosDetalhesPet;
import br.com.alura.adopet.api.record.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    PetService petService;


    @Mock
    Abrigo abrigo;


    @Mock
    private PetRepository petRepository;


    @Captor
    private ArgumentCaptor<Pet> adocaoCaptor;



    @Test
    void deveriaRetornarPetCadastrado(){


        var cadastrarPetDTO = new CadastrarPetDTO(TipoPet.GATO, "Arrupiado", "RasgaSaco", "branco", 1, 1f);
        var pet = new Pet(cadastrarPetDTO, abrigo);

        //ACT

        petService.cadastrarPet(abrigo, cadastrarPetDTO);

        //ASSERT
        then(petRepository).should().save(adocaoCaptor.capture());
        Pet petSalvo = adocaoCaptor.getValue();
        Assertions.assertEquals(pet.getNome(), petSalvo.getNome());
        Assertions.assertEquals(abrigo, petSalvo.getAbrigo());

    }


    @Test
    void deveriaRetornarTodosOsPetsDisponiveis(){

        var pets = petRepository.findAllByAdotadoFalse().stream().map(DadosDetalhesPet::new).toList();

        var petsDisponiveisDTO = petService.listarPetsDisponíveis();

        assertThat(petsDisponiveisDTO).isEqualTo(pets);

    }


    @Test
    void deveriaRetornarPetDetalhado(){


        var cadastrarPetDTO = new CadastrarPetDTO(TipoPet.GATO, "Arrupiado", "RasgaSaco", "branco", 1, 1f);
        var pet = new Pet(cadastrarPetDTO, abrigo);
        pet.setId(1l);
        given(petRepository.getReferenceById(any())).willReturn(pet);

        //ACT

        var petDTO = petService.detalharPet(pet.getId());

        //ASSERT

        assertThat(new DadosDetalhesPet(pet)).isEqualTo(petDTO);



    }


}