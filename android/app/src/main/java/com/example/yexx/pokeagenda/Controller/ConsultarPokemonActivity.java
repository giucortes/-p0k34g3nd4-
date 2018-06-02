package com.example.yexx.pokeagenda.Controller;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.yexx.pokeagenda.Model.ListCell;
import com.example.yexx.pokeagenda.R;

import androidx.appcompat.app.AppCompatActivity;


public class ConsultarPokemonActivity extends AppCompatActivity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pokemon);

        /* Pega a ação de clicar no menu id consultar_menu e traz para a tela ConsultarPokemonActivity */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Integer[] imagens = {0};
        String[] nomes = {"nome"};
        String[] treinas = {"treina"};
        this.popularListaPokemon(imagens, nomes, treinas);
    }

    public void popularListaPokemon(Integer[] imagem, String[] nomePoke, String[] nomeTreina){
        ListCell adapter = new ListCell(ConsultarPokemonActivity.this, imagem, nomePoke, nomeTreina);
        list = (ListView)findViewById(R.id.listaPokemon);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ConsultarPokemonActivity.this, "clicou", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
