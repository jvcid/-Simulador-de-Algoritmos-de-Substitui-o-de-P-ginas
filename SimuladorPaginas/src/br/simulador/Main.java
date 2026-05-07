package br.simulador;

import br.simulador.gui.JanelaPrincipal;

import javax.swing.*;

/**
 * Ponto de entrada da aplicação.
 * <p>
 * Garante que a interface gráfica seja criada na Event Dispatch Thread (EDT),
 * conforme exigido pelo Swing.
 */
public class Main {

    public static void main(String[] args) {
        configurarLookAndFeel();
        SwingUtilities.invokeLater(JanelaPrincipal::new);
    }

    private static void configurarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Não foi possível aplicar o Look and Feel: " + e.getMessage());
        }
    }
}
