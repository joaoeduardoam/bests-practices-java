package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.record.CadastrarTutorDTO;
import br.com.alura.adopet.api.record.DadosAtualizacaoTutor;
import br.com.alura.adopet.api.record.DadosDetalhesPet;
import br.com.alura.adopet.api.record.DadosDetalhesTutor;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {


    @Autowired
    private TutorRepository tutorRepository;

    public DadosDetalhesTutor cadastrar(CadastrarTutorDTO dto) {
        boolean jaCadastrado = tutorRepository.existsByTelefoneOrEmail(dto.telefone(),dto.email());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        } else {
            Tutor tutor = new Tutor(dto);
            tutorRepository.save(tutor);
            return new DadosDetalhesTutor(tutor);
        }

    }

    public DadosDetalhesTutor atualizarInformacoes(DadosAtualizacaoTutor dto) {
        try {

        Tutor tutor = tutorRepository.getReferenceById(dto.id());
        tutor.atualizarInformacoes(dto);
        return new DadosDetalhesTutor(tutor);

        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException("Não foi encontrado tutor com esses dados!");
        }
    }

    public List<DadosDetalhesTutor> listarTutores() {

        List<Tutor> tutores = tutorRepository.findAll();
        List<DadosDetalhesTutor> tutoresDTO = tutores.stream().map(DadosDetalhesTutor::new).toList();
        return tutoresDTO;
    }
}
