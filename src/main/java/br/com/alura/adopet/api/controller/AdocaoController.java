package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.record.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.service.AdocaoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;


    @PostMapping
    @Transactional
    public ResponseEntity solicitar(@RequestBody @Valid SolicitacaoAdocaoDTO dto, UriComponentsBuilder uriBuilder) {
        try{
            var adocaoSolicitada = adocaoService.solicitar(dto);
            var uri = uriBuilder.path("/adocoes/{id}").buildAndExpand(adocaoSolicitada.id()).toUri();
            return ResponseEntity.created(uri).body(adocaoSolicitada);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhesAdocao>> listar() {

        var adocoes = adocaoService.listarAdocoes();

        return ResponseEntity.ok(adocoes);
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity aprovar(@RequestBody @Valid AprovacaoAdocaoDTO dto) {
        try{
        var adocaoAprovada = adocaoService.aprovar(dto);
        return ResponseEntity.ok(adocaoAprovada);
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("Não foi encontrado adoção com os dados informados!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity reprovar(@RequestBody @Valid ReprovacaoAdocaoDTO dto) {
        try{
            var adocaoReprovada = adocaoService.reprovar(dto);
            return ResponseEntity.ok(adocaoReprovada);
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("Não foi encontrado adoção com os dados informados!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){

        var adocao = adocaoService.getAdocaoById(id);

        return ResponseEntity.ok(new DadosDetalhesAdocao(adocao));

    }

}
