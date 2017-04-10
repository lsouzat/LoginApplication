package br.com.app.loginapplication.activitys.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marcos on 09/04/2017.
 */

public class Usuario implements Parcelable {

    private String nome,email,senha;
    private int id;

    public Usuario(int id, String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.id = id;
    }

    public Usuario(){

    }

    protected Usuario(Parcel in) {
        nome = in.readString();
        email = in.readString();
        senha = in.readString();
        id = in.readInt();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeInt(id);
    }
}