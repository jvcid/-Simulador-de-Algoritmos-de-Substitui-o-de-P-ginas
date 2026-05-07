package br.simulador.gui;

import java.awt.*;

/**
 * Constantes de cores e fontes utilizadas em toda a interface gráfica.
 * Centraliza o tema visual para facilitar manutenção.
 */
public final class Tema {

    private Tema() { /* utilitário estático */ }

    // ── Cores base ─────────────────────────────────────────────────────────────
    public static final Color FUNDO         = new Color(18, 18, 24);
    public static final Color SUPERFICIE    = new Color(28, 28, 36);
    public static final Color SUPERFICIE2   = new Color(38, 38, 50);
    public static final Color BORDA         = new Color(60, 60, 80);
    public static final Color TEXTO         = new Color(220, 220, 235);
    public static final Color TEXTO_SUAVE   = new Color(140, 140, 165);
    public static final Color DESTAQUE      = new Color(100, 140, 255);

    // ── Uma cor distinta por algoritmo ─────────────────────────────────────────
    public static final Color[] COR_ALGORITMO = {
        new Color(55,  138, 221),  // FIFO     – azul
        new Color(29,  158, 117),  // LRU      – verde
        new Color(216,  90,  48),  // Ótimo    – laranja
        new Color(212,  83, 126),  // Relógio  – rosa
    };

    // ── Fontes ─────────────────────────────────────────────────────────────────
    public static final Font FONTE_TITULO   = new Font("SansSerif",  Font.BOLD,  17);
    public static final Font FONTE_CABECALHO= new Font("SansSerif",  Font.BOLD,  13);
    public static final Font FONTE_NORMAL   = new Font("SansSerif",  Font.PLAIN, 13);
    public static final Font FONTE_MONO     = new Font("Monospaced", Font.PLAIN, 12);
    public static final Font FONTE_MONO_P   = new Font("Monospaced", Font.BOLD,  11);
    public static final Font FONTE_VALOR    = new Font("SansSerif",  Font.BOLD,  30);
}
