package servidor;

import estruturas.ListaLigada;
import estruturas.TabelaHash;
import estruturas.NoLista;
import modelo.Filme;

public class Servidor {
    private ListaLigada dados;
    private TabelaHash index;

    public Servidor() {
        this.dados = new ListaLigada();
        this.index = new TabelaHash();
    }

    public void cadastrarFilme(Filme filmeValor) {
        NoLista novoNo = this.dados.inserir(filmeValor);
        this.index.inserir(filmeValor.getId(), novoNo);
    }

    public Filme buscarComIndice(int id) {
        NoLista resultado = this.index.buscar(id);
        if (resultado != null) {
            return resultado.getValorFilme();
        }
        return null;
    }

    public Filme buscarSemIndice(int id) {
        NoLista resultado = this.dados.buscar(id);
        if (resultado != null) {
            return resultado.getValorFilme();
        }
        return null;
    }

    public int getComparacoesIndex() {
        return this.index.getComparacoesBusca();
    }

    public int getComparacoesFisica() {
        return this.dados.getComparacoesBusca();
    }
}
