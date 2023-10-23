package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.record.*;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.ValidacaoSolicitacaoAdocao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private List<ValidacaoSolicitacaoAdocao> validacoes;


    public List<DadosDetalhesAbrigo> listar() {

        List<Abrigo> abrigos = abrigoRepository.findAll();
        List<DadosDetalhesAbrigo> abrigosDTO = abrigos.stream().map(DadosDetalhesAbrigo::new).toList();
        return abrigosDTO;

    }

    public DadosDetalhesAbrigo cadastrar(CadastrarAbrigoDTO dto) {
        boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(dto.telefone());
        boolean emailJaCadastrado = abrigoRepository.existsByEmail(dto.email());
        boolean nomeJaCadastrado = abrigoRepository.existsByNome(dto.nome());

        if (telefoneJaCadastrado || emailJaCadastrado || nomeJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        } else {
            Abrigo abrigo = new Abrigo(dto);
            abrigoRepository.save(abrigo);
            return new DadosDetalhesAbrigo(abrigo);
        }

    }


    public List<DadosDetalhesPet> listarPets(String idOuNome) {

        var abrigo = getAbrigoByIdOuNome(idOuNome);
        List<Pet> pets = abrigo.getPets();
        var petsDTO = pets.stream().map(DadosDetalhesPet::new).toList();
        return ResponseEntity.ok(petsDTO).getBody();


    }


    public Abrigo getAbrigoByIdOuNome(String idOuNome) {

        try{
            Long id = Long.parseLong(idOuNome);
            var abrigo = abrigoRepository.getReferenceById(id);
            return abrigo;
        } catch (
                EntityNotFoundException enfe) {
            throw new ValidacaoException("Não foi encontrado abrigo com esses dados!");
        } catch (NumberFormatException e) {
            try {
                var abrigo = abrigoRepository.findByNome(idOuNome);
                return abrigo;
            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException("Não foi encontrado abrigo com esses dados!");
            }
        }

    }
}
