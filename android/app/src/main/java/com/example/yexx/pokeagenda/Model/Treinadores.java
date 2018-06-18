package com.example.yexx.pokeagenda.Model;


public class Treinadores {

    private String nomeTreinador;
    private String email;
    private String senha;
    private String pokemonFavorito;


    public Treinadores() {
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

    public String getPokemonFavorito() {
        return pokemonFavorito;
    }

    public void setPokemonFavorito(String pokemonFavorito) {
        this.pokemonFavorito = pokemonFavorito;
    }
}
