package estruturas;
import modelo.Filme;

public class NoAVL {
    private int chave;
    private NoAVL esquerda, direita;
    private int altura;
    private Filme valorFilme;

    public NoAVL(int chave, Filme filme) {
        setChave(chave);
        setValorFilme(filme);
        setEsquerda(null);
        setDireita(null);
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public NoAVL getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(NoAVL esquerda) {
        this.esquerda = esquerda;
    }

    public NoAVL getDireita() {
        return direita;
    }

    public void setDireita(NoAVL direita) {
        this.direita = direita;
    }

    public Filme getValorFilme() {
        return valorFilme;
    }

    public void setValorFilme(Filme valorFilme) {
        this.valorFilme = valorFilme;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
