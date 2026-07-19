# AGENTS.md

Regras para qualquer agente de IA trabalhando neste repositório durante o
programa de upgrade Java 25 LTS + Spring Boot 4.1.

Leia este arquivo por inteiro no início de toda sessão.

---

## Contexto obrigatório

Antes de escrever qualquer código relacionado a Spring Boot 4, Spring
Framework 7 ou Jackson 3, leia:

- `context/target-versions.md`
- `context/breaking-changes/` — o arquivo relevante à mudança em questão

**Você não tem conhecimento confiável destas versões.** Elas são posteriores
ao seu corte de treinamento. Assinaturas de método, nomes de pacote e
propriedades de configuração que você "lembra" provavelmente estão erradas.

---

## Invioláveis

1. **Uma onda por PR.** Nunca misture upgrade de Java com upgrade de Spring
   no mesmo commit. Se o p99 degradar, é preciso saber qual dos dois causou.

2. **Nunca altere lógica de negócio** no mesmo PR de um upgrade.

3. **Se um golden file quebrar: PARE.** Reporte o diff exato, campo a campo.
   **NUNCA atualize o arquivo `.json` para o teste passar.**
   Um golden file quebrado significa que os bytes publicados em um tópico
   Kafka mudaram. Isso é um incidente de contrato, não uma falha de teste.

4. **Nunca edite um teste para fazê-lo passar.** Se um teste falha, ou o
   código está errado, ou o teste revelou uma mudança de comportamento real.
   Ambos os casos exigem decisão humana. Reporte e pare.

5. **Não invente API.** Se você não tem certeza da assinatura de um método
   de Boot 4 / Jackson 3 / Security 7, consulte `context/breaking-changes/`
   ou busque na documentação oficial. Não adivinhe. Não "provavelmente é".

6. **Rode o build antes de afirmar que terminou.** `mvn verify` (ou
   `./gradlew build`). Sem exceção.

7. **Não altere configuração do `ObjectMapper` / `JsonMapper`** para fazer
   um teste passar. Essa configuração é contrato.

8. **Nunca remova um teste.** Nunca adicione `@Disabled` / `@Ignore`.

9. **Limite de tentativas: 3.** Se após três tentativas o build não passar,
   pare, reporte o que tentou e o erro atual. Não continue tentando variações.

10. **Antes de qualquer onda — inclusive as de toolchain — rode o build uma
    vez para confirmar baseline verde.** Uma onda que não muda versão de
    dependência ainda pode expor falha pré-existente (infraestrutura fora
    do ar, teste mal escopado). Sem baseline confirmado, toda falha
    pós-onda vira suspeita da onda, mesmo quando não é.

11. **Quando um teste falha por infraestrutura ausente (banco, fila),
    primeiro pergunte se o teste deveria depender daquela infraestrutura.**
    - Se o teste verifica lógica de negócio, regra de segurança, ou
      contrato HTTP — **não deveria**. `@SpringBootTest` genérico sobe o
      contexto inteiro por padrão, incluindo `@Configuration` de
      persistência que o teste não usa. Reduza o escopo: `@WebMvcTest`
      com as classes de controller explícitas, `@MockBean` para cada
      serviço injetado, `@Import` explícito de configs de segurança
      customizadas (elas não entram automaticamente no component scan
      reduzido do slice).
    - Se o teste verifica comportamento real de persistência (dialeto,
      schema, query gerada) — **deveria**. A correção aqui é
      Testcontainers, nunca remover a dependência.
    - Nunca "resolva" isso convertendo o teste em mock do banco quando o
      propósito do teste era validar o banco de verdade.

12. **Quando um warning de build sugerir uma flag de diagnóstico** (ex:
    "Recompile with -Xlint:deprecation for details", "Run with
    --stacktrace", "use --warning-mode all") **aplique-a automaticamente
    para capturar o detalhe — não pare no warning genérico.** Procedimento:
    - Prefira a via menos invasiva (flag de linha de comando) quando o
      build tool suportar passthrough direto.
    - Se só for possível via edição do arquivo de build (caso comum em
      Gradle, que não tem passthrough universal para `compilerArgs`),
      aplique a edição, capture o output completo, e **reverta a edição
      imediatamente depois** — antes de qualquer commit. A alteração
      diagnóstica nunca deve aparecer no diff da onda.
    - Classifique cada warning revelado em uma de três categorias:
      **(a) relacionado à onda atual** — resolve agora, dentro do escopo;
      **(b) pré-existente, não relacionado ao upgrade** — registra em
      `nao_determinado`/`riscos` da ficha do serviço, não resolve na
      mesma onda a menos que seja trivial e sem risco;
      **(c) relacionado a onda futura** — registra em
      `context/breaking-changes/` ou na ficha do serviço, não resolve
      agora, mas o diário deve mencionar que já foi previsto.
    - Nunca ignore o warning original só porque o build passou. "Verde"
      não significa "sem achado relevante" — a Onda -1/3.5.x existe
      justamente para revelar esses achados com antecedência.

---

## Ordem de execução

O trabalho mecânico é feito por **OpenRewrite**, não por você.

```
1. OpenRewrite roda e commita       (determinístico, auditável)
2. Build                             (oráculo)
3. VOCÊ trabalha apenas no resíduo   (o que não compilou / não passou)
```

Nunca reescreva à mão algo que uma recipe do OpenRewrite resolve.
Se você suspeita que existe uma recipe, pergunte antes de editar.

---

## Diário de bordo

Ao final de **toda** sessão, anexe a `diario-de-bordo.md`:

```markdown
## <data> — <etapa>

**Escopo:** o que foi tocado
**Tempo:** quanto durou
**OpenRewrite resolveu:** <lista>
**Resíduo que eu resolvi:** <lista>
**Resíduo que exigiu humano:** <lista>
**Golden files que quebraram:** <lista + diff>
**Tentei burlar algum teste?** sim/não — se sim, descreva
**Surpresas:** o que não estava documentado em context/
```

O campo "surpresas" é o mais importante do arquivo. Ele alimenta
`context/breaking-changes/` para os próximos serviços.

---

## Quando parar e perguntar ao humano

- Um golden file mudou
- O SQL gerado pelo Hibernate mudou
- Uma métrica do Micrometer sumiu ou foi renomeada
- Uma dependência transitiva não tem versão compatível
- O parent POM corporativo precisa mudar
- Você precisaria mudar um contrato de API ou de evento
- Qualquer coisa em que você consideraria escrever "provavelmente"
