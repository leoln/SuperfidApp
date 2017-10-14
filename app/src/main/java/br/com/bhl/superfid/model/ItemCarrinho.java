package br.com.bhl.superfid.model;

public class ItemCarrinho {

    private Produto produto;
    private double quantidade;

    public ItemCarrinho() {
        this.quantidade = 1;
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
