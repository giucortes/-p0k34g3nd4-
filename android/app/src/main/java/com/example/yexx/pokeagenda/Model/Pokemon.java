package com.example.yexx.pokeagenda.Model;

import android.net.Uri;

import java.net.URI;

/**
 * Created by gazip on 30/05/2018.
 */

public class Pokemon {
    private String nome;
    private String especie;
    private String fotoUrl;
    private double peso;
    private double altura;
    private String idTreinador;
    private Treinadores treinadorCadastrou;

    public Pokemon() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String foto) {
        this.fotoUrl = foto;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public Treinadores getTreinadorCadastrou() {
        return treinadorCadastrou;
    }

    public void setTreinadorCadastrou(Treinadores treinadorCadastrou) {
        this.treinadorCadastrou = treinadorCadastrou;
    }
}
