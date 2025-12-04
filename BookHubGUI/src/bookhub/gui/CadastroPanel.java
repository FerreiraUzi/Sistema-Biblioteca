package bookhub.gui;

import bookhub.Livro;
import bookhub.LivroDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroPanel extends JPanel {
    private JTextField txtTitulo, txtAutor, txtGenero, txtAno, txtPreco, txtEstoque;
    private JButton btnCadastrar, btnLimpar;
    private LivroDAO dao;
    
    public CadastroPanel() {
        dao = new LivroDAO();
        initUI();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        
        // Painel central com formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título do formulário
        JLabel lblTitulo = new JLabel("Cadastro de Novo Livro");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(lblTitulo, gbc);
        
        // Campos do formulário
        String[] labels = {"Título:", "Autor:", "Gênero:", "Ano:", "Preço (R$):", "Estoque:"};
        JTextField[] campos = new JTextField[6];
        
        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.gridy = i + 1;
            
            // Label
            gbc.gridx = 0;
            gbc.fill = GridBagConstraints.NONE;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            formPanel.add(label, gbc);
            
            // Campo
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            campos[i] = new JTextField(20);
            campos[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            formPanel.add(campos[i], gbc);
        }
        
        // Atribuir aos campos da classe
        txtTitulo = campos[0];
        txtAutor = campos[1];
        txtGenero = campos[2];
        txtAno = campos[3];
        txtPreco = campos[4];
        txtEstoque = campos[5];
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnCadastrar = new JButton(" Cadastrar Livro ");
        btnLimpar = new JButton(" Limpar Campos ");
        
        // Estilizar botões
        styleButton(btnCadastrar, new Color(46, 204, 113));
        styleButton(btnLimpar, new Color(52, 152, 219));
        
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarLivro();
            }
        });
        
        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        
        buttonPanel.add(btnCadastrar);
        buttonPanel.add(btnLimpar);
        
        // Adicionar ao painel principal
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void cadastrarLivro() {
        try {
            // Validação
            if (txtTitulo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o título!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Livro livro = new Livro();
            livro.setTitulo(txtTitulo.getText().trim());
            livro.setAutor(txtAutor.getText().trim());
            livro.setGenero(txtGenero.getText().trim());
            livro.setAnoPublicacao(Integer.parseInt(txtAno.getText().trim()));
            livro.setPreco(Double.parseDouble(txtPreco.getText().trim()));
            livro.setQuantidadeEstoque(Integer.parseInt(txtEstoque.getText().trim()));
            
            if (dao.inserir(livro)) {
                JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar livro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique os campos numéricos (ano, preço, estoque)!", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtGenero.setText("");
        txtAno.setText("");
        txtPreco.setText("");
        txtEstoque.setText("");
        txtTitulo.requestFocus();
    }
}