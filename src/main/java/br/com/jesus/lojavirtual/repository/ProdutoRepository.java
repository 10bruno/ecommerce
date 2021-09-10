package br.com.jesus.lojavirtual.repository;

import br.com.jesus.lojavirtual.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
}