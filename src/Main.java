import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static class Movie {
        String title;
        String director;

        public Movie(String title, String director) {
            this.title = title;
            this.director = director;
        }

        @Override
        public String toString() {
            return "Índice: " + movies.indexOf(this) + " | Filme: " + title + " | Diretor: " + director;
        }
    }

    static List<Movie> movies = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadMoviesFromFile();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Adicionar Filme");
            System.out.println("2. Listar Filmes");
            System.out.println("3. Atualizar Filme");
            System.out.println("4. Deletar Filme");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (choice) {
                case 1:
                    System.out.print("Digite o título do filme: ");
                    String title = scanner.nextLine();
                    System.out.print("Digite o diretor do filme: ");
                    String director = scanner.nextLine();
                    movies.add(new Movie(title, director));
                    saveMoviesToFile();
                    System.out.println("Filme adicionado com sucesso!");
                    break;
                case 2:
                    if (movies.isEmpty()) {
                        System.out.println("Nenhum filme cadastrado.");
                    } else {
                        System.out.println("Lista de Filmes:");
                        for (Movie movie : movies) {
                            System.out.println(movie);
                        }
                    }
                    break;
                case 3:
                    if (movies.isEmpty()) {
                        System.out.println("Nenhum filme cadastrado para atualizar.");
                    } else {
                        System.out.print("Digite o índice do filme que deseja atualizar: ");
                        int index = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer

                        if (index >= 0 && index < movies.size()) {
                            System.out.print("Digite o novo título do filme: ");
                            String newTitle = scanner.nextLine();
                            System.out.print("Digite o novo diretor do filme: ");
                            String newDirector = scanner.nextLine();
                            movies.set(index, new Movie(newTitle, newDirector));
                            saveMoviesToFile();
                            System.out.println("Filme atualizado com sucesso!");
                        } else {
                            System.out.println("Índice inválido.");
                        }
                    }
                    break;
                case 4:
                    if (movies.isEmpty()) {
                        System.out.println("Nenhum filme cadastrado para deletar.");
                    } else {
                        System.out.print("Digite o índice do filme que deseja deletar: ");
                        int deleteIndex = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer

                        if (deleteIndex >= 0 && deleteIndex < movies.size()) {
                            movies.remove(deleteIndex);
                            saveMoviesToFile();
                            System.out.println("Filme deletado com sucesso!");
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

    private static void saveMoviesToFile() {
        try (PrintWriter writer = new PrintWriter("movies.txt")) {
            for (Movie movie : movies) {
                writer.println(movie.title + "," + movie.director);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    private static void loadMoviesFromFile() {
        try (Scanner scanner = new Scanner(new File("movies.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    movies.add(new Movie(parts[0], parts[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de filmes não encontrado. Será criado um novo.");
        }
    }
}
