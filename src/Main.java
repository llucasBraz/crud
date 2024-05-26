import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static class Book {
        String title;
        String author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        @Override
        public String toString() {
            return "Índice: " + books.indexOf(this) + " | Livro: " + title + " | Autor: " + author;
        }
    }

    static List<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadBooksFromFile();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Listar Livros");
            System.out.println("3. Atualizar Livro");
            System.out.println("4. Deletar Livro");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (choice) {
                case 1:
                    System.out.print("Digite o título do livro: ");
                    String title = scanner.nextLine();
                    System.out.print("Digite o autor do livro: ");
                    String author = scanner.nextLine();
                    books.add(new Book(title, author));
                    saveBooksToFile();
                    System.out.println("Livro adicionado com sucesso!");
                    break;
                case 2:
                    if (books.isEmpty()) {
                        System.out.println("Nenhum livro cadastrado.");
                    } else {
                        System.out.println("Lista de Livros:");
                        for (Book book : books) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 3:
                    if (books.isEmpty()) {
                        System.out.println("Nenhum livro cadastrado para atualizar.");
                    } else {
                        System.out.print("Digite o índice do livro que deseja atualizar: ");
                        int index = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer

                        if (index >= 0 && index < books.size()) {
                            System.out.print("Digite o novo título do livro: ");
                            String newTitle = scanner.nextLine();
                            System.out.print("Digite o novo autor do livro: ");
                            String newAuthor = scanner.nextLine();
                            books.set(index, new Book(newTitle, newAuthor));
                            saveBooksToFile();
                            System.out.println("Livro atualizado com sucesso!");
                        } else {
                            System.out.println("Índice inválido.");
                        }
                    }
                    break;
                case 4:
                    if (books.isEmpty()) {
                        System.out.println("Nenhum livro cadastrado para deletar.");
                    } else {
                        System.out.print("Digite o índice do livro que deseja deletar: ");
                        int deleteIndex = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer

                        if (deleteIndex >= 0 && deleteIndex < books.size()) {
                            books.remove(deleteIndex);
                            saveBooksToFile();
                            System.out.println("Livro deletado com sucesso!");
                        } else {
                            System.out.println("Índice inválido.");
                        }
                    }
                    break;
                case 5:
                    System.out.println("Saindo do programa. Adeus!");
                    scanner.close(); // Fechando o scanner antes de sair
                    return; // Encerra o programa
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    private static void saveBooksToFile() {
        try (PrintWriter writer = new PrintWriter("books.txt")) {
            for (Book book : books) {
                writer.println(book.title + "," + book.author);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    private static void loadBooksFromFile() {
        try (Scanner scanner = new Scanner(new File("books.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    books.add(new Book(parts[0], parts[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de livros não encontrado. Será criado um novo.");
        }
    }
}
