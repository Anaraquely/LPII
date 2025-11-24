package org.example.model;

public class LivroDigital extends Livro {
    private double tamanhoArquivoMB;
    private String formato;

    public LivroDigital() { this.tipo = "digital"; }
    public LivroDigital(int id, String titulo, int anoPublicacao, int autorId,
                        double tamanhoArquivoMB, String formato) {
        super(id, titulo, anoPublicacao, autorId, "digital");
        this.tamanhoArquivoMB = tamanhoArquivoMB;
        this.formato = formato;
    }

    public double getTamanhoArquivoMB() { 
        return tamanhoArquivoMB; }
    public void setTamanhoArquivoMB(double tamanhoArquivoMB) {
        this.tamanhoArquivoMB = tamanhoArquivoMB; }

    public String getFormato() { 
        return formato; }
    public void setFormato(String formato) { 
        this.formato = formato; }
}