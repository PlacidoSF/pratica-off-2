package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import modelo.Filme;

public class LeitorCSV {
    public List<Filme> lerFilmes(String caminhoArquivo) {
        List<Filme> filmes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(caminhoArquivo)))) {

            String linha = br.readLine();
            linha = br.readLine();
            
            while (linha != null) {
                String[] itens = linha.split(",");
                
                filmes.add(new Filme(Integer.parseInt(itens[0]), itens[1], Integer.parseInt(itens[2]), itens[3], itens[4]));
                linha = br.readLine();
            }

        return filmes;

        } catch (Exception e) {
           System.out.println("error: " + e.getMessage());
            return null;
        }
    }
}
