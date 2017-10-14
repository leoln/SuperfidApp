package br.com.bhl.superfid.model;

import android.content.ClipData;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private List<ItemCarrinho> listaCarrinho;
    private double subTotal;

    public Carrinho() {
        listaCarrinho = new ArrayList<>();
        subTotal = 0.0;
    }

    public List<ItemCarrinho> getListaCarrinho() {
        return listaCarrinho;
    }

    public void setListaCarrinho(ItemCarrinho itemCarrinho){
        if(listaCarrinho.isEmpty()){
            //se nao houver produtos
            listaCarrinho.add(itemCarrinho);
        }else {
            //se houver produtos, verificar duplicatas
            for (ItemCarrinho item :
                    this.listaCarrinho) {
                if (itemCarrinho.getProduto().equals(item.getProduto())) {
                    item.setQuantidade(item.getQuantidade() + 1);
                } else {
                    listaCarrinho.add(itemCarrinho);
                }
            }
        }
        somaSubTotal( itemCarrinho );
    }

    public void somaSubTotal( ItemCarrinho itemCarrinho ) {
        subTotal += ( itemCarrinho.getProduto().getPrecoUnitario() * itemCarrinho.getProduto().getUnidade() ) * itemCarrinho.getQuantidade();
    }

    public double getSubTotal() {
        return subTotal;
    }
}
