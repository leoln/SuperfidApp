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
    private Long numeroCPF;
    private Integer numeroDDD;
    private Long numeroTelefone;

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

    public Long getNumeroCPF() {
        return numeroCPF;
    }

    public void setNumeroCPF(Long numeroCPF) {
        this.numeroCPF = numeroCPF;
    }

    public Integer getNumeroDDD() {
        return numeroDDD;
    }

    public void setNumeroDDD(Integer numeroDDD) {
        this.numeroDDD = numeroDDD;
    }

    public Long getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(Long numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    @Override
    public String toString() {
        return "Usuario [codigoSistema=" + codigoSistema + ", codigoAutenticacao=" + codigoAutenticacao + ", nome="
                + nome + ", sobrenome=" + sobrenome + ", dataNascimento=" + dataNascimento + ", emailAutenticacao="
                + emailAutenticacao + ", numeroCPF=" + numeroCPF + ", numeroDDD=" + numeroDDD + ", numeroTelefone="
                + numeroTelefone + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigoAutenticacao == null) ? 0 : codigoAutenticacao.hashCode());
        result = prime * result + ((codigoSistema == null) ? 0 : codigoSistema.hashCode());
        result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
        result = prime * result + ((emailAutenticacao == null) ? 0 : emailAutenticacao.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((numeroCPF == null) ? 0 : numeroCPF.hashCode());
        result = prime * result + ((numeroDDD == null) ? 0 : numeroDDD.hashCode());
        result = prime * result + ((numeroTelefone == null) ? 0 : numeroTelefone.hashCode());
        result = prime * result + ((sobrenome == null) ? 0 : sobrenome.hashCode());
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
        Usuario other = (Usuario) obj;
        if (codigoAutenticacao == null) {
            if (other.codigoAutenticacao != null)
                return false;
        } else if (!codigoAutenticacao.equals(other.codigoAutenticacao))
            return false;
        if (codigoSistema == null) {
            if (other.codigoSistema != null)
                return false;
        } else if (!codigoSistema.equals(other.codigoSistema))
            return false;
        if (dataNascimento == null) {
            if (other.dataNascimento != null)
                return false;
        } else if (!dataNascimento.equals(other.dataNascimento))
            return false;
        if (emailAutenticacao == null) {
            if (other.emailAutenticacao != null)
                return false;
        } else if (!emailAutenticacao.equals(other.emailAutenticacao))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (numeroCPF == null) {
            if (other.numeroCPF != null)
                return false;
        } else if (!numeroCPF.equals(other.numeroCPF))
            return false;
        if (numeroDDD == null) {
            if (other.numeroDDD != null)
                return false;
        } else if (!numeroDDD.equals(other.numeroDDD))
            return false;
        if (numeroTelefone == null) {
            if (other.numeroTelefone != null)
                return false;
        } else if (!numeroTelefone.equals(other.numeroTelefone))
            return false;
        if (sobrenome == null) {
            if (other.sobrenome != null)
                return false;
        } else if (!sobrenome.equals(other.sobrenome))
            return false;
        return true;
    }

}//fim da classe
