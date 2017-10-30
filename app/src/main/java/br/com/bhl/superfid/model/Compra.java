package br.com.bhl.superfid.model;

import java.io.Serializable;

public class Compra implements Serializable{

    private Long codigo;
    private Long codigoCarrinho;
    private Long codigoUsuario;
    private String dataInicio;
    private String dataTermino;
    private Double precoTotal;
    private String indicadorFinalizado;
    private String indicadorPagamento;

    public Compra() {
        this.codigo = (long)(Math.random() * 1000000000 * Math.random()) * hashCode();
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigoCarrinho() {
        return codigoCarrinho;
    }

    public void setCodigoCarrinho(Long codigoCarrinho) {
        this.codigoCarrinho = codigoCarrinho;
    }

    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
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

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public String getIndicadorFinalizado() {
        return indicadorFinalizado;
    }

    public void setIndicadorFinalizado(String indicadorFinalizado) {
        this.indicadorFinalizado = indicadorFinalizado;
    }

    public String getIndicadorPagamento() {
        return indicadorPagamento;
    }

    public void setIndicadorPagamento(String indicadorPagamento) {
        this.indicadorPagamento = indicadorPagamento;
    }
}
