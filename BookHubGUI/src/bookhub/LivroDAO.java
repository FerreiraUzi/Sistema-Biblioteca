package bookhub;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    
    // CREATE
    public boolean inserir(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, genero, ano_publicacao, preco, quantidade_estoque) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setDouble(5, livro.getPreco());
            stmt.setInt(6, livro.getQuantidadeEstoque());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
            return false;
        }
    }
    
    // READ (todos)
    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros ORDER BY titulo";
        
        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setGenero(rs.getString("genero"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livro.setPreco(rs.getDouble("preco"));
                livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                livro.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
                
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
        }
        return livros;
    }
    
    // READ (por ID)
    public Livro buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        Livro livro = null;
        
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setGenero(rs.getString("genero"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livro.setPreco(rs.getDouble("preco"));
                livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                livro.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro: " + e.getMessage());
        }
        return livro;
    }
    
    // UPDATE
    public boolean atualizar(Livro livro) {
        String sql = "UPDATE livros SET titulo=?, autor=?, genero=?, ano_publicacao=?, preco=?, quantidade_estoque=? WHERE id=?";
        
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setDouble(5, livro.getPreco());
            stmt.setInt(6, livro.getQuantidadeEstoque());
            stmt.setInt(7, livro.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE
    public boolean deletar(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar livro: " + e.getMessage());
            return false;
        }
    }
    
    // BUSCA por título
    public List<Livro> buscarPorTitulo(String palavra) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE titulo LIKE ? ORDER BY titulo";
        
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + palavra + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setGenero(rs.getString("genero"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livro.setPreco(rs.getDouble("preco"));
                livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                
                livros.add(livro);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Erro na busca: " + e.getMessage());
        }
        return livros;
    }
}