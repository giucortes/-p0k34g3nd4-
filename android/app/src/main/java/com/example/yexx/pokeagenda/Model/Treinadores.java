package com.example.yexx.pokeagenda.Model;


public class Treinadores {

    private String idTreinador;
    private String nomeTreinador;
    private String email;
    private String senha;


    public Treinadores() {
;
    }

    public String getIdTreinador() {
        return idTreinador;

    }

    public void setIdTreinador(String idTreinador) {
        this.idTreinador = idTreinador;
    }

    public String getNomeTreinador() {
        return nomeTreinador;
    }

    public void setNomeTreinador(String nomeTreinador) {
        this.nomeTreinador = nomeTreinador;
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
}
