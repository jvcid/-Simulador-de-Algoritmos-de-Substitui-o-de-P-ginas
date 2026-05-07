package br.simulador.modelo;

import java.util.Collections;
import java.util.List;

/**
 * Resultado completo de uma simulação de substituição de páginas.
 * <p>
 * Contém o nome do algoritmo, a lista de {@link PassoSimulacao} e o
 * total de faltas de página calculado.
 */
public final class ResultadoSimulacao {

    private final String nomeAlgoritmo;
    private final List<PassoSimulacao> passos;
    private final int totalFaltas;

    public ResultadoSimulacao(String nomeAlgoritmo, List<PassoSimulacao> passos) {
        this.nomeAlgoritmo = nomeAlgoritmo;
        this.passos = Collections.unmodifiableList(passos);
        this.totalFaltas = (int) passos.stream().filter(PassoSimulacao::isFalta).count();
    }

    public String getNomeAlgoritmo() { return nomeAlgoritmo; }

    public List<PassoSimulacao> getPassos() { return passos; }

    public int getTotalFaltas() { return totalFaltas; }

    public int getNumeroPassos() { return passos.size(); }

    @Override
    public String toString() {
        return nomeAlgoritmo + " → " + totalFaltas + " falta(s) de página";
    }
}
