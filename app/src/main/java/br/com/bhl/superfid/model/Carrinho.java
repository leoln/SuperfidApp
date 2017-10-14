package br.com.bhl.superfid.model;

import android.content.ClipData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hericlespontes on 14/10/17.
 */

public class Carrinho {

    private List<ItemCarrinho> listaCarrinho;
    private double subTotal;

    public Carrinho() {
        listaCarrinho = new ArrayList<>();
        subTotal = 0.0;
    }

    public void setListaCarrinho(ItemCarrinho itemCarrinho){

    }


}
