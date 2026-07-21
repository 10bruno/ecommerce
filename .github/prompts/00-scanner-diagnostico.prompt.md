---
agent: 'agent'
description: 'Diagnostico do servico: arquetipo, integracoes, riscos (Camada 1+2)'
---
Diagnostique este repositório antes de qualquer onda de upgrade.

**Passo 1 — varredura determinística (Camada 1).** Percorra o projeto e
extraia:
- Build: Maven ou Gradle, versão do Boot, versão do Java, versão do
  Gradle wrapper
- Stack web: `starter-web` vs `starter-webflux` vs nenhum
- Integrações: procure por Kafka, Oracle, Postgres, MySQL, Mongo, AWS
  SDK, Redis nas dependências declaradas
- Sinais de risco: conte imports `javax.persistence`/`javax.servlet`/
  `javax.validation`/`javax.annotation`/`javax.transaction` (relevantes
  para Jakarta EE — **não** conte `javax.sql`/`javax.crypto`/`javax.net`/
  `javax.management`, que são do JDK e nunca migram)
- Arquivos com múltiplos `@Bean` retornando `DataSource` (sinal de
  multi-datasource)
- `new ObjectMapper()` fora de `@Configuration`
- `embedded-kafka`, Mongo embarcado, ou H2 em escopo de teste

**Passo 2 — interpretação.** Com os fatos brutos do passo 1, escreva
um resumo em prosa: qual o arquétipo deste serviço (ex:
`mvc-jpa-multi-datasource`, `webflux-kafka`, `mvc-jpa-mongo`), quais
integrações reais existem, quais riscos merecem atenção antes de
migrar.

**Regras:**
- Toda afirmação cita evidência (arquivo:linha). Se não tem certeza,
  marque como "não determinado" — não invente.
- Não infira criticidade de negócio — isso vem de um humano.
- `@Primary`/`@Qualifier` são mecanismo de resolução do Spring, não
  indicam importância de negócio — não interprete como tal.

**Passo 3 — determine o PRÓXIMO PASSO REAL, não uma lista genérica.**
Percorra esta checklist NA ORDEM e pare no primeiro item que ainda não
está feito — esse é o comando a recomendar. Não recomende uma onda que
já está concluída, mesmo que ela exista na sequência.

| Onda | Já está feito se... | Comando |
|---|---|---|
| 0 | `gradle-wrapper.properties` já aponta para >= 8.14 | `/01-onda-0-gradle` |
| -1 | Boot já está numa versão 4.x (não precisa mais de ponte 3.x) | `/02-onda-neg1-boot-ponte` |
| -1b | Nenhum `@MockBean`/`@SpyBean` encontrado no projeto | `/03-onda-neg1b-mockbean` |
| 1 | Toolchain Java já declarado >= 21 | `/04-onda-1-java-minimo` |
| 1b | Toolchain Java já declarado = 25 | `/05-onda-1b-java-lts` |
| 2a | Existe pelo menos um teste `@JsonTest`/`JacksonTester` com round-trip para os DTOs de request | `/06-onda-2a-golden-files` |
| 2b | Existe teste `@Testcontainers` real (não embedded) para cada integração de dados encontrada | `/07-onda-2b-testcontainers` |
| 3 | Boot já está em 4.1.x (não só 4.0.x) | `/08-onda-3-boot-major` |
| 4 | `spring.jackson.use-jackson2-defaults` está ausente OU explicitamente `false` | `/09-onda-4-jackson-nativo` |

Se TODOS os itens já estiverem feitos, diga isso claramente — não
invente uma onda para recomendar. "Este serviço já está no destino
(Java 25 + Boot 4.1 + Jackson 3 nativo); nenhuma onda pendente" é uma
resposta válida e esperada, não uma falha do diagnóstico.

**Saída:** um resumo estruturado (pode ser um comentário no chat, não
precisa criar arquivo) com: arquétipo, build atual, integrações
encontradas, riscos identificados, **e o único próximo comando
recomendado** (não uma lista de possibilidades) — resultado do Passo 3.

Não edite nenhum arquivo neste passo — é só leitura e diagnóstico.
