package br.com.bhl.superfid.model;

import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

import br.com.bhl.superfid.util.LibraryClass;

/**
 * Created by leonardoln on 12/07/2017.
 */

public class Usuario {

    private String codigo;
    private String nome;
    private String sobrenome;
    private long cpf;
    private int ddd;
    private int telefone;
    private Calendar dataNascimento;
    private String email;
    private String senha;

    public Usuario() {
    }

    public Usuario(String codigo, String nome, String sobrenome, long cpf, int ddd, int telefone, Calendar dataNascimento, String email, String senha) {
        this.codigo = codigo;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.ddd = ddd;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void salvarDatabase(DatabaseReference.CompletionListener... completionListener) {
        DatabaseReference firebase = LibraryClass.getFirebase().child("users").child(getCodigo());

        if (completionListener.length == 0) {
            firebase.setValue(this);
        } else {
            firebase.setValue(this, completionListener[0]);
        }
    }

    public static String tirarCaracteresEspeciais( String texto ) {
        texto = texto.replaceAll("[-./,;]", "");
        return texto;
    }

}//fim da classe
