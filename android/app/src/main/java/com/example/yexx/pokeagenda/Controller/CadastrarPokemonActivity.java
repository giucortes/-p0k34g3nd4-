package com.example.yexx.pokeagenda.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.yexx.pokeagenda.R;

public class CadastrarPokemonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pokemon);

        /* Pega a ação de clicar no menu id cadastrar_menu e traz para a tela CadastrarPokemonActivity */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void cadastrarPokemon(View view){
        /* Joguei pra welcome só pra testar */
        Intent it = new Intent(this, WelcomeActivity.class);
        startActivity(it);
    }
}
