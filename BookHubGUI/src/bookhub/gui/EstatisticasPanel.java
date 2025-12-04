package bookhub.gui;

import bookhub.Livro;
import bookhub.LivroDAO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EstatisticasPanel extends JPanel {
    private JLabel lblTotalLivros, lblTotalExemplares, lblValorTotal, lblPrecoMedio;
    private LivroDAO dao;
    
    public EstatisticasPanel() {
        dao = new LivroDAO();
        initUI();
        atualizarEstatisticas();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Estatísticas da Livraria", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(70, 130, 180));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(lblTitulo, BorderLayout.NORTH);
        
        // Painel de estatísticas
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        lblTotalLivros = criarLinhaEstatistica("Total de Livros:", "0");
        lblTotalExemplares = criarLinhaEstatistica("Exemplares em Estoque:", "0");
        lblValorTotal = criarLinhaEstatistica("Valor Total em Estoque:", "R$ 0,00");
        lblPrecoMedio = criarLinhaEstatistica("Preço Médio:", "R$ 0,00");
        
        statsPanel.add(lblTotalLivros);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        statsPanel.add(lblTotalExemplares);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        statsPanel.add(lblValorTotal);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        statsPanel.add(lblPrecoMedio);
        
        add(statsPanel, BorderLayout.CENTER);
        
        // Botão atualizar
        JButton btnAtualizar = new JButton("Atualizar Estatísticas");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAtualizar.setBackground(new Color(70, 130, 180));
        btnAtualizar.setForeground(Color.BLACK);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnAtualizar.addActionListener(e -> atualizarEstatisticas());
        
        JPanel panelBtn = new JPanel();
        panelBtn.add(btnAtualizar);
        add(panelBtn, BorderLayout.SOUTH);
    }
    
    private JLabel criarLinhaEstatistica(String label, String valor) {
        JLabel linha = new JLabel("<html><div style='font-size:14pt; margin:5px;'><b>" + label + "</b> <span style='color:#2c3e50; font-size:16pt;'>" + valor + "</span></div></html>");
        return linha;
    }
    
    private void atualizarEstatisticas() {
        try {
            System.out.println("Atualizando estatísticas...");
            List<Livro> livros = dao.listarTodos();
            System.out.println("Livros encontrados: " + livros.size());
            
            if (livros.isEmpty()) {
                lblTotalLivros.setText("<html><div style='font-size:14pt; margin:5px;'><b>📚 Total de Livros:</b> <span style='color:#2c3e50; font-size:16pt;'>0</span></div></html>");
                lblTotalExemplares.setText("<html><div style='font-size:14pt; margin:5px;'><b>📦 Exemplares em Estoque:</b> <span style='color:#2c3e50; font-size:16pt;'>0</span></div></html>");
                lblValorTotal.setText("<html><div style='font-size:14pt; margin:5px;'><b>💰 Valor Total em Estoque:</b> <span style='color:#2c3e50; font-size:16pt;'>R$ 0,00</span></div></html>");
                lblPrecoMedio.setText("<html><div style='font-size:14pt; margin:5px;'><b>📈 Preço Médio:</b> <span style='color:#2c3e50; font-size:16pt;'>R$ 0,00</span></div></html>");
                return;
            }
            
            int totalLivros = livros.size();
            int totalExemplares = 0;
            double valorTotal = 0;
            double somaPrecos = 0;
            
            for (Livro livro : livros) {
                totalExemplares += livro.getQuantidadeEstoque();
                valorTotal += livro.getPreco() * livro.getQuantidadeEstoque();
                somaPrecos += livro.getPreco();
                System.out.println("Livro: " + livro.getTitulo() + ", Estoque: " + livro.getQuantidadeEstoque() + ", Preço: " + livro.getPreco());
            }
            
            double precoMedio = somaPrecos / totalLivros;
            
            System.out.println("Resultados:");
            System.out.println("Total Livros: " + totalLivros);
            System.out.println("Total Exemplares: " + totalExemplares);
            System.out.println("Valor Total: " + valorTotal);
            System.out.println("Preço Médio: " + precoMedio);
            
            lblTotalLivros.setText("<html><div style='font-size:14pt; margin:5px;'><b>📚 Total de Livros:</b> <span style='color:#2c3e50; font-size:16pt;'>" + totalLivros + "</span></div></html>");
            lblTotalExemplares.setText("<html><div style='font-size:14pt; margin:5px;'><b>📦 Exemplares em Estoque:</b> <span style='color:#2c3e50; font-size:16pt;'>" + totalExemplares + "</span></div></html>");
            lblValorTotal.setText("<html><div style='font-size:14pt; margin:5px;'><b>💰 Valor Total em Estoque:</b> <span style='color:#2c3e50; font-size:16pt;'>R$ " + String.format("%.2f", valorTotal) + "</span></div></html>");
            lblPrecoMedio.setText("<html><div style='font-size:14pt; margin:5px;'><b>📈 Preço Médio:</b> <span style='color:#2c3e50; font-size:16pt;'>R$ " + String.format("%.2f", precoMedio) + "</span></div></html>");
            
            // Forçar repaint
            revalidate();
            repaint();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar estatísticas:\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}