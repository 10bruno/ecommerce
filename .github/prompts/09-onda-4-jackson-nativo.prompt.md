---
agent: 'agent'
description: 'Onda 4: desliga a ponte Jackson 2, expoe comportamento nativo do Jackson 3'
---
Execute a Onda 4: desliga a ponte de compatibilidade do Jackson,
expondo o comportamento nativo do Jackson 3.

**Passo 1.** Confirme baseline verde com `clean build`.

**Passo 2.** Em `application.yml` e `application-test.yml`, mude:
```yaml
spring:
  jackson:
    use-jackson2-defaults: false
```
Não apague a linha — deixa explícito que foi desligado de propósito.

**Passo 3.** Rode a suíte inteira (`clean build`).

**Passo 4. Se algum golden file quebrar, isso pode ser o RESULTADO
ESPERADO desta onda** — o objetivo é justamente expor o comportamento
real do Jackson 3, antes mascarado pela ponte. Mesmo assim: NÃO edite
o `.json`/`.sql`/asserções para forçar passar. Reporte o diff exato
(esperado vs. capturado) para decisão humana sobre aceitar os novos
bytes como novo contrato.

**Passo 5.** Se nada quebrar, não aceite isso de bandeja — confirme
por que. A ponte controla apenas 4 features:
`WRITE_DATES_AS_TIMESTAMPS`, `WRITE_DURATIONS_AS_TIMESTAMPS`,
`FAIL_ON_UNKNOWN_PROPERTIES`, `DEFAULT_VIEW_INCLUSION`. Verifique se
os testes existentes realmente exercitam essas 4 (ex: existe teste
que envia campo JSON desconhecido? Existe `@JsonView` em uso?). Se
não existir teste para `FAIL_ON_UNKNOWN_PROPERTIES`, essa é uma
lacuna de cobertura real — escreva um teste que envie um campo extra
no JSON e confirme o comportamento (ignora ou lança exceção) antes de
considerar a onda concluída.

**⚠️ `FAIL_ON_UNKNOWN_PROPERTIES` é sensível a patch version do Boot
4.0.x** — há issue real do próprio Spring Boot
(`spring-projects/spring-boot#49951`) mostrando comportamento oposto
entre patches próximos. Não assuma que o resultado deste serviço vale
para outro sem teste próprio.

**Pare antes de commitar.** Reporte o resultado da suíte completa e,
se nada quebrou, a confirmação de que as 4 features foram de fato
exercitadas por teste real. Aguarde confirmação.

---
**Próximo passo:** esta é a última onda da sequência principal. Se
tudo passou, o serviço está no destino (Java 25 LTS + Boot 4.1 +
Jackson 3 nativo). Para migrar outro serviço, comece de novo por
`/00-scanner-diagnostico` nele.
