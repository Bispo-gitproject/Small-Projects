package com.bispo.crud.service;

import com.bispo.crud.entity.PessoaEntity;
import com.bispo.crud.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public PessoaEntity cadastrarPessoas(PessoaEntity pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Optional<PessoaEntity> buscaPessoaPorDocumento(String tipo, String numero) {
        return pessoaRepository.findByTipoAndNumero(tipo, numero);
    }

    public boolean deletarPessoaPorDocumento(String tipo, String numero) {
        Optional<PessoaEntity> pessoa = pessoaRepository.findByTipoAndNumero(tipo, numero);
        if (pessoa.isPresent()) {
            pessoaRepository.delete(pessoa.get());
            return true;
        }
        return false;
    }
}
