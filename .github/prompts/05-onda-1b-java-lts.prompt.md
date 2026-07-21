---
agent: 'agent'
description: 'Onda 1b: Java para o LTS alvo final (25)'
---
Execute a Onda 1b: sobe o Java do mínimo intermediário (21) para o LTS
alvo final (25).

**Passo 1.** Confirme baseline verde com `clean build`.

**Passo 2.** Em `build.gradle`, altere:
```groovy
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}
```

**Passo 3.** Rode `clean build`. **Diferente da Onda 1**, é provável
que não exista JDK 25 pré-instalado na máquina — espere o Gradle
baixar de verdade via Foojay Disco API. Se falhar por rede bloqueada,
reporte claramente — é achado de infraestrutura, não bug de código.

**Se aparecer `ExceptionInInitializerError` do Lombok:** confirme a
versão declarada. Lombok < 1.18.42 não compila sob Java 25. Atualize
para 1.18.42 ou mais recente (validado: 1.18.42 e 1.18.46 funcionam).
As duas ocorrências (`compileOnly` e `annotationProcessor`) precisam
estar na mesma versão.

**Nota:** há bug residual conhecido, não totalmente corrigido, em
combinações `@Value`/`@Builder` + inicialização de construtor default
sob JDK 25 (`projectlombok/lombok#3981`). Se aparecer erro de
"variable not initialized in default constructor" mesmo com Lombok
atualizado, é isso — reporte e não tente forçar workaround sem
confirmar com um humano.

**Pare antes de commitar.** Reporte o resultado do build, se baixou
JDK, e a versão final do Lombok. Aguarde confirmação.

---
**Próximo passo, após o merge confirmado:** `/06-onda-2a-golden-files`
