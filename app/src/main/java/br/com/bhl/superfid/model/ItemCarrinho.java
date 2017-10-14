package br.com.bhl.superfid.model;

/**
 * Created by hericlespontes on 14/10/17.
 */

public class ItemCarrinho {

    private Produto produto;
    private double quantidade;

    public ItemCarrinho() {
    }

    public ItemCarrinho(Produto produto, double quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
