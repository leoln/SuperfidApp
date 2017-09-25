package br.com.bhl.superfid.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.bhl.superfid.R;

/**
 * Created by hericles on 28/05/17.
 */

public class ComprasViewHolder extends RecyclerView.ViewHolder {
    final TextView descricao;
    final TextView precoUnitario;
    final TextView validade;
    final TextView unidades;

    public ComprasViewHolder(View view) {
        super(view);
        descricao = (TextView) view.findViewById(R.id.item_produto_descricao);
        precoUnitario = (TextView) view.findViewById(R.id.item_produto_precoUnitario);
        validade = (TextView) view.findViewById(R.id.item_produto_validade);
        unidades = (TextView) view.findViewById(R.id.item_produto_unidades);
    }
}
