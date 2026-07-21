---
agent: 'agent'
description: 'Onda 3: salto para Spring Boot 4.1 (a onda principal)'
---
Execute a Onda 3: salto para Spring Boot 4.1.

**⚠️ Pré-requisito não negociável:** confirme que as Ondas 2a e 2b (golden
files + Testcontainers) já existem e passam. Sem isso, PARE e reporte
— não prossiga sem rede de segurança.

**Passo 1.** Confirme baseline verde com `clean build`.

**Passo 2.** Adicione temporariamente ao `build.gradle`:
```groovy
rewrite {
    activeRecipe("org.openrewrite.java.spring.boot4.UpgradeSpringBoot_4_0")
}
dependencies {
    rewrite("org.openrewrite.recipe:rewrite-spring:latest.release")
}
```
**Nota de licença:** `rewrite-spring` está sob Moderne Source
Available License, não Apache. Confirme com seu jurídico/compliance
antes de aplicar isto em qualquer serviço real — não assuma aprovado
só por este prompt existir.

**Passo 3.** Rode `./gradlew rewriteRun` (não dryRun — já é a
aplicação real). A recipe provavelmente mira a última minor conhecida
por ela (ex: 4.0.7), não a mais recente estável — depois de rodar,
confirme a versão realmente estável via `maven-metadata.xml` do Maven
Central e ajuste manualmente para ela.

**Passo 4.** Confira a versão do Lombok após a recipe rodar — ela pode
ter mudado (subido ou descido). Não assuma, verifique.

**Passo 5.** Remova a dependência `rewrite-spring` e a `activeRecipe`
temporária; restaure a recipe própria (`MigrateMockBeanToMockitoBean`,
se existir) ou remova o bloco `rewrite` inteiro se não precisar mais.
Fixe a versão do plugin `org.openrewrite.rewrite` na versão real
resolvida (não deixe `latest.release` permanente — quebra
reprodutibilidade).

**Passo 6.** Adicione a ponte de compatibilidade em
`application.yml`/`application-test.yml`:
```yaml
spring:
  jackson:
    use-jackson2-defaults: true
```
(desliga isso é a Onda 4, separada — não desligue aqui)

**Passo 7.** Compile e resolva o resíduo. Esperado:
- `MigrateToModularStarters` (maior volume): starters reorganizados
- Pacotes movidos sem aviso em release note — se o compilador reclamar
  de símbolo que sumiu e a doc não explicar, inspecione o jar da nova
  versão diretamente
- Property renomeada (`server.error.include-stacktrace` pode virar
  `spring.web.error.include-stacktrace`)
- `WWW-Authenticate` ganha `, charset="UTF-8"` (Security 7) — ajuste a
  asserção do teste, não a aplicação

**Passo 8. CRÍTICO — se golden files quebrarem no round-trip (não na
serialização):** é provável que seja o bug real e confirmado do
Jackson 3 em detecção de creator implícito para `@Data @Builder` sem
anotação explícita (`InvalidDefinitionException: no Creators, like
default constructor, exist`). A ponte `use-jackson2-defaults` **não
cobre isso**. PARE, reporte o diff exato, e proponha a correção — não
aplique sozinho sem confirmação:
```java
@Data
@Builder
@NoArgsConstructor    // as duas juntas, sempre
@AllArgsConstructor   // uma sozinha quebra a compilacao do @Builder
public class MeuDto { ... }
```

**Pare antes de commitar.** Reporte: diff completo, resultado do
build, cada achado classificado (mecânico vs. quebra de contrato vs.
pré-existente). Aguarde confirmação.

---
**Próximo passo, após o merge confirmado:** `/09-onda-4-jackson-nativo`
