package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;

public record DadosDetalhesPet(Long id, TipoPet tipo, String nome, String raca, Integer idade, Boolean adotado, Long idAbrigo) {

    public DadosDetalhesPet(Pet pet) {
        this(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getAdotado(), pet.getAbrigo().getId());
    }

}

