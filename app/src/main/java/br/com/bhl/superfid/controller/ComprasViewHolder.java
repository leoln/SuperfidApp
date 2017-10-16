package br.com.bhl.superfid.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.bhl.superfid.R;
public class ComprasViewHolder extends RecyclerView.ViewHolder {
    final TextView descricao;
    final TextView precoUnitario;
    final TextView validade;
    final TextView quantidade;
    final ImageView imagem;

    public ComprasViewHolder(View view) {
        super(view);
        descricao = (TextView) view.findViewById(R.id.item_produto_descricao);
        precoUnitario = (TextView) view.findViewById(R.id.item_produto_precoUnitario);
        validade = (TextView) view.findViewById(R.id.item_produto_validade);
        quantidade = (TextView) view.findViewById(R.id.item_carrinho_quantidade);
        imagem = (ImageView) view.findViewById(R.id.item_produto_imagem);
    }
}
