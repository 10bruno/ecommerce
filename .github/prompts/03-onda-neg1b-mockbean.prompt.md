---
agent: 'agent'
description: 'Onda -1b: migra @MockBean/@SpyBean para @MockitoBean/@MockitoSpyBean'
---
Execute a Onda -1b: migração de `@MockBean`/`@SpyBean` (deprecados
desde Boot 3.4.0, remoção prevista para 4.0.0) para
`@MockitoBean`/`@MockitoSpyBean`.

**Passo 1.** Confirme baseline verde com `clean build`.

**Passo 2.** Verifique se o repositório usa `@MockBean` ou `@SpyBean`
em algum teste. Se não usar nenhum, reporte isso e pare — onda não se
aplica a este serviço.

**Passo 3.** Se `build.gradle` ainda não tiver o plugin do
OpenRewrite, adicione:
```groovy
plugins {
    id 'org.openrewrite.rewrite' version '6.16.3'
}
```

**Passo 4.** Crie (se ainda não existir) `rewrite.yml` na raiz do
projeto com este conteúdo exato:
```yaml
---
type: specs.openrewrite.org/v1beta/recipe
name: br.com.empresa.rewrite.MigrateMockBeanToMockitoBean
displayName: Migrar MockBean/SpyBean para MockitoBean/MockitoSpyBean
recipeList:
  - org.openrewrite.java.ChangeType:
      oldFullyQualifiedTypeName: org.springframework.boot.test.mock.mockito.MockBean
      newFullyQualifiedTypeName: org.springframework.test.context.bean.override.mockito.MockitoBean
  - org.openrewrite.java.ChangeType:
      oldFullyQualifiedTypeName: org.springframework.boot.test.mock.mockito.SpyBean
      newFullyQualifiedTypeName: org.springframework.test.context.bean.override.mockito.MockitoSpyBean
```
Ajuste `br.com.empresa` para o pacote real da sua organização se
quiser manter convenção — não é obrigatório, o nome da recipe é livre.

**Passo 5.** Ative a recipe:
```groovy
rewrite {
    activeRecipe("br.com.empresa.rewrite.MigrateMockBeanToMockitoBean")
}
```

**Passo 6.** Rode `./gradlew rewriteDryRun` primeiro, leia o relatório
gerado. Se o diff parecer correto (só troca de import + anotação),
rode `./gradlew rewriteRun` para aplicar de verdade.

**Passo 7.** Rode a suíte **inteira** (`clean build`), não só os
arquivos tocados — há diferença comportamental documentada entre as
duas anotações em cenários raros; só a suíte completa garante que não
apareceu aqui.

**Nota de licença:** esta recipe usa só `org.openrewrite.java.ChangeType`
(núcleo do OpenRewrite, Apache 2.0) — não depende do módulo
`rewrite-spring`, que tem pendência de licença (Moderne Source
Available) ainda não confirmada juridicamente para uso comercial.

**Pare antes de commitar.** Reporte o diff e o resultado do build
completo. Aguarde confirmação.

---
**Próximo passo, após o merge confirmado:** `/04-onda-1-java-minimo`
