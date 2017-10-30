package br.com.bhl.superfid.controller;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.ItemCarrinho;

public class ComprasAdapter extends RecyclerView.Adapter {

    private List<ItemCarrinho> itemCarrinhos;
    private Context context;

    ComprasViewHolder holder;
    ItemCarrinho itemCarrinho;
    Bitmap imagem;

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
        holder = (ComprasViewHolder) viewHolder;

        itemCarrinho = itemCarrinhos.get(position);

        NumberFormat formataReal = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        holder.descricao.setText(itemCarrinho.getProduto().getDescricao() + " " + itemCarrinho.getProduto().getMarca());
        holder.precoUnitario.setText("" + formataReal.format( itemCarrinho.getProduto().getPrecoUnitario()));
        holder.validade.setText("Dt. Validade: " + itemCarrinho.getProduto().getDataValidade());
        holder.quantidade.setText("Quantidade: " + itemCarrinho.getQuantidade());

        if(itemCarrinho.getProduto().getCodigo() == 51){
            holder.imagem.setImageResource(R.drawable.bolacha_bono);
        }else if(itemCarrinho.getProduto().getCodigo() == 61){
            holder.imagem.setImageResource(R.drawable.cocacola);
        }else if(itemCarrinho.getProduto().getCodigo() == 91){
            holder.imagem.setImageResource(R.drawable.batatapalha);
        }else if(itemCarrinho.getProduto().getCodigo() == 81){
            holder.imagem.setImageResource(R.drawable.heins);
        }else if(itemCarrinho.getProduto().getCodigo() == 71){
            holder.imagem.setImageResource(R.drawable.farofa);
        }
/*
            new Thread(new Runnable()

            {
                public void run() {

                    try {
                        URL url;
                        url = new URL(itemCarrinho.getProduto().getUrlImagem());
                        imagem = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        holder.imagem.setImageBitmap(imagem);
                    } catch (Exception e) {
                        Log.d("COMPRAS_IMAGEM", e.toString());
                    }

                }
            }).start();
*/
    }

    @Override
    public int getItemCount() {
        return itemCarrinhos.size();
    }
}
