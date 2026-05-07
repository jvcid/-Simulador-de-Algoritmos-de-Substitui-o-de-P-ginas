package br.simulador.algoritmo;

import br.simulador.modelo.PassoSimulacao;
import br.simulador.modelo.ResultadoSimulacao;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Algoritmo FIFO (First In, First Out).
 * <p>
 * A página mais antiga na memória é substituída quando ocorre uma falta.
 * Mantém uma fila de chegada para determinar a vítima.
 */
public class AlgoritmoFIFO implements AlgoritmoSubstituicao {

    @Override
    public ResultadoSimulacao simular(int[] paginas, int nQuadros) {
        int[] quadros = new int[nQuadros];
        Arrays.fill(quadros, -1);

        Deque<Integer> filaDeFila = new ArrayDeque<>();  // ordem de chegada
        List<PassoSimulacao> passos = new ArrayList<>();

        for (int pagina : paginas) {
            boolean falta = !contemPagina(quadros, pagina);

            if (falta) {
                if (filaDeFila.size() < nQuadros) {
                    // Ainda há espaço livre
                    int posLivre = indiceLivre(quadros);
                    quadros[posLivre] = pagina;
                    filaDeFila.addLast(pagina);
                } else {
                    // Remove a página mais antiga
                    int vitima = filaDeFila.removeFirst();
                    quadros[indiceDePagina(quadros, vitima)] = pagina;
                    filaDeFila.addLast(pagina);
                }
            }

            passos.add(new PassoSimulacao(pagina, quadros, falta));
        }

        return new ResultadoSimulacao(getNome(), passos);
    }

    @Override
    public String getNome() { return "FIFO"; }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private boolean contemPagina(int[] quadros, int pagina) {
        for (int q : quadros) if (q == pagina) return true;
        return false;
    }

    private int indiceLivre(int[] quadros) {
        for (int i = 0; i < quadros.length; i++) if (quadros[i] == -1) return i;
        return -1;
    }

    private int indiceDePagina(int[] quadros, int pagina) {
        for (int i = 0; i < quadros.length; i++) if (quadros[i] == pagina) return i;
        return -1;
    }
}
