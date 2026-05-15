package cliente;

import java.util.ArrayDeque;
import java.util.Deque;

import cliente.cache.ArvoreAVL;
import cliente.cache.NoAVL;
import modelo.Filme;
import servidor.Servidor;


public class Cliente {
    private Deque<Integer> fila = new ArrayDeque<>();
    private static final int limite = 50;
    private Servidor servidor;
    private ArvoreAVL cache;

    public Cliente(Servidor servidor) {
        this.servidor = servidor;
        this.cache = new ArvoreAVL();
    }
    
    public void solicitarFilme(int id) {

        System.out.println("===================================================");
        System.out.println("[BUSCA SOLICITADA] ID: " + id);
        System.out.println("---------------------------------------------------");

        NoAVL no = cache.buscar(id);

        if (no != null) {
            System.out.println("[HIT] Filme encontrado no Cache (AVL).");
            System.out.println("> Comparações na Árvore: "+ cache.getComparacoesBusca());
            System.out.println("---------------------------------------------------");
            System.out.println(no.getValorFilme());
        }

        else {

            System.out.println("[MISS] Filme não encontrado no Cache.");
            System.out.println("[SERVIDOR] Buscando dados no backend...");

            //busca na tabela Hash
            Filme filmeIndex = servidor.buscarComIndice(id);

            //busca na banco de dados
            Filme filmeFisica = servidor.buscarSemIndice(id);

            if (filmeIndex != null && filmeFisica != null) {

                System.out.println("> ÍNDICE (Hash): " + servidor.getComparacoesIndex() + " comparações");
                System.out.println("> DISCO (Lista): " + servidor.getComparacoesFisica() + " comparações");
                System.out.println("---------------------------------------------------");
                System.out.println("[FILME ENCONTRADO NO SERVIDOR]");
                System.out.println(filmeIndex);

                atualizarCache(filmeIndex);

            } else {

                System.out.println("> ÍNDICE (Hash): " + servidor.getComparacoesIndex() + " comparações");
                System.out.println("> DISCO (Lista): " + servidor.getComparacoesFisica() + " comparações");
                System.out.println("---------------------------------------------------");
                System.out.println("[ERRO] Filme não existe no banco de dados.");     

            }
        }
        System.out.println("===================================================\n");
    }

    public void atualizarCache(Filme filme) {
        if (fila.size() == limite) {
            int idFilmeRemovido = fila.poll();

            Filme filmeRemovido = cache.buscar(idFilmeRemovido).getValorFilme();

            System.out.println("---------------------------------------------------");
            System.out.println("[AVISO DE CACHE] Limite máximo (" + limite + ") atingido. Aplicando regra FIFO...");
            System.out.println("Removendo: " + filmeRemovido.getNome() + " (ID: " + idFilmeRemovido + ")");
            System.out.println("---------------------------------------------------");
            cache.remover(idFilmeRemovido);
        }

        fila.offer(filme.getId());
        cache.inserir(filme.getId(), filme);
    }
}
