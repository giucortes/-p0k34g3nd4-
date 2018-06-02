package com.example.yexx.pokeagenda;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class PesquisarPokemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_pokemon);

        /* Pega a ação de clicar no menu id pesquisar_menu e traz para a tela PesquisarPokemon */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
