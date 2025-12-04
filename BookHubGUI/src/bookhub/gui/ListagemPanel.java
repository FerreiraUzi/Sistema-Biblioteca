package bookhub.gui;

import bookhub.Livro;
import bookhub.LivroDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListagemPanel extends JPanel {
    private JTable tabelaLivros;
    private DefaultTableModel tableModel;
    private JTextField txtBusca;
    private JButton btnBuscar, btnAtualizar, btnExcluir;
    private LivroDAO dao;
    
    public ListagemPanel() {
        dao = new LivroDAO();
        initUI();
        carregarLivros();
    }
    
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Painel superior (busca)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Buscar por título:"));
        txtBusca = new JTextField(20);
        txtBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        topPanel.add(txtBusca);
        
        btnBuscar = new JButton("Buscar");
        styleButton(btnBuscar, new Color(52, 152, 219));
        topPanel.add(btnBuscar);
        
        btnAtualizar = new JButton("Atualizar Lista");
        styleButton(btnAtualizar, new Color(155, 89, 182));
        topPanel.add(btnAtualizar);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Tabela central
        String[] colunas = {"ID", "Título", "Autor", "Gênero", "Ano", "Preço (R$)", "Estoque", "Cadastro"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };
        
        tabelaLivros = new JTable(tableModel);
        tabelaLivros.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaLivros.setRowHeight(25);
        tabelaLivros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Renderizador para alinhar números à direita
        tabelaLivros.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tabelaLivros.getColumnModel().getColumn(4).setPreferredWidth(60); // Ano
        tabelaLivros.getColumnModel().getColumn(5).setPreferredWidth(80); // Preço
        tabelaLivros.getColumnModel().getColumn(6).setPreferredWidth(70); // Estoque
        
        JScrollPane scrollPane = new JScrollPane(tabelaLivros);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Livros Cadastrados"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior (ações)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnExcluir = new JButton("Excluir Livro Selecionado");
        styleButton(btnExcluir, new Color(231, 76, 60));
        bottomPanel.add(btnExcluir);
        
        JButton btnEditar = new JButton("Editar Livro");
        styleButton(btnEditar, new Color(241, 196, 15));
        bottomPanel.add(btnEditar);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Listeners
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLivros();
            }
        });
        
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarLivros();
                txtBusca.setText("");
            }
        });
        
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirLivro();
            }
        });
        
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarLivro();
            }
        });
    }
    
    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void carregarLivros() {
        tableModel.setRowCount(0); // Limpar tabela
        List<Livro> livros = dao.listarTodos();
        
        for (Livro livro : livros) {
            Object[] row = {
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getGenero(),
                livro.getAnoPublicacao(),
                String.format("%.2f", livro.getPreco()),
                livro.getQuantidadeEstoque(),
                livro.getDataCadastro().toLocalDate().toString()
            };
            tableModel.addRow(row);
        }
    }
    
    private void buscarLivros() {
        String termo = txtBusca.getText().trim();
        if (termo.isEmpty()) {
            carregarLivros();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Livro> livros = dao.buscarPorTitulo(termo);
        
        if (livros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum livro encontrado!", "Busca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Livro livro : livros) {
                Object[] row = {
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getGenero(),
                    livro.getAnoPublicacao(),
                    String.format("%.2f", livro.getPreco()),
                    livro.getQuantidadeEstoque(),
                    livro.getDataCadastro() != null ? livro.getDataCadastro().toLocalDate().toString() : "N/A"
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void excluirLivro() {
        int selectedRow = tabelaLivros.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String titulo = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Excluir o livro \"" + titulo + "\"?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deletar(id)) {
                JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarLivros();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir livro!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editarLivro() {
        int selectedRow = tabelaLivros.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Livro livro = dao.buscarPorId(id);
        
        if (livro != null) {
            // Diálogo de edição
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Editar Livro", true);
            dialog.setLayout(new BorderLayout());
            dialog.setSize(400, 500);
            dialog.setLocationRelativeTo(this);
            
            // Criar formulário de edição (simplificado - poderia ser um painel separado)
            JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JTextField txtEditTitulo = new JTextField(livro.getTitulo());
            JTextField txtEditAutor = new JTextField(livro.getAutor());
            JTextField txtEditGenero = new JTextField(livro.getGenero());
            JTextField txtEditAno = new JTextField(String.valueOf(livro.getAnoPublicacao()));
            JTextField txtEditPreco = new JTextField(String.valueOf(livro.getPreco()));
            JTextField txtEditEstoque = new JTextField(String.valueOf(livro.getQuantidadeEstoque()));
            
            formPanel.add(new JLabel("Título:"));
            formPanel.add(txtEditTitulo);
            formPanel.add(new JLabel("Autor:"));
            formPanel.add(txtEditAutor);
            formPanel.add(new JLabel("Gênero:"));
            formPanel.add(txtEditGenero);
            formPanel.add(new JLabel("Ano:"));
            formPanel.add(txtEditAno);
            formPanel.add(new JLabel("Preço:"));
            formPanel.add(txtEditPreco);
            formPanel.add(new JLabel("Estoque:"));
            formPanel.add(txtEditEstoque);
            
            dialog.add(formPanel, BorderLayout.CENTER);
            
            // Botões
            JPanel buttonPanel = new JPanel();
            JButton btnSalvar = new JButton("Salvar");
            JButton btnCancelar = new JButton("Cancelar");
            
            btnSalvar.addActionListener(e -> {
                try {
                    livro.setTitulo(txtEditTitulo.getText());
                    livro.setAutor(txtEditAutor.getText());
                    livro.setGenero(txtEditGenero.getText());
                    livro.setAnoPublicacao(Integer.parseInt(txtEditAno.getText()));
                    livro.setPreco(Double.parseDouble(txtEditPreco.getText()));
                    livro.setQuantidadeEstoque(Integer.parseInt(txtEditEstoque.getText()));
                    
                    if (dao.atualizar(livro)) {
                        JOptionPane.showMessageDialog(dialog, "Livro atualizado com sucesso!");
                        dialog.dispose();
                        carregarLivros();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro numérico nos campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            btnCancelar.addActionListener(e -> dialog.dispose());
            
            buttonPanel.add(btnSalvar);
            buttonPanel.add(btnCancelar);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            
            dialog.setVisible(true);
        }
    }
}