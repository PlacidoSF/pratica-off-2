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
        boolean continuar = true;
        
        while (continuar) {
            limparTela();
            System.out.println("\n===================================================");
            System.out.println("      SISTEMA DE BUSCA DE FILMES");
            System.out.println("===================================================");
            System.out.println("1. Buscar Filme por ID");
            System.out.println("2. Executar Roteiro de Testes");
            System.out.println("3. Acessar Catálogo Completo");
            System.out.println("0. Sair do Sistema");
            System.out.println("===================================================");
            System.out.print("Escolha uma opção: ");

            String opcao = input.nextLine();

            switch (opcao) {
                case "1":
                    limparTela();
                    System.out.print("Digite o ID do filme [1/1000]: ");
                    int id = Integer.parseInt(input.nextLine());
                    cliente.solicitarFilme(id);
                    pausar(input);
                    break;

                case "2":
                    limparTela();
                    testePadrao(cliente);
                    pausar(input);
                    break;

                case "3":
                menuPaginacao(servidor, input);
                break;

                case "0":
                    System.out.println("\nEncerrando o sistema... Fim.");
                    continuar = false;
                    break;

                default:
                    System.out.println("\n[AVISO] Opção inválida. Tente novamente.");
                    pausar(input);
                    break;
            }
        }

        input.close();


    }

    public static void testePadrao(Cliente cliente) {
        System.out.println("\n>>> INICIANDO ROTEIRO DE TESTES AUTOMATIZADO <<<\n");

        System.out.println("--- TESTE 1: BUSCAS COM SUCESSO NO CACHE (HITS) ---");
        System.out.println("Testando IDs que já estão carregados na memória cache...");
        int[] testeHit = {1, 10, 20, 30, 40, 50};
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
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void pausar(Scanner teclado) {
        System.out.println("\nPressione [ENTER] para continuar...");
        teclado.nextLine();
    }

    private static void menuPaginacao(Servidor servidor, Scanner teclado) {
        int paginaAtual = 1;
        int totalPaginas = 50;
        boolean navegando = true;

        while (navegando) {
            limparTela();
            System.out.println("===============================================================");
            System.out.println("          CATÁLOGO DE FILMES (SERVIDOR) - PÁGINA " + paginaAtual + "/" + totalPaginas);
            System.out.println("===============================================================");
           

            System.out.printf("%-5s | %-40s | %s\n", "ID", "NOME", "GÊNERO");
            System.out.println("---------------------------------------------------------------");

            
            List<Filme> paginaDeFilmes = servidor.obterPagina(paginaAtual, 20);

            for (Filme f : paginaDeFilmes) {
                String nome = f.getNome();
                System.out.printf("%-5d | %-40s | %s\n", f.getId(), nome, f.getCategoria());
            }

            System.out.println("===============================================================");
            System.out.println("[P] Próxima Página  |  [V] Voltar Página  |  [S] Sair");
            System.out.print("Escolha uma ação: ");
            
            String acao = teclado.nextLine().toUpperCase();

            switch (acao) {
                case "P":
                    if (paginaAtual < totalPaginas) {
                        paginaAtual++;
                    }
                    break;
                case "V":
                    if (paginaAtual > 1) {
                        paginaAtual--;
                    }
                    break;
                case "S":
                    navegando = false;
                    break;
                default:
                    break;
            }
        }
    }
}