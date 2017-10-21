package br.com.bhl.superfid.model;

import java.io.Serializable;

public class ItemCarrinho implements Serializable{

    private Long codigoItem;

    private transient Carrinho carrinho;

    private Produto produto;

    private double quantidade;

    public ItemCarrinho() {
        this.quantidade = 1;
    }

    public Produto getProduto() {
        return produto;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
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