package br.com.bhl.superfid.model;

import java.io.Serializable;
import java.util.Calendar;

public class Usuario implements Serializable{

    private Long codigoSistema;
    private String codigoAutenticacao;
    private String nome;
    private String sobrenome;
    private String dataNascimento;
    private String emailAutenticacao;
    private long numeroCPF;
    private int numeroDDD;
    private long numeroTelefone;

    public Usuario() { }

    public Long getCodigoSistema() {
        return codigoSistema;
    }

    public void setCodigoSistema(Long codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    public String getCodigoAutenticacao() {
        return codigoAutenticacao;
    }

    public void setCodigoAutenticacao(String codigoAutenticacao) {
        this.codigoAutenticacao = codigoAutenticacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public long getNumeroCPF() {
        return numeroCPF;
    }

    public void setNumeroCPF(long numeroCPF) {
        this.numeroCPF = numeroCPF;
    }

    public int getNumeroDDD() {
        return numeroDDD;
    }

    public void setNumeroDDD(int numeroDDD) {
        this.numeroDDD = numeroDDD;
    }

    public long getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(long numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmailAutenticacao() {
        return emailAutenticacao;
    }

    public void setEmailAutenticacao(String emailAutenticacao) {
        this.emailAutenticacao = emailAutenticacao;
    }

    @Override
    public String toString() {
        return "Usuario [codigoSistema=" + codigoSistema + ", codigoAutenticacao=" + codigoAutenticacao + ", nome="
                + nome + ", sobrenome=" + sobrenome + ", dataNascimento=" + dataNascimento + ", emailAutenticacao="
                + emailAutenticacao + ", numeroCPF=" + numeroCPF + ", numeroDDD=" + numeroDDD + ", numeroTelefone=" + numeroTelefone + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        if (getCodigoSistema() != usuario.getCodigoSistema()) return false;
        if (getNumeroCPF() != usuario.getNumeroCPF()) return false;
        if (getNumeroDDD() != usuario.getNumeroDDD()) return false;
        if (getNumeroTelefone() != usuario.getNumeroTelefone()) return false;
        if (getCodigoAutenticacao() != null ? !getCodigoAutenticacao().equals(usuario.getCodigoAutenticacao()) : usuario.getCodigoAutenticacao() != null)
            return false;
        if (getNome() != null ? !getNome().equals(usuario.getNome()) : usuario.getNome() != null)
            return false;
        if (getSobrenome() != null ? !getSobrenome().equals(usuario.getSobrenome()) : usuario.getSobrenome() != null)
            return false;
        if (getDataNascimento() != null ? !getDataNascimento().equals(usuario.getDataNascimento()) : usuario.getDataNascimento() != null)
            return false;
        return getEmailAutenticacao() != null ? getEmailAutenticacao().equals(usuario.getEmailAutenticacao()) : usuario.getEmailAutenticacao() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getCodigoSistema() ^ (getCodigoSistema() >>> 32));
        result = 31 * result + (getCodigoAutenticacao() != null ? getCodigoAutenticacao().hashCode() : 0);
        result = 31 * result + (getNome() != null ? getNome().hashCode() : 0);
        result = 31 * result + (getSobrenome() != null ? getSobrenome().hashCode() : 0);
        result = 31 * result + (getDataNascimento() != null ? getDataNascimento().hashCode() : 0);
        result = 31 * result + (getEmailAutenticacao() != null ? getEmailAutenticacao().hashCode() : 0);
        result = 31 * result + (int) (getNumeroCPF() ^ (getNumeroCPF() >>> 32));
        result = 31 * result + getNumeroDDD();
        result = 31 * result + (int) (getNumeroTelefone() ^ (getNumeroTelefone() >>> 32));
        return result;
    }

}//fim da classe
