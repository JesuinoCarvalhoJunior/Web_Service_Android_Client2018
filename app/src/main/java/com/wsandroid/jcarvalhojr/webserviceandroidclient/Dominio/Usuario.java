package com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Junior_Carvalho on 19/09/2015.
 */
public class Usuario implements Parcelable {

    private int id;
    private String nome;
    private int idade;
    private String login;
    private String senha;


    public Usuario(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        idade = in.readInt();
        login = in.readString();
        senha = in.readString();
    }

    public Usuario() {

    }

    public Usuario(int id, String nome, int idade) {
    }

    public Usuario(int id, String nome, int idade, String login, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.login = login;
        this.senha = senha;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    //sobrescrecer metodo toString
/*
    public String toString() {
        return "Usuario [id=" + id + ",nome=" + nome + ", idade=" + idade + "]";
    }

*/
    public String toString() {
        return nome;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeInt(idade);
        dest.writeString(login);
        dest.writeString(senha);
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}