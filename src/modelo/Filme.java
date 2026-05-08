package modelo;

public class Filme {
    private int id;
    private String nome;
    private int ano;
    private String sinopse;
    private String genero;

    public Filme() {    
    }

    public Filme(int id, String nome, int ano, String sinopse, String genero) {
        setId(id);
        setNome(nome);
        setAno(ano);
        setSinopse(sinopse);
        setGenero(genero);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getAno() {
        return ano;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getGenero() {
        return genero;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        }
    }

    public void setNome(String nome) {
        if (nome != null) {
            this.nome = nome;
        }
    }

    public void setAno(int ano) {
        if (ano > 0) {
            this.ano = ano;
        }
    }

    public void setSinopse(String sinopse) {
        if (sinopse != null) {
            this.sinopse = sinopse;
        }
    }

    public void setGenero(String genero) {
        if (genero != null) {
            this.genero = genero;
        }
    }
}
