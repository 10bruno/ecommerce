package br.com.ecommerce.golden;

import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Registrado via propriedade Hibernate (hibernate.session_factory.statement_inspector),
 * que instancia esta classe por reflexao -- por isso o estado precisa ser
 * estatico: nao ha como o teste segurar a instancia que o Hibernate cria.
 * As duas SessionFactory (Postgres e MySQL) compartilham a mesma lista
 * estatica, o que e aceitavel porque cada teste limpa a lista antes de
 * rodar e le logo em seguida, sem concorrencia entre metodos de teste.
 */
public class SqlCapturingStatementInspector implements StatementInspector {

    private static final List<String> CAPTURED = Collections.synchronizedList(new ArrayList<>());

    @Override
    public String inspect(String sql) {
        CAPTURED.add(sql);
        return sql;
    }

    static List<String> captured() {
        synchronized (CAPTURED) {
            return new ArrayList<>(CAPTURED);
        }
    }

    static void clear() {
        CAPTURED.clear();
    }

    static long countStatementsStartingWith(String keyword) {
        String upperKeyword = keyword.toUpperCase(Locale.ROOT);
        synchronized (CAPTURED) {
            return CAPTURED.stream()
                    .filter(sql -> sql.trim().toUpperCase(Locale.ROOT).startsWith(upperKeyword))
                    .count();
        }
    }
}
