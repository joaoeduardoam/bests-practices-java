package br.com.alura.adopet.api.record;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;

import java.util.List;
import java.util.stream.Collectors;

public record DadosDetalhesAbrigo(Long id, String nome, String email, String telefone
                                  , List<DadosMinPet> pets
        //, List<Long> idsPets
        )
{

    public DadosDetalhesAbrigo(Abrigo abrigo) {
        this(abrigo.getId(), abrigo.getNome(), abrigo.getEmail(), abrigo.getTelefone()
                , abrigo.getPets().stream().map(DadosMinPet::new).toList()
//                , abrigo.getPets().stream()
//                .map(Pet::getId)
//                .collect(Collectors.toList())
        );
    }
}

