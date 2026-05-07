package br.simulador.algoritmo;

import br.simulador.modelo.PassoSimulacao;
import br.simulador.modelo.ResultadoSimulacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Algoritmo do Relógio (Clock / Segunda Chance).
 * <p>
 * Variação do FIFO com um bit de uso por quadro.
 * Quando ocorre falta, o ponteiro percorre os quadros em ordem circular:
 * se o bit de uso for 1, zera e avança; se for 0, substitui a página.
 * Isso dá uma "segunda chance" às páginas recentemente referenciadas.
 */
public class AlgoritmoRelogio implements AlgoritmoSubstituicao {

    @Override
    public ResultadoSimulacao simular(int[] paginas, int nQuadros) {
        int[] quadros  = new int[nQuadros];
        int[] bitDeUso = new int[nQuadros];
        Arrays.fill(quadros, -1);

        int ponteiro = 0;
        List<PassoSimulacao> passos = new ArrayList<>();

        for (int pagina : paginas) {
            int indice = indiceDePagina(quadros, pagina);
            boolean falta = (indice == -1);

            if (!falta) {
                // Página já está na memória — atualiza bit de uso
                bitDeUso[indice] = 1;
            } else {
                // Percorre o relógio até encontrar bit de uso = 0
                while (bitDeUso[ponteiro] == 1) {
                    bitDeUso[ponteiro] = 0;
                    ponteiro = (ponteiro + 1) % nQuadros;
                }
                quadros[ponteiro]  = pagina;
                bitDeUso[ponteiro] = 1;
                ponteiro = (ponteiro + 1) % nQuadros;
            }

            passos.add(new PassoSimulacao(pagina, quadros, falta));
        }

        return new ResultadoSimulacao(getNome(), passos);
    }

    @Override
    public String getNome() { return "Relógio"; }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private int indiceDePagina(int[] quadros, int pagina) {
        for (int i = 0; i < quadros.length; i++) if (quadros[i] == pagina) return i;
        return -1;
    }
}
