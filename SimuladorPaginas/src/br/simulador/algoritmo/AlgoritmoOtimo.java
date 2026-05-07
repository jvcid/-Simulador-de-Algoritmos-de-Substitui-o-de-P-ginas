package br.simulador.algoritmo;

import br.simulador.modelo.PassoSimulacao;
import br.simulador.modelo.ResultadoSimulacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Algoritmo Ótimo (Belady).
 * <p>
 * Substitui a página que será utilizada mais longe no futuro.
 * Não é implementável na prática (requer conhecimento antecipado),
 * mas serve como referência teórica de desempenho mínimo de faltas.
 */
public class AlgoritmoOtimo implements AlgoritmoSubstituicao {

    @Override
    public ResultadoSimulacao simular(int[] paginas, int nQuadros) {
        int[] quadros = new int[nQuadros];
        Arrays.fill(quadros, -1);
        int posicoesPrenchidas = 0;

        List<PassoSimulacao> passos = new ArrayList<>();

        for (int i = 0; i < paginas.length; i++) {
            int pagina = paginas[i];
            boolean falta = !contemPagina(quadros, pagina);

            if (falta) {
                if (posicoesPrenchidas < nQuadros) {
                    quadros[posicoesPrenchidas++] = pagina;
                } else {
                    int indiceVitima = encontrarVitima(quadros, paginas, i);
                    quadros[indiceVitima] = pagina;
                }
            }

            passos.add(new PassoSimulacao(pagina, quadros, falta));
        }

        return new ResultadoSimulacao(getNome(), passos);
    }

    /**
     * Encontra o índice no array de quadros da página que será usada
     * mais longe no futuro (ou nunca), tornando-a a candidata a substituição.
     */
    private int encontrarVitima(int[] quadros, int[] paginas, int posicaoAtual) {
        int[] proximoUso = new int[quadros.length];

        for (int r = 0; r < quadros.length; r++) {
            proximoUso[r] = Integer.MAX_VALUE; // assume que não será usada
            for (int j = posicaoAtual + 1; j < paginas.length; j++) {
                if (paginas[j] == quadros[r]) {
                    proximoUso[r] = j;
                    break;
                }
            }
        }

        // Retorna o índice com maior próximo uso (mais distante ou nunca usada)
        int indiceVitima = 0;
        for (int r = 1; r < quadros.length; r++) {
            if (proximoUso[r] > proximoUso[indiceVitima]) indiceVitima = r;
        }
        return indiceVitima;
    }

    @Override
    public String getNome() { return "Ótimo"; }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private boolean contemPagina(int[] quadros, int pagina) {
        for (int q : quadros) if (q == pagina) return true;
        return false;
    }
}
