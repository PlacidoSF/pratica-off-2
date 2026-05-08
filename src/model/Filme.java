package model;

public class Filme {
    private Integer id;
    private String nome;
    private Integer ano;
    private String sinopse;
    private String genero;

    public Filme() {    
    }

    public Filme(Integer id, String nome, Integer ano, String sinopse, String genero) {
        setId(id);
        setNome(nome);
        setAno(ano);
        setSinopse(sinopse);
        setGenero(genero);
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getAno() {
        return ano;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getGenero() {
        return genero;
    }

    public void setId(Integer id) {
        if (id != null && id > 0) {
            this.id = id;
        }
    }

    public void setNome(String nome) {
        if (nome != null) {
            this.nome = nome;
        }
    }

    public void setAno(Integer ano) {
        if (ano != null && ano > 0) {
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
