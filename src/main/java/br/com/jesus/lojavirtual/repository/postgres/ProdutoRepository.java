package br.com.jesus.lojavirtual.repository.postgres;

import br.com.jesus.lojavirtual.domain.entity.postgres.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
}