package org.example.model;

public class LivroFisico extends Livro {
    private int numeroPaginas;
    private double pesoGramas;

    public LivroFisico() { this.tipo = "fisico"; }
    public LivroFisico(int id, String titulo, int anoPublicacao, int autorId,
                       int numeroPaginas, double pesoGramas) {
        super(id, titulo, anoPublicacao, autorId, "fisico");
        this.numeroPaginas = numeroPaginas;
        this.pesoGramas = pesoGramas;
    }

    public int getNumeroPaginas() { return numeroPaginas; }
    public void setNumeroPaginas(int numeroPaginas) { this.numeroPaginas = numeroPaginas; }

    public double getPesoGramas() { return pesoGramas; }
    public void setPesoGramas(double pesoGramas) { this.pesoGramas = pesoGramas; }
}