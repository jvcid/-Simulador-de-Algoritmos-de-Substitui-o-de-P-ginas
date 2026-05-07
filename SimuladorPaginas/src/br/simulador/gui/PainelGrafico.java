package br.simulador.gui;

import br.simulador.modelo.ResultadoSimulacao;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Painel que renderiza um gráfico de barras comparando o número de
 * faltas de página de cada algoritmo simulado.
 */
public class PainelGrafico extends JPanel {

    private static final Color BG       = new Color(18, 18, 24);
    private static final Color GRADE    = new Color(50, 50, 68);
    private static final Color TEXT_MUTED = new Color(130, 130, 155);
    private static final Color TEXT     = new Color(220, 220, 235);

    private List<ResultadoSimulacao> resultados;

    public PainelGrafico() {
        setBackground(BG);
        setPreferredSize(new Dimension(0, 180));
    }

    /** Atualiza os dados e redesenha o gráfico. */
    public void setResultados(List<ResultadoSimulacao> resultados) {
        this.resultados = resultados;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (resultados == null || resultados.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int W = getWidth(), H = getHeight();
        int padL = 44, padR = 16, padT = 20, padB = 40;
        int chartW = W - padL - padR;
        int chartH = H - padT - padB;

        int max = resultados.stream().mapToInt(ResultadoSimulacao::getTotalFaltas).max().orElse(1);
        if (max == 0) max = 1;

        // Linhas de grade horizontais
        g2.setStroke(new BasicStroke(0.6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10, new float[]{4, 4}, 0));
        g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
        for (int tick = 0; tick <= 4; tick++) {
            int y = padT + chartH - (int) ((double) tick / 4 * chartH);
            int val = (int) Math.round((double) tick / 4 * max);
            g2.setColor(GRADE);
            g2.drawLine(padL, y, W - padR, y);
            g2.setColor(TEXT_MUTED);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(String.valueOf(val), padL - fm.stringWidth(String.valueOf(val)) - 4, y + 4);
        }
        g2.setStroke(new BasicStroke(1));

        // Barras
        int n = resultados.size();
        int barW = chartW / n;
        int gap  = Math.max(8, (int) (barW * 0.3));

        for (int i = 0; i < n; i++) {
            ResultadoSimulacao r = resultados.get(i);
            Color cor = Tema.COR_ALGORITMO[i % Tema.COR_ALGORITMO.length];
            int faltas = r.getTotalFaltas();
            int barH = faltas == 0 ? 0 : (int) ((double) faltas / max * chartH);
            int x = padL + i * barW + gap / 2;
            int w = barW - gap;
            int y = padT + chartH - barH;

            // Barra
            g2.setColor(cor);
            g2.fillRoundRect(x, y, w, Math.max(barH, 2), 6, 6);

            // Valor no topo
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontMetrics fm = g2.getFontMetrics();
            String val = String.valueOf(faltas);
            g2.drawString(val, x + (w - fm.stringWidth(val)) / 2, y - 5);

            // Label embaixo
            g2.setColor(TEXT_MUTED);
            g2.setFont(new Font("Monospaced", Font.BOLD, 11));
            fm = g2.getFontMetrics();
            String nome = r.getNomeAlgoritmo();
            g2.drawString(nome, x + (w - fm.stringWidth(nome)) / 2, H - 10);
        }
    }
}
