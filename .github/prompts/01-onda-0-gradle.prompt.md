---
agent: 'agent'
description: 'Onda 0: bump do Gradle wrapper para versao que suporta o plugin do Boot 4'
---
Execute a Onda 0: bump do Gradle wrapper.

**Passo 1.** Rode `./gradlew clean build` (ou `mvnw clean verify` se for
Maven — se for Maven, pare e reporte, essa onda é específica de Gradle)
e confirme baseline verde antes de tocar em qualquer coisa. Se o
baseline já estiver quebrado, PARE e reporte — não prossiga.

**Passo 2.** Atualize `gradle/wrapper/gradle-wrapper.properties` para
Gradle 8.14 ou mais recente (o plugin do Spring Boot 4 exige no mínimo
8.14). Comando:
```
./gradlew wrapper --gradle-version 8.14 --distribution-type bin
```
No Windows, a primeira execução pode gerar erro de terminal por
autossobrescrita do `.bat` — se aparecer erro após "BUILD SUCCESSFUL",
rode `./gradlew --version` limpo para confirmar se a versão nova
realmente aplicou antes de assumir falha.

**Passo 3.** Rode `./gradlew clean build` de novo (nunca `--rerun` —
essa flag não existe no Gradle, é `--rerun-tasks`, e pode ser aceita
silenciosamente sem forçar nada).

**Se algum teste falhar nesta onda:** antes de assumir que a onda
causou, pergunte se o teste deveria depender do que falhou. Testes de
regra de negócio/segurança/HTTP que falham por infraestrutura ausente
(Docker parado, por exemplo) geralmente são teste mal escopado
pré-existente (`@SpringBootTest` genérico demais), não causado por
esta onda — reduza o escopo (`@WebMvcTest` + `@MockBean`/`@MockitoBean`
dos serviços injetados + `@Import` de qualquer config de segurança
customizada) em vez de assumir regressão.

**Pare antes de commitar.** Reporte: build antes/depois, versão do
Gradle antes/depois, qualquer teste que precisou de ajuste e por quê.
Aguarde confirmação para `git add`/`commit`/`push`.

---
**Próximo passo, após o merge confirmado:** `/02-onda-neg1-boot-ponte`
