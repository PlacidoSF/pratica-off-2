package app;

import java.util.List;
import java.util.Scanner;

import util.LeitorCSV;
import servidor.Servidor;
import cliente.Cliente;
import modelo.Filme;

public class App {
    public static void main(String[] args) {

        LeitorCSV leitor = new LeitorCSV();
        List<Filme> catalogo = leitor.lerFilmes("resources/filmes.csv");
        Servidor servidor = new Servidor();
        Cliente cliente = new Cliente(servidor);
       
        for (Filme filme : catalogo) {
           servidor.cadastrarFilme(filme);
        }
        
        for (int i = 0; i < 50; i++) {
            cliente.atualizarCache(catalogo.get(i));
        }

        Scanner input = new Scanner(System.in);

        realizarLogin(input);

        int paginaAtual = 1;
        int totalPaginas = 50;
        boolean continuar = true;
        
        while (continuar) {
            limparTela();
            System.out.println("===============================================================");
            System.out.println("          SISTEMA DE STREAMING - PÁGINA " + paginaAtual + "/" + totalPaginas);
            System.out.println("===============================================================");
            System.out.printf("%-5s | %-40s | %s\n", "ID", "NOME", "GÊNERO");
            System.out.println("---------------------------------------------------------------");

            List<Filme> paginaDeFilmes = servidor.obterPagina(paginaAtual, 20);
            for (Filme f : paginaDeFilmes) {
                System.out.printf("%-5d | %-40s | %s\n", f.getId(), f.getNome(), f.getCategoria());
            }

            System.out.println("===============================================================");
            System.out.println("OPÇÕES: [P] Próxima | [V] Voltar | [T] Roteiro de Testes | [S] Sair");
            System.out.println("BUSCA:  Para acessar um filme, digite o seu ID numérico.");
            System.out.print("\nEscolha uma opção ou digite um ID: ");

            String entrada = input.nextLine();
            String comandoUpper = entrada.toUpperCase();

            switch (comandoUpper) {
                case "P":
                    if (paginaAtual < totalPaginas) paginaAtual++;
                    break;
                case "V":
                    if (paginaAtual > 1) paginaAtual--;
                    break;
                case "T":
                    limparTela();
                    testePadrao(cliente);
                    pausar(input);
                    break;
                case "S":
                    System.out.println("\nEncerrando o sistema... Fim.");
                    continuar = false;
                    break;
                default:
                    try {
                        int idBuscado = Integer.parseInt(entrada);
                        limparTela();
                        cliente.solicitarFilme(idBuscado);
                        pausar(input);
                    } catch (NumberFormatException e) {
                        System.out.println("\n[AVISO] Entrada inválida. Por favor, digite um comando válido ou um ID numérico.");
                        pausar(input);
                    }
            }
        }

        input.close();
    }


    private static void realizarLogin(Scanner teclado) {
        boolean autenticado = false;
        while (!autenticado) {
            limparTela();
            System.out.println("===================================================");
            System.out.println("                    BEM-VINDO");
            System.out.println("===================================================");
            System.out.print("Login: ");
            String login = teclado.nextLine();
            System.out.print("Senha: ");
            String senha = teclado.nextLine();

            if (login.equals("admin") && senha.equals("admin")) {
                autenticado = true;
                System.out.println("\n[SUCESSO] Acesso concedido!");
                pausar(teclado);
            } else {
                System.out.println("\n[ERRO] Credenciais inválidas. Tente novamente.");
                pausar(teclado);
            }
        }
    }

    public static void testePadrao(Cliente cliente) {
        System.out.println("\n>>> INICIANDO ROTEIRO DE TESTES AUTOMATIZADO <<<\n");

        System.out.println("--- TESTE 1: BUSCAS COM SUCESSO NO CACHE (HITS) ---");
        System.out.println("Testando IDs que já estão carregados na memória cache...");
        int[] testeHit = {5, 10, 20, 30, 40, 50};
        for (int id : testeHit) {
            cliente.solicitarFilme(id);
        }

        System.out.println("\n--- TESTE 2: BUSCAS NO SERVIDOR (MISS NO CACHE) ---");
        System.out.println("Testando IDs válidos onde os dados não estão no cache e exigem busca no banco...");
        int[] testeMiss = {500, 600, 700, 800, 900, 1000};
        for (int id : testeMiss) {
            cliente.solicitarFilme(id);
        }

        System.out.println("\n--- TESTE 3: BUSCAS INVÁLIDAS (FALHAS) ---");
        System.out.println("Testando IDs que não existem no cache nem no servidor...");
        int[] testeFalha = {1001, 1100};
        for (int id : testeFalha) {
            cliente.solicitarFilme(id);
        }

        System.out.println("\n>>> ROTEIRO FINALIZADO COM SUCESSO <<<");
    }

private static void limparTela() {
        try {
            String sistemaOperacional = System.getProperty("os.name").toLowerCase();
            
            if (sistemaOperacional.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Erro ao limpar a tela: " + e.getMessage());
        }
    }

    private static void pausar(Scanner teclado) {
        System.out.println("\nPressione [ENTER] para continuar...");
        teclado.nextLine();
    }
 
}