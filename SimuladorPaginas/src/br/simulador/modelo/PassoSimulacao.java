package br.simulador.modelo;

import java.util.Arrays;

/**
 * Representa o estado da memória em um único acesso da cadeia de referência.
 * <p>
 * Cada instância é <em>imutável</em>: os quadros são copiados no construtor
 * para evitar aliasing.
 */
public final class PassoSimulacao {

    /** Página que foi acessada neste passo. */
    private final int pagina;

    /** Estado dos quadros de memória após o acesso (-1 = vazio). */
    private final int[] quadros;

    /** Indica se ocorreu falta de página neste passo. */
    private final boolean falta;

    public PassoSimulacao(int pagina, int[] quadros, boolean falta) {
        this.pagina  = pagina;
        this.quadros = Arrays.copyOf(quadros, quadros.length);
        this.falta   = falta;
    }

    public int getPagina() { return pagina; }

    public int[] getQuadros() { return Arrays.copyOf(quadros, quadros.length); }

    public int getQuadro(int indice) { return quadros[indice]; }

    public boolean isFalta() { return falta; }

    @Override
    public String toString() {
        return "PassoSimulacao{pagina=" + pagina
                + ", quadros=" + Arrays.toString(quadros)
                + ", falta=" + falta + '}';
    }
}
