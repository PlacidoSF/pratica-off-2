package estruturas;
import modelo.Filme;
public class NoHash {
    private int chave;
    private Filme valorFilme;
    private NoHash proxNo;

    public NoHash(int chave, Filme valorFilme) {
        this.chave = chave;
        this.valorFilme = valorFilme;
        this.proxNo = null;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public Filme getValorFilme() {
        return valorFilme;
    }

    public void setValorFilme(Filme valorFilme) {
        this.valorFilme = valorFilme;
    }

    public NoHash getProxNo() {
        return proxNo;
    }

    public void setProxNo(NoHash proxNo) {
        this.proxNo = proxNo;
    }
}
