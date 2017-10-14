package br.com.bhl.superfid.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.ItemCarrinho;
import br.com.bhl.superfid.model.Produto;

public class ComprasAdapter extends RecyclerView.Adapter {

    private List<ItemCarrinho> itemCarrinhos;
    private Context context;

    public ComprasAdapter(List<ItemCarrinho> itemCarrinhos, Context context) {
        this.itemCarrinhos = itemCarrinhos;
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


        ItemCarrinho itemCarrinho = itemCarrinhos.get(position);

        holder.descricao.setText(itemCarrinho.getProduto().getDescricao());
        holder.precoUnitario.setText("" + itemCarrinho.getProduto().getPrecoUnitario());
        holder.unidades.setText("" + itemCarrinho.getProduto().getUnidade());
        holder.validade.setText("" + itemCarrinho.getProduto().getDataValidade());
        holder.quantidade.setText(""+ itemCarrinho.getQuantidade());
    }

    @Override
    public int getItemCount() {
        return itemCarrinhos.size();
    }
}
