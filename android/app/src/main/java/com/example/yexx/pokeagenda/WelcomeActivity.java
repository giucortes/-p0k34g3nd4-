package com.example.yexx.pokeagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case R.id.consultar_menu:
                 Intent intent1 = new Intent(this,ConsultarPokemon.class);
                 this.startActivity(intent1);
                 return true;
             case R.id.cadastrar_menu:
                 Intent intent2 = new Intent(this, CadastrarPokemon.class);
                 this.startActivity(intent2);
                 return true;
             case R.id.pesquisar_menu:
                 Intent intent3 = new Intent(this,PesquisarPokemon.class);
                 this.startActivity(intent3);
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
    }


}
