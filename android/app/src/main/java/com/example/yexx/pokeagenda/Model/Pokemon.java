package com.example.yexx.pokeagenda.Model;

import android.net.Uri;

/**
 * Created by gazip on 30/05/2018.
 */

public class Pokemon {
    private String nome;
    private String especie;
    private Uri foto;
    private double peso;
    private double altura;
    private String treinadorCadastrou;

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

    public Uri getFoto() {
        return foto;
    }

    public void setFoto(Uri foto) {
        this.foto = foto;
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

    public String getTreinadorCadastrou() {
        return treinadorCadastrou;
    }

    public void setTreinadorCadastrou(String treinadorCadastrou) {
        this.treinadorCadastrou = treinadorCadastrou;
    }
}
