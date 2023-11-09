package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.record.*;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoService abrigoService;


    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<DadosDetalhesAbrigo>> listar() {
        return ResponseEntity.ok(abrigoService.listar());
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarAbrigoDTO dto, UriComponentsBuilder uriBuilder) {
        try{
            var detalhesAbrigo = abrigoService.cadastrar(dto);
            var uri = uriBuilder.path("abrigos/{id}").buildAndExpand(detalhesAbrigo.id()).toUri();
            return ResponseEntity.created(uri).body(detalhesAbrigo);
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{idOuNomeAbrigo}/pets")
    public ResponseEntity listarPets(@PathVariable String idOuNomeAbrigo) {

        try{

            var pets = abrigoService.listarPets(idOuNomeAbrigo);

            return ResponseEntity.ok(pets);

        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("Não foi encontrado abrigo com os dados informados!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/{idOuNomeAbrigo}/pets")
    @Transactional
    public ResponseEntity cadastrarPet(@PathVariable String idOuNomeAbrigo, @RequestBody @Valid CadastrarPetDTO dto, UriComponentsBuilder uriBuilder) {
        try {
            var abrigo = abrigoService.getAbrigoByIdOuNome(idOuNomeAbrigo);
            var detalhesPet = petService.cadastrarPet(abrigo, dto);

            var uri = uriBuilder.path("/pets/{id}").buildAndExpand(detalhesPet.id()).toUri();
            return ResponseEntity.created(uri).body(detalhesPet);

        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("Não foi encontrado abrigo com os dados informados!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
