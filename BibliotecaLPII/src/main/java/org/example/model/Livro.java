package org.example.model;

public class Livro {
    protected int id;
    protected String titulo;
    protected int anoPublicacao;
    protected int autorId;
    protected String tipo;

    public Livro() {}
    public Livro(int id, String titulo, int anoPublicacao, int autorId, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.autorId = autorId;
        this.tipo = tipo;
    }

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public int getAutorId() { return autorId; }
    public void setAutorId(int autorId) { this.autorId = autorId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
