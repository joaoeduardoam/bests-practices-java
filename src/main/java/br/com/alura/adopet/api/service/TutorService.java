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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {


    @Autowired
    private TutorRepository tutorRepository;

    public DadosDetalhesTutor cadastrar(CadastrarTutorDTO dto) {
        boolean telefoneJaCadastrado = tutorRepository.existsByTelefone(dto.telefone());
        boolean emailJaCadastrado = tutorRepository.existsByEmail(dto.email());

        if (telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        } else {
            Tutor tutor = new Tutor(dto);
            tutorRepository.save(tutor);
            return new DadosDetalhesTutor(tutor);
        }

    }

    public DadosDetalhesTutor atualizarInformacoes(DadosAtualizacaoTutor dto) {

        var tutor = tutorRepository.getReferenceById(dto.id());
        tutor.atualizarInformacoes(dto);
        return new DadosDetalhesTutor(tutor);

    }
}
