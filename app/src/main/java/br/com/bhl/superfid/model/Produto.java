package br.com.bhl.superfid.model;

/**
 * Created by hericles on 28/05/17.
 */

public class Produto {

    private String descricao;
    private double precoUnitario;
    private String marca;
    private String validade;
    private String lote;
    private double unidades;

    public Produto(String descricao, double precoUnitario, String marca, String validade, String lote, double unidades) {
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.marca = marca;
        this.validade = validade;
        this.lote = lote;
        this.unidades = unidades;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public double getUnidades() {
        return unidades;
    }

    public void setUnidades(double unidades) {
        this.unidades = unidades;
    }

}
