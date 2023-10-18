package com.bispo.crud.controller;

import com.bispo.crud.entity.PessoaEntity;
import com.bispo.crud.service.PessoaService;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/pessoas")
public class PessoaController<Pessoa> {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarPessoas(@RequestBody PessoaEntity pessoa) {
        try {
            PessoaEntity pessoaCadastrada = pessoaService.cadastrarPessoas(pessoa);
            return new ResponseEntity<>(pessoaCadastrada, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao cadastrar a pessoa: " + e.getMessage());
        }
    }
    @GetMapping("/documento")
    public ResponseEntity<PessoaEntity> obterPessoaPorDocumento(@RequestParam("tipo") String tipo, @RequestParam("numero") String numero) {
        Optional<PessoaEntity> pessoa = pessoaService.buscaPessoaPorDocumento(tipo, numero);
        return pessoa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/documento")
    public ResponseEntity<Void> deletarPessoaPorDocumento(@RequestParam("tipo") String tipo, @RequestParam("numero") String numero) {
        boolean deleted = pessoaService.deletarPessoaPorDocumento(tipo, numero);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
