---
agent: 'agent'
description: 'Onda 2a: golden files de serializacao (round-trip) antes do Boot major'
---
Execute a Onda 2a: rede de segurança de serialização, obrigatória
antes de qualquer tentativa de subir o Spring Boot major.

**Passo 1.** Identifique os DTOs em risco: classes usadas como
`@RequestBody` em endpoints `POST`/`PUT`, especialmente as anotadas
`@Data @Builder` (Lombok) sem nenhuma anotação Jackson explícita
(`@JsonCreator`, `@Jacksonized`, etc.) — esse padrão é o que mais
frequentemente quebra na migração para Jackson 3.

**Passo 2.** Para cada DTO identificado, escreva um teste `@JsonTest`
com `JacksonTester`, cobrindo **sempre os dois sentidos**:
```java
@JsonTest
class XGoldenTest {
    @Autowired private JacksonTester<X> json;

    @Test
    void serializacao_naoMudou() throws Exception {
        assertThat(json.write(fixture())).isEqualToJson(new ClassPathResource("golden/x.json"));
    }

    @Test
    void roundTrip_preservaTiposEValores() throws Exception {
        var jsonContent = json.write(fixture()).getJson();
        var desserializado = json.parseObject(jsonContent);
        // asserts campo a campo -- para BigDecimal use isEqualByComparingTo, nao isEqualTo
    }
}
```
**O teste de round-trip não é opcional.** Ele existe porque há bug
real e confirmado no Jackson 3 (creator implícito quebrado para
`@Data @Builder`) que só quebra na desserialização — a serialização
continua idêntica. Um golden file sem round-trip não detecta isso.

**Passo 3.** Nunca chute o JSON esperado. Escreva primeiro um teste de
descoberta (imprime o JSON real gerado), rode, confirme o formato real
(datas, decimais), só então fixe o arquivo `golden/x.json` definitivo
e apague o teste de descoberta.

**No Windows, ao criar os arquivos `.json`:** não use `Out-File
-Encoding utf8` do PowerShell — insere BOM e quebra o parser JSON.
Use `[System.IO.File]::WriteAllText(caminho, conteudo,
[System.Text.UTF8Encoding]::new($false))`.

**Pare antes de commitar.** Reporte quais DTOs foram cobertos e o
resultado do build. Aguarde confirmação.

---
**Próximo passo, após o merge confirmado:** `/07-onda-2b-testcontainers`
