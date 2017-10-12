package br.com.bhl.superfid.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.Produto;

/**
 * Created by hericles on 28/05/17.
 */

public class ComprasAdapter extends RecyclerView.Adapter {

    private List<Produto> produtos;
    private Context context;

    public ComprasAdapter(List<Produto> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.viewholder, parent, false);

        ComprasViewHolder holder = new ComprasViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ComprasViewHolder holder = (ComprasViewHolder) viewHolder;

        Produto produto = produtos.get(position);

        holder.descricao.setText(produto.getDescricao());
        holder.precoUnitario.setText("" + produto.getPrecoUnitario());
        holder.unidades.setText("" + produto.getUnidades());
        holder.validade.setText("" + produto.getDataValidade());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}
