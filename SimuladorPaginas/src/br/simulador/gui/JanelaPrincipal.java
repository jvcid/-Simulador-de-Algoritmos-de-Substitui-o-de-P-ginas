package br.simulador.gui;

import br.simulador.algoritmo.*;
import br.simulador.modelo.ResultadoSimulacao;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Janela principal da aplicação.
 * <p>
 * Compõe os painéis de entrada, cards de resultado, gráfico de barras
 * e abas de tabela passo a passo.
 */
public class JanelaPrincipal extends JFrame {

    // ── Algoritmos registrados ──────────────────────────────────────────────────
    private static final AlgoritmoSubstituicao[] ALGORITMOS = {
        new AlgoritmoFIFO(),
        new AlgoritmoLRU(),
        new AlgoritmoOtimo(),
        new AlgoritmoRelogio()
    };

    // ── Widgets de entrada ──────────────────────────────────────────────────────
    private JTextField campoCadeia;
    private JSpinner   spinnerQuadros;

    // ── Widgets de resultado ────────────────────────────────────────────────────
    private JLabel[]       lblFaltas = new JLabel[ALGORITMOS.length];
    private PainelGrafico  painelGrafico;
    private JTabbedPane    abas;
    private PainelTabela[] paineisTabelaas = new PainelTabela[ALGORITMOS.length];

    public JanelaPrincipal() {
        super("Simulador de Substituição de Páginas");
        configurarJanela();
        add(construirCabecalho(),  BorderLayout.NORTH);
        add(construirConteudo(),   BorderLayout.CENTER);
        setVisible(true);
    }

