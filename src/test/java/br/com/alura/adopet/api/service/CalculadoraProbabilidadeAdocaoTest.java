package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.record.CadastrarAbrigoDTO;
import br.com.alura.adopet.api.record.CadastrarPetDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraProbabilidadeAdocaoTest {

    @Test
    void deveriaRetornarProbabilidadeAltaParaPetComBaixoPesoEBaixaIdade(){
        // idade 4 anos e 4kg - ALTA

        Abrigo abrigo = new Abrigo(new CadastrarAbrigoDTO(
                "Abrigo Feliz",
                "86999998888",
                "abrigofeliz@email.com.br"
        ));

        Pet pet = new Pet(new CadastrarPetDTO(
                TipoPet.GATO,
                "Miau",
                "Siames",
                "Cinza",
                4,
                4.0f

        ), abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);

    }

    @Test
    void deveriaRetornarProbabilidadeMediaParaPetComBaixoPesoEAltaIdade(){
        // idade 15 anos e 4kg - ALTA

        Abrigo abrigo = new Abrigo(new CadastrarAbrigoDTO(
                "Abrigo Feliz",
                "86999998888",
                "abrigofeliz@email.com.br"
        ));

        Pet pet = new Pet(new CadastrarPetDTO(
                TipoPet.GATO,
                "Miau",
                "Siames",
                "Cinza",
                15,
                4.0f

        ), abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);

    }

}