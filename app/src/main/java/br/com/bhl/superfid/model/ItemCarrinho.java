package br.com.bhl.superfid.model;

import java.io.Serializable;

public class ItemCarrinho implements Serializable{

    private Long codigoItem;

    private Long codigoCarrinho;

    private transient Carrinho carrinho;

    private Long codigoProduto;

    private transient Produto produto;

    private Double quantidade;

    public ItemCarrinho() {
        this.codigoItem = (long)(Math.random() * 1000000000 * Math.random()) * hashCode();
        this.quantidade = 1.0;
    }

    public void setCodigoCarrinho(Long codigoCarrinho) {
        this.codigoCarrinho = codigoCarrinho;
    }

    public void setCodigoProduto(Long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}