    // ── Configuração da janela ──────────────────────────────────────────────────
    private void configurarJanela() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 740);
        setMinimumSize(new Dimension(840, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Tema.FUNDO);
        setLayout(new BorderLayout());
    }

    // ── Cabeçalho ───────────────────────────────────────────────────────────────
    private JPanel construirCabecalho() {
        JPanel painel = new JPanel(new BorderLayout(12, 0));
        painel.setBackground(Tema.SUPERFICIE);
        painel.setBorder(new CompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Tema.BORDA),
            new EmptyBorder(12, 20, 12, 20)
        ));

        JLabel titulo = new JLabel("Simulador de Substituição de Páginas");
        titulo.setFont(Tema.FONTE_TITULO);
        titulo.setForeground(Tema.TEXTO);
        painel.add(titulo, BorderLayout.WEST);

        painel.add(construirBarraDeControles(), BorderLayout.EAST);
        return painel;
    }

    private JPanel construirBarraDeControles() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        barra.setBackground(Tema.SUPERFICIE);

        JLabel lblCad = rotulo("Cadeia de páginas:", Tema.TEXTO_SUAVE, 12);
        campoCadeia = new JTextField("7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1", 30);
        estilizarCampo(campoCadeia);

        JLabel lblQ = rotulo("Quadros:", Tema.TEXTO_SUAVE, 12);
        spinnerQuadros = new JSpinner(new SpinnerNumberModel(3, 1, 20, 1));
        estilizarSpinner(spinnerQuadros);

        JButton btnSimular = new JButton("  Simular  ");
        estilizarBotao(btnSimular);
        btnSimular.addActionListener(e -> executarSimulacao());

        barra.add(lblCad);
        barra.add(campoCadeia);
        barra.add(lblQ);
        barra.add(spinnerQuadros);
        barra.add(btnSimular);
        return barra;
    }

    // ── Conteúdo central ────────────────────────────────────────────────────────
    private JPanel construirConteudo() {
        JPanel painel = new JPanel(new BorderLayout(0, 14));
        painel.setBackground(Tema.FUNDO);
        painel.setBorder(new EmptyBorder(18, 20, 18, 20));

        painel.add(construirTopo(), BorderLayout.NORTH);
        painel.add(construirAbas(), BorderLayout.CENTER);
        return painel;
    }

    private JPanel construirTopo() {
        JPanel topo = new JPanel(new BorderLayout(0, 12));
        topo.setBackground(Tema.FUNDO);
        topo.add(construirCards(), BorderLayout.NORTH);

        painelGrafico = new PainelGrafico();
        topo.add(painelGrafico, BorderLayout.CENTER);
        return topo;
    }

    private JPanel construirCards() {
        JPanel grid = new JPanel(new GridLayout(1, ALGORITMOS.length, 12, 0));
        grid.setBackground(Tema.FUNDO);

        for (int i = 0; i < ALGORITMOS.length; i++) {
            grid.add(construirCard(i));
        }
        return grid;
    }

    private JPanel construirCard(int indice) {
        JPanel card = new JPanel(new BorderLayout(0, 4));
        card.setBackground(Tema.SUPERFICIE);
        card.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Tema.BORDA),
            new EmptyBorder(14, 16, 14, 16)
        ));

        Color cor = Tema.COR_ALGORITMO[indice];
        JLabel nomeAlgo = rotulo(ALGORITMOS[indice].getNome(), cor, 11);
        nomeAlgo.setFont(Tema.FONTE_MONO_P);

        lblFaltas[indice] = new JLabel("–");
        lblFaltas[indice].setFont(Tema.FONTE_VALOR);
        lblFaltas[indice].setForeground(cor);

        JLabel subLabel = rotulo("faltas de página", Tema.TEXTO_SUAVE, 11);

        card.add(nomeAlgo,          BorderLayout.NORTH);
        card.add(lblFaltas[indice], BorderLayout.CENTER);
        card.add(subLabel,          BorderLayout.SOUTH);
        return card;
    }

    private JTabbedPane construirAbas() {
        abas = new JTabbedPane();
        abas.setBackground(Tema.SUPERFICIE);
        abas.setForeground(Tema.TEXTO);
        abas.setFont(Tema.FONTE_MONO_P);

        for (int i = 0; i < ALGORITMOS.length; i++) {
            paineisTabelaas[i] = new PainelTabela(Tema.COR_ALGORITMO[i]);
            abas.addTab(ALGORITMOS[i].getNome(), paineisTabelaas[i]);
            abas.setForegroundAt(i, Tema.COR_ALGORITMO[i]);
        }
        return abas;
    }

    // ── Simulação ───────────────────────────────────────────────────────────────
    private void executarSimulacao() {
        int[] paginas = parsearCadeia();
        if (paginas == null) return;

        int nQuadros = (int) spinnerQuadros.getValue();

        List<ResultadoSimulacao> resultados = new ArrayList<>();
        for (int i = 0; i < ALGORITMOS.length; i++) {
            ResultadoSimulacao r = ALGORITMOS[i].simular(paginas, nQuadros);
            resultados.add(r);

            // Atualiza card
            lblFaltas[i].setText(String.valueOf(r.getTotalFaltas()));

            // Atualiza aba de tabela
            paineisTabelaas[i].atualizar(r, nQuadros);
        }

        // Atualiza gráfico
        painelGrafico.setResultados(resultados);

        // Log no console
        System.out.println("\n=== Resultado da Simulação ===");
        for (ResultadoSimulacao r : resultados) System.out.println(r);
    }

    private int[] parsearCadeia() {
        String texto = campoCadeia.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Insira a cadeia de referência de páginas.",
                "Entrada inválida", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String[] partes = texto.split("\\s+");
        int[] paginas = new int[partes.length];
        try {
            for (int i = 0; i < partes.length; i++) paginas[i] = Integer.parseInt(partes[i]);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Cadeia inválida. Use apenas números inteiros separados por espaço.",
                "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return paginas;
    }

    // ── Helpers de estilo ────────────────────────────────────────────────────────
    private JLabel rotulo(String texto, Color cor, int tamanho) {
        JLabel l = new JLabel(texto);
        l.setForeground(cor);
        l.setFont(new Font("SansSerif", Font.PLAIN, tamanho));
        return l;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setBackground(Tema.SUPERFICIE2);
        campo.setForeground(Tema.TEXTO);
        campo.setCaretColor(Tema.DESTAQUE);
        campo.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Tema.BORDA),
            new EmptyBorder(5, 8, 5, 8)
        ));
        campo.setFont(Tema.FONTE_MONO);
    }

    private void estilizarSpinner(JSpinner spinner) {
        spinner.setBackground(Tema.SUPERFICIE2);
        spinner.setForeground(Tema.TEXTO);
        spinner.setFont(Tema.FONTE_NORMAL);
        spinner.setBorder(BorderFactory.createLineBorder(Tema.BORDA));
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor de) {
            de.getTextField().setBackground(Tema.SUPERFICIE2);
            de.getTextField().setForeground(Tema.TEXTO);
            de.getTextField().setCaretColor(Tema.DESTAQUE);
            de.getTextField().setBorder(new EmptyBorder(4, 6, 4, 6));
        }
    }

    private void estilizarBotao(JButton botao) {
        botao.setBackground(Tema.DESTAQUE);
        botao.setForeground(Color.WHITE);
        botao.setFont(Tema.FONTE_CABECALHO);
        botao.setBorder(new EmptyBorder(7, 18, 7, 18));
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorderPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { botao.setBackground(new Color(80, 120, 240)); }
            @Override public void mouseExited(MouseEvent e)  { botao.setBackground(Tema.DESTAQUE); }
        });
    }
}
