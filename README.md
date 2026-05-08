# Simulador de Substituição de Páginas

Simulador gráfico (Java Swing) para algoritmos de substituição de páginas de memória virtual.

## Estrutura do projeto

```
SimuladorPaginas/
├── .idea/                          ← Configurações do IntelliJ IDEA
│   ├── misc.xml
│   └── modules.xml
├── src/
│   └── br/simulador/
│       ├── Main.java               ← Ponto de entrada (EDT + JanelaPrincipal)
│       │
│       ├── algoritmo/
│       │   ├── AlgoritmoSubstituicao.java   ← Interface (contrato)
│       │   ├── AlgoritmoFIFO.java           ← Implementação FIFO
│       │   ├── AlgoritmoLRU.java            ← Implementação LRU
│       │   ├── AlgoritmoOtimo.java          ← Implementação Ótimo (Belady)
│       │   └── AlgoritmoRelogio.java        ← Implementação Relógio (Clock)
│       │
│       ├── modelo/
│       │   ├── PassoSimulacao.java          ← Estado dos quadros em um acesso
│       │   └── ResultadoSimulacao.java      ← Resultado completo (passos + faltas)
│       │
│       └── gui/
│           ├── Tema.java                    ← Constantes de cores e fontes
│           ├── JanelaPrincipal.java         ← Janela principal (composição dos painéis)
│           ├── PainelGrafico.java           ← Gráfico de barras (Graphics2D)
│           └── PainelTabela.java            ← Tabela passo a passo por algoritmo
│
├── SimuladorPaginas.iml            ← Módulo IntelliJ
├── SimuladorPaginas.jar            ← JAR executável
└── README.md
```

## Algoritmos implementados

| # | Classe | Algoritmo | Descrição |
|---|--------|-----------|-----------|
| 1 | `AlgoritmoFIFO` | FIFO | Substitui a página mais antiga (fila de chegada) |
| 2 | `AlgoritmoLRU` | LRU | Substitui a menos recentemente usada |
| 3 | `AlgoritmoOtimo` | Ótimo (Belady) | Substitui a que será usada mais tarde no futuro |
| 4 | `AlgoritmoRelogio` | Relógio (Clock) | Segunda chance com ponteiro circular e bit de uso |

## Como abrir no IntelliJ IDEA

1. **Abrir projeto**: `File → Open` → selecione a pasta `SimuladorPaginas`
2. O IntelliJ reconhece automaticamente o `.iml` e o diretório `src`
3. Verifique o SDK em `File → Project Structure → Project SDK` (JDK 11+ recomendado)
4. Execute a classe `br.simulador.Main`

## Como executar pelo JAR

```bash
java -jar SimuladorPaginas.jar
```

## Como compilar manualmente

```bash
# Na raiz do projeto
javac -d out $(find src -name "*.java")
java -cp out br.simulador.Main
```

## Como usar a interface

1. Edite a **cadeia de referência** (números inteiros separados por espaço)
2. Escolha o número de **quadros de memória** (1–10)
3. Clique em **Simular**

### O que é exibido
- **Cards superiores** — faltas de página por algoritmo
- **Gráfico de barras** — comparativo visual dos 4 algoritmos
- **Abas de tabela** — estado dos quadros em cada acesso (falta destacada em cor)

## Exemplo de saída (cadeia padrão, 3 quadros)

```
=== Resultado da Simulação ===
FIFO     → 15 falta(s) de página
LRU      → 12 falta(s) de página
Ótimo    →  9 falta(s) de página
Relógio  → 14 falta(s) de página
```

## Dupla

> João Victor Martins Cid & Aaron Magno Campos Nogueira
