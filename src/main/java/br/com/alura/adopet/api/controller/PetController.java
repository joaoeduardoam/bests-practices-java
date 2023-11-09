package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.record.DadosDetalhesPet;
import br.com.alura.adopet.api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pets")

public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<DadosDetalhesPet>> listarTodosDisponiveis() {

        var disponiveis = petService.listarPetsDisponíveis();

        return ResponseEntity.ok(disponiveis);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharPet(@PathVariable Long id){

        var detalhesPet = petService.detalharPet(id);

        return ResponseEntity.ok(detalhesPet);

    }

}
