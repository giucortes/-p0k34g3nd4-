package com.example.yexx.pokeagenda;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class ConsultarPokemon extends AppCompatActivity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pokemon);

        /* Pega a ação de clicar no menu id consultar_menu e traz para a tela ConsultarPokemon */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Integer[] imagens = {0};
        String[] nomes = {"nome"};
        String[] treinas = {"treina"};
        this.popularListaPokemon(imagens, nomes, treinas);
    }

    public void popularListaPokemon(Integer[] imagem, String[] nomePoke, String[] nomeTreina){
        ListCell adapter = new ListCell(ConsultarPokemon.this, imagem, nomePoke, nomeTreina);
        list = (ListView)findViewById(R.id.listaPokemon);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ConsultarPokemon.this, "clicou", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
