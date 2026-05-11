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
        List<Filme> catalogo = leitor.lerFilmes("filmes.csv");
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
            System.out.println("\n===================================================");
            System.out.println("      SISTEMA DE BUSCA DE FILMES");
            System.out.println("===================================================");
            System.out.println("1. Buscar Filme por ID");
            System.out.println("2. Executar Roteiro de Testes");
            System.out.println("0. Sair do Sistema");
            System.out.println("===================================================");
            System.out.print("Escolha uma opção: ");

            String opcao = input.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Digite o ID do filme [1/1000]: ");
                    int id = Integer.parseInt(input.nextLine());
                    cliente.solicitarFilme(id);
                    break;

                case "2":
                    testePadrao(cliente);
                    break;

                case "0":
                    System.out.println("\nEncerrando o sistema... Até logo!");
                    continuar = false;
                    break;

                default:
                    System.out.println("\n[AVISO] Opção inválida. Tente novamente.");
                    break;
            }
        }

        input.close();
    }


    public static void testePadrao(Cliente cliente) {
        System.out.println("\n>>> INICIANDO ROTEIRO DE TESTES AUTOMATIZADO <<<\n");

        int[] testeHit = {1, 10, 20, 30, 40, 50};
        for (int id : testeHit) {
            cliente.solicitarFilme(id);
        }

        int[] testeMiss = {500, 600, 700, 800, 900, 1000};
        for (int id : testeMiss) {
            cliente.solicitarFilme(id);
        }

        int[] testeFalha = {1001, 1100};
        for (int id : testeFalha) {
            cliente.solicitarFilme(id);
        }

        System.out.println("\n>>> ROTEIRO FINALIZADO COM SUCESSO <<<");
    }
}