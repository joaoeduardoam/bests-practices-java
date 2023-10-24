package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Abrigo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AbrigoRepository extends JpaRepository<Abrigo, Long> {

    Abrigo findByNome(String nome);

    boolean existsByTelefoneOrEmailOrNome(String telefone, String email, String nome);
}
