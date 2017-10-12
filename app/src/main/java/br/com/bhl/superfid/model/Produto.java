package br.com.bhl.superfid.model;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Produto implements Serializable {

    private long codigo;
    private String codigoRfid;
    private String descricao;
    private String marca;
    private double precoUnitario;
    private String dataValidade;
    private String codigoLote;
    private String unidade;
    private String urlImagem;

    public Produto() { }

    public Produto(long codProduto, String codigoRfid, String descricao, String marca, double precoUnitario, String validade, String codigoLote, String unidades, String urlImagem) {
        this.codigo = codProduto;
        this.codigoRfid = codigoRfid;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.marca = marca;
        this.dataValidade = validade;
        this.codigoLote = codigoLote;
        this.unidade = unidades;
        this.urlImagem= urlImagem;
    }

    public Produto(String codigoRfid, String descricao, String marca, double precoUnitario, String dataValidade, String codigoLote, String unidades, String urlImagem) {
        this.codigoRfid = codigoRfid;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.marca = marca;
        this.dataValidade = dataValidade;
        this.codigoLote = codigoLote;
        this.unidade = unidades;
        this.urlImagem= urlImagem;
    }

    public long getCodProduto() {
        return codigo;
    }

    public void setCodProduto(long codProduto) {
        this.codigo = codProduto;
    }

    public String getCodRfid() {
        return codigoRfid;
    }

    public void setCodRfid(String codigoRfid) {
        this.codigoRfid = codigoRfid;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        DateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        this.dataValidade = dataFormatada.format(dataValidade);
    }

    public String getLote() {
        return codigoLote;
    }

    public void setLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public String getUnidades() {
        return unidade;
    }

    public void setUnidades(String unidades) {
        this.unidade = unidades;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    @Override
    public String toString() {
        return "Produto [codProduto=" + codigo + ", descricao=" + descricao + ", precoUnitario=" + precoUnitario
                + ", marca=" + marca + ", validade=" + dataValidade + ", codigoLote=" + codigoLote + ", unidade=" + unidade
                + ", urlImagem=" + urlImagem + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (codigo ^ (codigo >>> 32));
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((codigoLote == null) ? 0 : codigoLote.hashCode());
        result = prime * result + ((marca == null) ? 0 : marca.hashCode());
        long temp;
        temp = Double.doubleToLongBits(precoUnitario);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
        result = prime * result + ((urlImagem == null) ? 0 : urlImagem.hashCode());
        result = prime * result + ((dataValidade == null) ? 0 : dataValidade.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Produto other = (Produto) obj;
        if (codigo != other.codigo)
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (codigoLote == null) {
            if (other.codigoLote != null)
                return false;
        } else if (!codigoLote.equals(other.codigoLote))
            return false;
        if (marca == null) {
            if (other.marca != null)
                return false;
        } else if (!marca.equals(other.marca))
            return false;
        if (Double.doubleToLongBits(precoUnitario) != Double.doubleToLongBits(other.precoUnitario))
            return false;
        if (unidade == null) {
            if (other.unidade != null)
                return false;
        } else if (!unidade.equals(other.unidade))
            return false;
        if (urlImagem == null) {
            if (other.urlImagem != null)
                return false;
        } else if (!urlImagem.equals(other.urlImagem))
            return false;
        if (dataValidade == null) {
            if (other.dataValidade != null)
                return false;
        } else if (!dataValidade.equals(other.dataValidade))
            return false;
        return true;
    }
}