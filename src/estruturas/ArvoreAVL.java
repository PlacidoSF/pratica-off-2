package estruturas;
import modelo.Filme;

public class ArvoreAVL {
    private NoAVL raiz;
    private int comparacoesBusca;

    public ArvoreAVL() {}

    public ArvoreAVL(NoAVL raiz) {
        setRaiz(raiz);
    }

    public NoAVL getRaiz() {
        return raiz;
    }
    
    public void setRaiz(NoAVL raiz) {
        this.raiz = raiz;
    }

    public int getComparacoesBusca() {
        return comparacoesBusca;
    }  

    public NoAVL buscar(int chave) {
        this.comparacoesBusca = 0;
        return this.buscar(getRaiz(), chave);
    }

    private NoAVL buscar(NoAVL arv, int chave) {
        if (arv == null) {
            return null;
        }

        this.comparacoesBusca++;
        if (arv.getChave() > chave) {
            return this.buscar(arv.getEsquerda(), chave);
        } 
        
        if (arv.getChave() < chave) {
            return this.buscar(arv.getDireita(), chave);
        } else {
            return arv;
        }
    }

    public int maior(int a, int b) {
        return (a > b) ? a : b;
    }

    public int altura(NoAVL arv) {
        if (arv == null) {
            return -1;
        } 
        return arv.getAltura();
    }

    public int obterFB(NoAVL arv) {
        if (arv == null) {
            return 0;
        }
        return this.altura(arv.getEsquerda()) - this.altura(arv.getDireita());
    }

    public void inserir(int chave, Filme valorFilme) {
        this.raiz = this.inserir(this.raiz, chave, valorFilme);
    }

    private NoAVL inserir(NoAVL arv, int chave, Filme valorFilme) {
        if (arv == null) {
            return new NoAVL(chave, valorFilme);
        }

        else if (chave < arv.getChave()) {
            arv.setEsquerda(this.inserir(arv.getEsquerda(), chave, valorFilme));
        } else if (chave > arv.getChave()) {
            arv.setDireita(this.inserir(arv.getDireita(), chave, valorFilme));
        } else {
            return arv;
        }

        arv = this.verificarBalanceamento(arv);

        return arv; 
    }

    public void remover(int chave) {
        this.raiz = this.remover(this.raiz, chave);
    }

    private NoAVL remover(NoAVL arv, int chave) {
        if (arv == null) {
            return arv;
        }

        if (chave < arv.getChave()) {
            arv.setEsquerda(remover(arv.getEsquerda(), chave));
        }

        else if (chave > arv.getChave()) {
            arv.setDireita(remover(arv.getDireita(), chave));
        }

        else {

            //nó encontrado
            //caso 1: nó sem filhos
            if (arv.getEsquerda() == null && arv.getDireita() == null) {
                return null;
            }

            //caso 2.1: nó só tem filho a direita
            else if (arv.getEsquerda() == null) {
                NoAVL temp = arv;
                arv = arv.getDireita();
                temp = null;
            }

            //caso 2.2: nó só tem filho a esquerda
            else if (arv.getDireita() == null) {
                NoAVL temp = arv;
                arv = arv.getEsquerda();
                temp = null;
            }

            else {

                //caso 3: nó com dois filhos
                NoAVL temp = this.noMenorChave(arv.getDireita());

                arv.setChave(temp.getChave());
                arv.setValorFilme(temp.getValorFilme());

                temp.setChave(chave);

                arv.setDireita(this.remover(arv.getDireita(), chave));
            }
        }

        if (arv == null) {
            return arv;
        }

        arv = this.verificarBalanceamento(arv);

        return arv;
    }

    private NoAVL noMenorChave(NoAVL no) {
        NoAVL temp = no;

        while (temp.getEsquerda() != null) {
            temp = temp.getEsquerda();
        }
        return temp;
    }


    public NoAVL verificarBalanceamento(NoAVL arv) {

        // atualiza altura do pai do nó inserido
        arv.setAltura(1 + this.maior(this.altura(arv.getEsquerda()), this.altura(arv.getDireita())));

        //verifica o fator de balanceamento do nó pai do nó inserido
        int fb = this.obterFB(arv);
        int fbEsquerda = this.obterFB(arv.getEsquerda());
        int fbDireita = this.obterFB(arv.getDireita());

        // caso 1: rotação direita simples
        if (fb > 1 && fbEsquerda >= 0) {
            arv = this.rds(arv);
        }

        // caso 2: rotação esquerda simples
        if (fb < -1 && fbDireita <= 0) {
            arv = this.res(arv);
        }

        // caso 3: rotação direita dupla
        if (fb > 1 && fbEsquerda < 0) {
            arv.setEsquerda(this.res(arv.getEsquerda()));
            arv = this.rds(arv);
        }

        // caso 4: rotação esquerda dupla
        if (fb < -1 && fbDireita > 0) {
            arv.setDireita(this.rds(arv.getDireita()));
            arv = this.res(arv);
        }

        return arv;
    }

    public NoAVL rds(NoAVL y) {
        NoAVL x = y.getEsquerda();
        NoAVL z = x.getDireita();

        // rotação
        x.setDireita(y);
        y.setEsquerda(z);
        
        //ajustando a altura
        y.setAltura(1 + this.maior(this.altura(y.getEsquerda()), this.altura(y.getDireita())));
        x.setAltura(1 + this.maior(this.altura(x.getEsquerda()), this.altura(x.getDireita())));

        return x;
    }

    public NoAVL res(NoAVL x) {
        NoAVL y = x.getDireita();
        NoAVL z = y.getEsquerda();

        // rotação
        y.setEsquerda(x);
        x.setDireita(z);
        
        //ajustando a altura
        x.setAltura(1 + this.maior(this.altura(x.getEsquerda()), this.altura(x.getDireita())));
        y.setAltura(1 + this.maior(this.altura(y.getEsquerda()), this.altura(y.getDireita())));

        return y;
    }
}
