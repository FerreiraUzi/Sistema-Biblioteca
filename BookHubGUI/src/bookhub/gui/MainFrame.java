
package bookhub.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        super("BookHub - Gestão de Livraria Digital");
        configurarJanela();
        initComponents();
    }
    
    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Cabeçalho
        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(70, 130, 180));
        cabecalho.setPreferredSize(new Dimension(900, 80));
        
        JLabel titulo = new JLabel(" BOOKHUB - Livraria Digital");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        cabecalho.add(titulo);
        
        add(cabecalho, BorderLayout.NORTH);
        
        // Área de abas
        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        abas.addTab("Cadastro", new CadastroPanel());
        abas.addTab("Listagem", new ListagemPanel());
        abas.addTab("Estatísticas", new EstatisticasPanel());
        
        add(abas, BorderLayout.CENTER);
        
        // Rodapé
        JLabel rodape = new JLabel("Sistema de Biblioteca em Java Swing | © 2025");
        rodape.setHorizontalAlignment(SwingConstants.CENTER);
        rodape.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(rodape, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.put("Button.background", new Color(52, 152, 219));
            UIManager.put("Button.foreground", Color.BLACK);
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

