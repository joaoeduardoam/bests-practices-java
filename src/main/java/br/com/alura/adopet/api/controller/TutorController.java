package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.record.CadastrarTutorDTO;
import br.com.alura.adopet.api.record.DadosAtualizacaoTutor;
import br.com.alura.adopet.api.record.DadosDetalhesPet;
import br.com.alura.adopet.api.record.DadosDetalhesTutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;


    @GetMapping
    public ResponseEntity<List<DadosDetalhesTutor>> listarTodosDisponiveis() {

        var tutores = tutorService.listarTutores();

        return ResponseEntity.ok(tutores);
    }
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarTutorDTO dto, UriComponentsBuilder uriBuilder) {
        try{
            var detalhesTutor = tutorService.cadastrar(dto);
            var uri = uriBuilder.path("tutores/{id}").buildAndExpand(detalhesTutor.id()).toUri();
            return ResponseEntity.created(uri).body(detalhesTutor);
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTutor dto) {

        try{
            var tutorAtualizado = tutorService.atualizarInformacoes(dto);
            return ResponseEntity.ok(tutorAtualizado);
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
