package br.com.bhl.superfid.model;

import java.io.Serializable;

/**
 * Created by hericlespontes on 29/10/2017.
 */

public class Compra implements Serializable{

    private long codigoCarrinho;
    private long codigoUsuario;
    private String dataInicio;
    private String dataTermino;
    private double precoTotal;
    private int indicadorFinalizado;


    public long getCodigoCarrinho() {
        return codigoCarrinho;
    }

    public void setCodigoCarrinho(long codigoCarrinho) {
        this.codigoCarrinho = codigoCarrinho;
    }

    public long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public int getIndicadorFinalizado() {
        return indicadorFinalizado;
    }

    public void setIndicadorFinalizado(int indicadorFinalizado) {
        this.indicadorFinalizado = indicadorFinalizado;
    }
}
