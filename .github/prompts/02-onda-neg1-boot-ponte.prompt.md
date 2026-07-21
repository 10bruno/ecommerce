---
agent: 'agent'
description: 'Onda -1: sobe ate o ultimo patch da linha atual do Spring Boot (ponte)'
---
Execute a Onda -1: ponte até o último patch da linha atual do Spring Boot.

**Passo 1.** Confirme baseline verde com `clean build` antes de tocar
em qualquer coisa.

**Passo 2.** Descubra qual é o último patch da linha atual (ex: se o
projeto está em `3.3.0`, descubra o último `3.5.x` publicado —
consulte o `maven-metadata.xml` do Maven Central, não suponha o
número). Atualize a versão do plugin/BOM do Spring Boot para esse
patch.

**Passo 3.** Rode `clean build --warning-mode all` (não só `clean
build`) — o objetivo desta onda é justamente capturar os warnings de
depreciação que preveem o resíduo da onda seguinte (o salto major).
Liste os warnings encontrados no relatório final, mesmo os que não
quebram nada agora.

**Passo 4.** Resolva achados **pré-existentes** revelados pelos
warnings (ex: método de senha inseguro, API depreciada há anos) como
commits separados, claramente rotulados como "achado pré-existente,
não causado por esta onda" — não misture com a mudança de versão em
si.

**Se sobrar dúvida sobre alguma API nova:** não invente assinatura.
Busque na documentação oficial da versão exata, ou inspecione o jar
diretamente se o compilador reclamar de símbolo que sumiu sem
explicação na doc.

**Pare antes de commitar.** Reporte: versão antes/depois, warnings
capturados, achados pré-existentes resolvidos (se houver). Aguarde
confirmação.

---
**Próximo passo, após o merge confirmado:** `/03-onda-neg1b-mockbean` se
o serviço usar `@MockBean`/`@SpyBean` (o scanner já deveria ter
indicado isso); senão, pule direto para `/04-onda-1-java-minimo`.
