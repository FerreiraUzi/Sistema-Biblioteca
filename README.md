# Sistema-Biblioteca
Sistema de cadastro de livros em Java (Interface em Swing)



## ✨ Funcionalidades

- ✅ **CRUD** de livros (Cadastrar, Listar, Atualizar, Excluir)
- 🎨 **Interface gráfica** com Java Swing
- 📊 **Estatísticas** em tempo real
- 🔍 **Busca** por título ou autor
- 💾 **Persistência** com MySQL


## 📦 Instalação

### 1. Pré-requisitos
- Java JDK 8 ou superior
- MySQL Server
- NetBeans IDE (opcional)


### 2. Banco de Dados Utilizado

  No XAMPP: 
  
    -- Criar banco de dados
    CREATE DATABASE IF NOT EXISTS bookhub;
    USE bookhub;
    
    -- Tabela de Livros
    CREATE TABLE livros (
        id INT AUTO_INCREMENT PRIMARY KEY,
        titulo VARCHAR(150) NOT NULL,
        autor VARCHAR(100) NOT NULL,
        genero VARCHAR(50),
        ano_publicacao INT,
        preco DECIMAL(10,2),
        quantidade_estoque INT DEFAULT 0,
        data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    
    -- Inserir dados de exemplo
    INSERT INTO livros (titulo, autor, genero, ano_publicacao, preco, quantidade_estoque) VALUES
    ('Dom Casmurro', 'Machado de Assis', 'Romance', 1899, 29.90, 15),
    ('1984', 'George Orwell', 'Distopia', 1949, 39.50, 8),
    ('O Hobbit', 'J.R.R. Tolkien', 'Fantasia', 1937, 45.00, 12),
    ('Cosmos', 'Carl Sagan', 'Ciência', 1980, 55.90, 5),
    ('A Sutil Arte de Ligar o F*da-se', 'Mark Manson', 'Autoajuda', 2016, 34.90, 20);

