package br.com.bhl.superfid.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.controller.ComprasAdapter;
import br.com.bhl.superfid.controller.DividerItemDecoration;
import br.com.bhl.superfid.model.Produto;
import br.com.bhl.superfid.util.WebClient;

public class ComprasActivity extends AppCompatActivity{

    public static RecyclerView recyclerView;
    public static List<Produto> produtos = new ArrayList<>();

    private static TextView subTotal;
    private static double subTotalDouble;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        subTotal = (TextView) findViewById(R.id.subTotal);

        //inicializando a list view de produtos
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setAdapter(new ComprasAdapter(this.produtos, this));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

    }

    public static void addProduto(String codigoRecebido){

        GetJson getJson = new GetJson();
        getJson.execute(codigoRecebido);


    }

    private static class GetJson extends AsyncTask<String, Void, Produto> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Produto doInBackground(String... strings) {
            Produto produto = null;
            Gson gson = new Gson();
            String json = "";
            WebClient webClient = new WebClient();

            try {
                json = webClient.get(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            produto = gson.fromJson(json, Produto.class);

            return produto;
        }

        @Override
        protected void onPostExecute(Produto produto) {
            super.onPostExecute(produto);

            produtos.add(produto);

            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

            if( !produtos.isEmpty() ) {
                for (Produto produtoEach : produtos) {
                    subTotalDouble = ( produtoEach.getPrecoUnitario() * produto.getUnidade() ) + Double.parseDouble(subTotal.getText().toString());
                }
            }

            subTotal.setText(subTotalDouble + "");
        }
    }
}
