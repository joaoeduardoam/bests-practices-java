package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;

public record DadosDetalhesAbrigo(Long id, String nome, String email, String telefone) {

    public DadosDetalhesAbrigo(Abrigo abrigo) {
        this(abrigo.getId(), abrigo.getNome(), abrigo.getEmail(), abrigo.getTelefone());
    }
}

