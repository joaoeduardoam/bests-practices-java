package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.record.*;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    TutorService tutorService;

    @Mock
    private TutorRepository tutorRepository;


    @Captor
    private ArgumentCaptor<Tutor> adocaoCaptor;



    @Test
    void deveriaRetornarTutorCadastrado(){

        //ARRANGE

        var cadastrarTutorDTO = new CadastrarTutorDTO("Xico", "9999999999", "xico@email.com.br");
        var tutor = new Tutor(cadastrarTutorDTO);

        //ACT

        tutorService.cadastrar(cadastrarTutorDTO);

        //ASSERT
        then(tutorRepository).should().save(adocaoCaptor.capture());
        Tutor tutorSalvo = adocaoCaptor.getValue();
        Assertions.assertEquals(tutor.getNome(), tutorSalvo.getNome());
        Assertions.assertEquals(tutor.getEmail(), tutorSalvo.getEmail());

    }


    @Test
    void deveriaRetornarTodosTutores(){

        var tutores = tutorRepository.findAll().stream().map(DadosDetalhesTutor::new).toList();

        var tutoresDTO = tutorService.listarTutores();

        assertThat(tutoresDTO).isEqualTo(tutores);

    }


    @Test
    void deveriaAtualizarInformacoes(){

        //ARRANGE

        var cadastrarTutorDTO = new CadastrarTutorDTO("Xico", "9999999999", "xico@email.com.br");
        var tutor = new Tutor(cadastrarTutorDTO);
        tutor.setId(1l);
        given(tutorRepository.getReferenceById(any())).willReturn(tutor);

        //ACT

        tutorService.cadastrar(cadastrarTutorDTO);
        tutor.setNome("Xico Silva");
        tutor.setTelefone("88888888888");
        tutor.setEmail("xicosilva@email.com.br");
        var tutorModificado = tutorService.atualizarInformacoes(new DadosAtualizacaoTutor(tutor));


        //ASSERT
        Assertions.assertEquals(tutor.getNome(), tutorModificado.nome());
        Assertions.assertEquals(tutor.getTelefone(), tutorModificado.telefone());
        Assertions.assertEquals(tutor.getEmail(), tutorModificado.email());


    }


}