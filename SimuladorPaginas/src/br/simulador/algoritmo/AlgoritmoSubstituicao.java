package br.simulador.algoritmo;

import br.simulador.modelo.ResultadoSimulacao;

/**
 * Contrato que todo algoritmo de substituição de páginas deve implementar.
 * Cada implementação recebe a cadeia de referência e o número de quadros
 * e devolve um {@link ResultadoSimulacao} com os passos e o total de faltas.
 */
public interface AlgoritmoSubstituicao {

    /**
     * Executa a simulação do algoritmo.
     *
     * @param paginas   cadeia de referência de páginas
     * @param nQuadros  número de quadros de memória disponíveis
     * @return resultado completo da simulação
     */
    ResultadoSimulacao simular(int[] paginas, int nQuadros);

    /**
     * Nome legível do algoritmo (exibido na interface).
     */
    String getNome();
}
