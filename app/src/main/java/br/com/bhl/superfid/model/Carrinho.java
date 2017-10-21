package br.com.bhl.superfid.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrinho implements Serializable{

    private Long codigo;
    private List<ItemCarrinho> listaCarrinho;
    private String dataCriacao;
    private double subtotal;

    public Carrinho() {
        listaCarrinho = new ArrayList<>();
        subtotal = 0.0;
    }

    public List<ItemCarrinho> getListaCarrinho() {
        return listaCarrinho;
    }

    public void setListaCarrinho(ItemCarrinho itemCarrinho){
        int count=0; //usado para saber se item ja existe para evitar duplicatas

        if(listaCarrinho.isEmpty()){
            //se nao houver produtos
            this.listaCarrinho.add(itemCarrinho);
        }else {
            //se houver produtos, verificar duplicatas
            for (ItemCarrinho item : listaCarrinho) {
                if (itemCarrinho.getProduto().getCodigoRfid().equals(item.getProduto().getCodigoRfid())) {
                    item.setQuantidade(item.getQuantidade() + 1);
                    count++;//para mostrar que passou por aqui
                    break;
                }else{
                    continue;
                }
            }
            if(count==0){//se for zero, nao passou pelo atualizador de quantidade
                //isso significa que é um item novo e que pode simplesmente adicionar
                listaCarrinho.add(itemCarrinho);
            }//se count for 1 significa que atualizou quantidade e nao precisa fazer mais nada
        }
        somaSubtotal( itemCarrinho ); //a cada atualizacao, atualiza o subtotal
    }

    public void somaSubtotal( ItemCarrinho itemCarrinho ) {
        subtotal += ( itemCarrinho.getProduto().getPrecoUnitario() * itemCarrinho.getProduto().getUnidade() ) * itemCarrinho.getQuantidade();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Carrinho [codigo=" + codigo + ", dataCriacao=" + dataCriacao + ", listaCarrinho=" + listaCarrinho + ", subtotal=" + subtotal + "]";
    }

}
