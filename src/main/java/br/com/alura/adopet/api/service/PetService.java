package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.record.CadastrarPetDTO;
import br.com.alura.adopet.api.record.DadosDetalhesPet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {


    @Autowired
    private PetRepository petRepository;



    @Autowired
    private AbrigoService abrigoService;

    @Autowired
    private AbrigoRepository abrigoRepository;

    public List<DadosDetalhesPet> listarPetsDisponíveis() {
        List<Pet> disponiveis = petRepository.findAllByAdotadoFalse();
        List<DadosDetalhesPet> disponiveisDTO = disponiveis.stream().map(DadosDetalhesPet::new).toList();
        return disponiveisDTO;

    }

    public DadosDetalhesPet cadastrarPet(String idOuNome, CadastrarPetDTO dto) {

        Abrigo abrigo = abrigoService.getAbrigoByIdOuNome(idOuNome);
        Pet pet = new Pet(dto, abrigo);
        abrigo.getPets().add(pet);
        petRepository.save(pet);
        return new DadosDetalhesPet(pet);

    }


    public DadosDetalhesPet detalharPet(Long id) {

        return new DadosDetalhesPet(petRepository.getReferenceById(id));

    }
}
