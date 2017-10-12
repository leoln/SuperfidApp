package br.com.bhl.superfid.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.controller.ComprasAdapter;
import br.com.bhl.superfid.controller.DividerItemDecoration;
import br.com.bhl.superfid.model.Produto;

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
        Produto p = new Produto(codigoRecebido, "D:"+codigoRecebido, "Marca", 4.70, "23/10/2018", "L4052", "1", "");
        produtos.add(p);

        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

        //Atualiza subTotal
        String tempSubTotal = subTotal.getText().toString();

        if(tempSubTotal.startsWith("R")){
            subTotal.setText("0");
        }
        subTotalDouble = (p.getPrecoUnitario()*Double.parseDouble(p.getUnidades())) + Double.parseDouble(subTotal.getText().toString());
        subTotal.setText(subTotalDouble+"");
    }
}
