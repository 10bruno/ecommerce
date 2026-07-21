---
agent: 'agent'
description: 'Onda 1: Java para a versao minima exigida pelo Boot 4 (toolchain hermetico)'
---
Execute a Onda 1: sobe o Java para a versão mínima exigida pelo Spring
Boot 4 (21), usando toolchain hermético do Gradle — não depende de
`JAVA_HOME`/PATH/IDE de cada máquina.

**Passo 1.** Confirme baseline verde com `clean build`.

**Passo 2.** Em `settings.gradle`, adicione:
```groovy
plugins {
    id 'org.gradle.toolchains.foojay-resolver-convention' version '1.0.0'
}
```

**Passo 3.** Em `build.gradle`, **substitua** `sourceCompatibility`
(se existir) pelo bloco:
```groovy
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
```

**Passo 4.** Rode `clean build --info | grep "Compiling with toolchain"`
(ou equivalente `Select-String` no PowerShell) — confirme qual JDK
compilou de verdade. Se o Gradle precisar baixar um JDK novo (via
Foojay Disco API), isso indica que a rede desta máquina permite acesso
a `api.foojay.io` — reporte se baixou ou só detectou um já instalado,
é informação relevante para confirmar com o time de infra se outras
máquinas/CI têm o mesmo acesso.

**Se aparecer erro do Lombok** (`ExceptionInInitializerError`,
menção a `sun.misc.Unsafe`/`lombok.permit.Permit`): é incompatibilidade
de versão do Lombok com o JDK, não bug seu. Confira a versão declarada
— versões antigas (< 1.18.42) não suportam Java 21+ corretamente em
alguns casos. Atualize para uma versão mais recente do Lombok.

**Pare antes de commitar.** Reporte o resultado do build e se houve
download de JDK. Aguarde confirmação.

---
**Próximo passo, após o merge confirmado:** `/05-onda-1b-java-lts`
