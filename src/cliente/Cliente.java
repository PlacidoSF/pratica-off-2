package cliente;

import java.util.ArrayDeque;
import java.util.Deque;

import estruturas.*;
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
        NoAVL no = cache.buscar(id);
        if (no != null) {
            System.out.println("Hit: Filme encontrado no cache. Quantidade de comparações: " + cache.getComparacoesBusca());
            System.out.println(no.getValorFilme());
        }

        else {
            System.out.println("Miss: Filme não encontrado no cache. Solicitando ao servidor...");
            // futuramente aqui eu chamo o servidor
        }
    }

    public void atualizarCache(Filme filme) {
        if (fila.size() == limite) {
            cache.remover(fila.poll());
        }

        fila.offer(filme.getId());
        cache.inserir(filme.getId(), filme);
    }
}
