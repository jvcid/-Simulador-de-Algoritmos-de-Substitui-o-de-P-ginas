package br.simulador.algoritmo;

import br.simulador.modelo.PassoSimulacao;
import br.simulador.modelo.ResultadoSimulacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Algoritmo LRU (Least Recently Used).
 * <p>
 * Substitui a página que foi utilizada há mais tempo.
 * Uma lista ligada mantém a ordem de acesso: o início contém a
 * menos recentemente usada e o fim a mais recentemente usada.
 */
public class AlgoritmoLRU implements AlgoritmoSubstituicao {

    @Override
    public ResultadoSimulacao simular(int[] paginas, int nQuadros) {
        int[] quadros = new int[nQuadros];
        Arrays.fill(quadros, -1);

        LinkedList<Integer> ordemDeUso = new LinkedList<>(); // cabeça = LRU
        List<PassoSimulacao> passos    = new ArrayList<>();

        for (int pagina : paginas) {
            boolean falta = !contemPagina(quadros, pagina);

            if (falta) {
                if (ordemDeUso.size() < nQuadros) {
                    int posLivre = indiceLivre(quadros);
                    quadros[posLivre] = pagina;
                    ordemDeUso.addLast(pagina);
                } else {
                    int vitima = ordemDeUso.removeFirst();
                    quadros[indiceDePagina(quadros, vitima)] = pagina;
                    ordemDeUso.addLast(pagina);
                }
            } else {
                // Atualiza posição na lista de ordem de uso
                ordemDeUso.remove((Integer) pagina);
                ordemDeUso.addLast(pagina);
            }

            passos.add(new PassoSimulacao(pagina, quadros, falta));
        }

        return new ResultadoSimulacao(getNome(), passos);
    }

    @Override
    public String getNome() { return "LRU"; }

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
