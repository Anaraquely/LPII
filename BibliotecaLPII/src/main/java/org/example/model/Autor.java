package org.example.model;

public class Autor extends Pessoa {
    private String nacionalidade;

    public Autor() { super(); }
    public Autor(int id, String nome, String nacionalidade) {
        super(id, nome);
        this.nacionalidade = nacionalidade;
    }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
}
