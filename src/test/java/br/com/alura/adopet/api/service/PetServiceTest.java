package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.record.CadastrarPetDTO;
import br.com.alura.adopet.api.record.DadosDetalhesPet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    PetService petService;

    @Mock
    CadastrarPetDTO cadastrarPetDTOdto;

    @Mock
    Abrigo abrigo;


    @Mock
    private PetRepository petRepository;

    @Mock
    Pet pet;


    @Test
    void deveriaRetornarPetCadastrado(){

        //ARRANGE
        var pet = new Pet(cadastrarPetDTOdto, abrigo);

        //ACT
        var petDTO = petService.cadastrarPet(abrigo, cadastrarPetDTOdto);

        //ASSERT
        assertThat(new DadosDetalhesPet(pet)).isEqualTo(petDTO);
        assertThat(abrigo.getId()).isEqualTo(petDTO.idAbrigo());


    }


    @Test
    void deveriaRetornarTodosOsPetsDisponiveis(){

        var pets = petRepository.findAllByAdotadoFalse().stream().map(DadosDetalhesPet::new).toList();

        var petsDisponiveisDTO = petService.listarPetsDisponíveis();

        assertThat(petsDisponiveisDTO).isEqualTo(pets);

    }


    @Test
    void deveriaRetornarPetDetalhado(){

        //ARRANGE
        var pet = new Pet(cadastrarPetDTOdto, abrigo);
        given(petRepository.getReferenceById(any())).willReturn(pet);

        //ACT
        var petDTO = petService.detalharPet(pet.getId());

        //ASSERT
        assertThat(new DadosDetalhesPet(pet)).isEqualTo(petDTO);


    }


}