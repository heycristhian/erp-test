package br.com.senior.erp.usecase;

import br.com.senior.erp.controller.dto.filter.ProdutoFilter;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.exception.EntidadeNotFoundException;
import br.com.senior.erp.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BuscarProduto {

    private final ProdutoRepository produtoRepository;

    public BuscarProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Page<Produto> todos(ProdutoFilter produtoFilter, Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto porId(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Produto nao encontrado para o id: " + id));
    }
}
