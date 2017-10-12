package br.com.bhl.superfid.model;

import java.util.Calendar;

public class Usuario {

    private long codigoSistema;
    private String codigoFirebase;
    private String nome;
    private String sobrenome;
    private Calendar dataNascimento;
    private String emailFirebase;
    private long numeroCPF;
    private int ddd;
    private int telefone;

    public Usuario() {
    }

    public Usuario(long codigoSistema, String codigoFirebase, String nome, String sobrenome, Calendar dataNascimento, String emailFirebase, long numeroCPF, int ddd, int telefone) {
        this.codigoSistema = codigoSistema;
        this.codigoFirebase = codigoFirebase;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.emailFirebase = emailFirebase;
        this.numeroCPF = numeroCPF;
        this.ddd = ddd;
        this.telefone = telefone;
    }

    public long getCodigoSistema() {
        return codigoSistema;
    }

    public void setCodigoSistema(long codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    public String getCodigoFirebase() {
        return codigoFirebase;
    }

    public void setCodigoFirebase(String codigoFirebase) {
        this.codigoFirebase = codigoFirebase;
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

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmailFirebase() {
        return emailFirebase;
    }

    public void setEmailFirebase(String emailFirebase) {
        this.emailFirebase = emailFirebase;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        if (getCodigoSistema() != usuario.getCodigoSistema()) return false;
        if (getNumeroCPF() != usuario.getNumeroCPF()) return false;
        if (getDdd() != usuario.getDdd()) return false;
        if (getTelefone() != usuario.getTelefone()) return false;
        if (getCodigoFirebase() != null ? !getCodigoFirebase().equals(usuario.getCodigoFirebase()) : usuario.getCodigoFirebase() != null)
            return false;
        if (getNome() != null ? !getNome().equals(usuario.getNome()) : usuario.getNome() != null)
            return false;
        if (getSobrenome() != null ? !getSobrenome().equals(usuario.getSobrenome()) : usuario.getSobrenome() != null)
            return false;
        if (getDataNascimento() != null ? !getDataNascimento().equals(usuario.getDataNascimento()) : usuario.getDataNascimento() != null)
            return false;
        return getEmailFirebase() != null ? getEmailFirebase().equals(usuario.getEmailFirebase()) : usuario.getEmailFirebase() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getCodigoSistema() ^ (getCodigoSistema() >>> 32));
        result = 31 * result + (getCodigoFirebase() != null ? getCodigoFirebase().hashCode() : 0);
        result = 31 * result + (getNome() != null ? getNome().hashCode() : 0);
        result = 31 * result + (getSobrenome() != null ? getSobrenome().hashCode() : 0);
        result = 31 * result + (getDataNascimento() != null ? getDataNascimento().hashCode() : 0);
        result = 31 * result + (getEmailFirebase() != null ? getEmailFirebase().hashCode() : 0);
        result = 31 * result + (int) (getNumeroCPF() ^ (getNumeroCPF() >>> 32));
        result = 31 * result + getDdd();
        result = 31 * result + getTelefone();
        return result;
    }

    public static String tirarCaracteresEspeciais( String texto ) {
        texto = texto.replaceAll("[-./,;]", "");
        return texto;
    }

}//fim da classe
