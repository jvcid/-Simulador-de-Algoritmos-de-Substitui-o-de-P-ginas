package br.simulador.gui;

import br.simulador.modelo.PassoSimulacao;
import br.simulador.modelo.ResultadoSimulacao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Painel que exibe a tabela passo a passo de uma simulação.
 * <p>
 * Cada coluna representa um acesso da cadeia de referência.
 * Linhas: página acessada → quadros (1..N) → linha de falta (● / vazio).
 * Células com falta de página são coloridas com a cor do algoritmo.
 */
public class PainelTabela extends JPanel {

    private final Color corAlgoritmo;

    public PainelTabela(Color corAlgoritmo) {
        super(new BorderLayout());
        this.corAlgoritmo = corAlgoritmo;
        setBackground(Tema.SUPERFICIE);
    }

    /** Reconstrói a tabela com o resultado fornecido. */
    public void atualizar(ResultadoSimulacao resultado, int nQuadros) {
        removeAll();

        List<PassoSimulacao> passos = resultado.getPassos();
        int nPassos = passos.size();

        // ── Montar dados ────────────────────────────────────────────────────────
        int nColunas = nPassos + 1;               // +1 para rótulo da linha
        int nLinhas  = nQuadros + 2;              // linha de página + quadros + linha de falta

        Object[][] dados = new Object[nLinhas][nColunas];
        dados[0][0] = "Página";
        for (int c = 0; c < nPassos; c++) dados[0][c + 1] = passos.get(c).getPagina();

        for (int r = 0; r < nQuadros; r++) {
            dados[r + 1][0] = "Quadro " + (r + 1);
            for (int c = 0; c < nPassos; c++) {
                int v = passos.get(c).getQuadro(r);
                dados[r + 1][c + 1] = (v == -1) ? "–" : v;
            }
        }

        dados[nQuadros + 1][0] = "Falta";
        for (int c = 0; c < nPassos; c++) {
            dados[nQuadros + 1][c + 1] = passos.get(c).isFalta() ? "●" : "";
        }

        // ── Montar nomes de colunas ─────────────────────────────────────────────
        String[] nomeColunas = new String[nColunas];
        nomeColunas[0] = "Ref";
        for (int c = 1; c < nColunas; c++) nomeColunas[c] = String.valueOf(c);

        // ── Criar tabela ────────────────────────────────────────────────────────
        DefaultTableModel model = new DefaultTableModel(dados, nomeColunas) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabela = new JTable(model);
        tabela.setBackground(Tema.SUPERFICIE);
        tabela.setForeground(Tema.TEXTO);
        tabela.setGridColor(Tema.BORDA);
        tabela.setFont(Tema.FONTE_MONO);
        tabela.setRowHeight(26);
        tabela.setSelectionBackground(Tema.SUPERFICIE2);
        tabela.setSelectionForeground(Tema.TEXTO);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Cabeçalho
        tabela.getTableHeader().setBackground(Tema.SUPERFICIE2);
        tabela.getTableHeader().setForeground(Tema.TEXTO_SUAVE);
        tabela.getTableHeader().setFont(Tema.FONTE_MONO_P);

        // Renderer customizado
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {

                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setHorizontalAlignment(CENTER);

                boolean ehFalta = col > 0 && passos.get(col - 1).isFalta();

                // Cor de fundo alternado
                if (sel) setBackground(Tema.SUPERFICIE2);
                else     setBackground(row % 2 == 0 ? Tema.SUPERFICIE : new Color(33, 33, 44));

                // Coluna de rótulos
                if (col == 0) {
                    setForeground(Tema.TEXTO_SUAVE);
                    setFont(Tema.FONTE_MONO_P);
                    return this;
                }

                // Linha da página
                if (row == 0) {
                    setForeground(ehFalta ? corAlgoritmo : Tema.TEXTO_SUAVE);
                    setFont(ehFalta ? new Font("Monospaced", Font.BOLD, 12) : Tema.FONTE_MONO);
                    return this;
                }

                // Linha de falta
                if (row == nQuadros + 1) {
                    setForeground(corAlgoritmo);
                    setFont(new Font("Monospaced", Font.BOLD, 14));
                    return this;
                }

                // Células de quadro
                if (ehFalta) {
                    int quadroIdx = row - 1;
                    int passoIdx  = col - 1;
                    PassoSimulacao passo = passos.get(passoIdx);
                    int valorCelula = passo.getQuadro(quadroIdx);
                    if (valorCelula == passo.getPagina()) {
                        setForeground(corAlgoritmo);
                        setFont(new Font("Monospaced", Font.BOLD, 12));
                    } else {
                        setForeground(Tema.TEXTO);
                        setFont(Tema.FONTE_MONO);
                    }
                } else {
                    setForeground(Tema.TEXTO);
                    setFont(Tema.FONTE_MONO);
                }

                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Tema.BORDA));
                return this;
            }
        });

        // Larguras de coluna
        tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
        for (int c = 1; c < nColunas; c++) tabela.getColumnModel().getColumn(c).setPreferredWidth(34);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Tema.SUPERFICIE);
        scroll.setBackground(Tema.SUPERFICIE);
        scroll.setBorder(BorderFactory.createLineBorder(Tema.BORDA));

        add(scroll, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
