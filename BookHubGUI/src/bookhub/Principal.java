package bookhub;

import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LivroDAO dao = new LivroDAO();
        int opcao;
        
        do {
            System.out.println("\n=== BOOKHUB - Gestão de Livraria Digital ===");
            System.out.println("1. Cadastrar novo livro");
            System.out.println("2. Listar todos os livros");
            System.out.println("3. Buscar livro por ID");
            System.out.println("4. Buscar livro por título");
            System.out.println("5. Atualizar livro");
            System.out.println("6. Excluir livro");
            System.out.println("7. Estatísticas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1 -> cadastrarLivro(scanner, dao);
                case 2 -> listarLivros(dao);
                case 3 -> buscarPorId(scanner, dao);
                case 4 -> buscarPorTitulo(scanner, dao);
                case 5 -> atualizarLivro(scanner, dao);
                case 6 -> excluirLivro(scanner, dao);
                case 7 -> exibirEstatisticas(dao);
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close();
    }
    
    private static void cadastrarLivro(Scanner scanner, LivroDAO dao) {
        System.out.println("\n--- CADASTRAR NOVO LIVRO ---");
        
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        
        System.out.print("Ano de publicação: ");
        int ano = scanner.nextInt();
        
        System.out.print("Preço: R$ ");
        double preco = scanner.nextDouble();
        
        System.out.print("Quantidade em estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();
        
        Livro livro = new Livro(titulo, autor, genero, ano, preco, estoque);
        
        if (dao.inserir(livro)) {
            System.out.println("✅ Livro cadastrado com sucesso!");
        } else {
            System.out.println("❌ Erro ao cadastrar livro.");
        }
    }
    
    private static void listarLivros(LivroDAO dao) {
        System.out.println("\n--- TODOS OS LIVROS CADASTRADOS ---");
        List<Livro> livros = dao.listarTodos();
        
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }
    
    private static void buscarPorId(Scanner scanner, LivroDAO dao) {
        System.out.print("\nDigite o ID do livro: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Livro livro = dao.buscarPorId(id);
        if (livro != null) {
            System.out.println("📖 Livro encontrado:\n" + livro);
        } else {
            System.out.println("Livro não encontrado.");
        }
    }
    
    private static void buscarPorTitulo(Scanner scanner, LivroDAO dao) {
        System.out.print("\nDigite parte do título: ");
        String titulo = scanner.nextLine();
        
        List<Livro> livros = dao.buscarPorTitulo(titulo);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            System.out.println("🔍 Resultados da busca:");
            livros.forEach(System.out::println);
        }
    }
    
    private static void atualizarLivro(Scanner scanner, LivroDAO dao) {
        System.out.print("\nDigite o ID do livro a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Livro livro = dao.buscarPorId(id);
        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }
        
        System.out.println("Dados atuais: " + livro);
        System.out.println("\n--- NOVOS DADOS ---");
        
        System.out.print("Novo título [" + livro.getTitulo() + "]: ");
        String novoTitulo = scanner.nextLine();
        if (!novoTitulo.isEmpty()) livro.setTitulo(novoTitulo);
        
        System.out.print("Novo autor [" + livro.getAutor() + "]: ");
        String novoAutor = scanner.nextLine();
        if (!novoAutor.isEmpty()) livro.setAutor(novoAutor);
        
        System.out.print("Novo gênero [" + livro.getGenero() + "]: ");
        String novoGenero = scanner.nextLine();
        if (!novoGenero.isEmpty()) livro.setGenero(novoGenero);
        
        System.out.print("Novo ano [" + livro.getAnoPublicacao() + "]: ");
        String novoAnoStr = scanner.nextLine();
        if (!novoAnoStr.isEmpty()) livro.setAnoPublicacao(Integer.parseInt(novoAnoStr));
        
        System.out.print("Novo preço [" + livro.getPreco() + "]: ");
        String novoPrecoStr = scanner.nextLine();
        if (!novoPrecoStr.isEmpty()) livro.setPreco(Double.parseDouble(novoPrecoStr));
        
        System.out.print("Nova quantidade [" + livro.getQuantidadeEstoque() + "]: ");
        String novoEstoqueStr = scanner.nextLine();
        if (!novoEstoqueStr.isEmpty()) livro.setQuantidadeEstoque(Integer.parseInt(novoEstoqueStr));
        
        if (dao.atualizar(livro)) {
            System.out.println("✅ Livro atualizado com sucesso!");
        } else {
            System.out.println("❌ Erro ao atualizar livro.");
        }
    }
    
    private static void excluirLivro(Scanner scanner, LivroDAO dao) {
        System.out.print("\nDigite o ID do livro a ser excluído: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Tem certeza? (S/N): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            if (dao.deletar(id)) {
                System.out.println("✅ Livro excluído com sucesso!");
            } else {
                System.out.println("❌ Erro ao excluir livro.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    
    private static void exibirEstatisticas(LivroDAO dao) {
        List<Livro> livros = dao.listarTodos();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro para exibir estatísticas.");
            return;
        }
        
        double totalEstoque = 0;
        double valorTotal = 0;
        for (Livro livro : livros) {
            totalEstoque += livro.getQuantidadeEstoque();
            valorTotal += livro.getPreco() * livro.getQuantidadeEstoque();
        }
        
        System.out.println("\n--- ESTATÍSTICAS ---");
        System.out.println("Total de livros cadastrados: " + livros.size());
        System.out.println("Total de exemplares em estoque: " + (int)totalEstoque);
        System.out.printf("Valor total em estoque: R$ %.2f\n", valorTotal);
    }
}