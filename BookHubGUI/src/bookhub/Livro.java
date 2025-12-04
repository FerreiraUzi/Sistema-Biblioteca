package bookhub;

import java.time.LocalDateTime;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private int anoPublicacao;
    private double preco;
    private int quantidadeEstoque;
    private LocalDateTime dataCadastro;
    
    // Construtores
    public Livro() {}
    
    public Livro(String titulo, String autor, String genero, int anoPublicacao, double preco, int quantidadeEstoque) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    // Getters e Setters (gerados com Alt+Insert no NetBeans)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    
    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
    
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Autor: %s | Gênero: %s | Ano: %d | Preço: R$%.2f | Estoque: %d",
                id, titulo, autor, genero, anoPublicacao, preco, quantidadeEstoque);
    }
}