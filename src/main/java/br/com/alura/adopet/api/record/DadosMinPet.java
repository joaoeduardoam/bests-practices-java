package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;

public record DadosMinPet(Long id, String nome) {

    public DadosMinPet(Pet pet) {
        this(pet.getId(), pet.getNome());
    }

}

