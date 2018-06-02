package com.example.yexx.pokeagenda.Controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yexx.pokeagenda.R;

public class PesquisarPokemonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_pokemon);

        /* Pega a ação de clicar no menu id pesquisar_menu e traz para a tela PesquisarPokemonActivity */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
