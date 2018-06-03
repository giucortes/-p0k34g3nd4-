package com.example.yexx.pokeagenda.Activity;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.yexx.pokeagenda.Adapter.PokemonAdapter;
import com.example.yexx.pokeagenda.DAO.ConfiguracaoFirebase;
import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class ConsultarPokemonActivity extends AppCompatActivity {
    private  ListView listView;
    private ArrayAdapter<Pokemon> adapter;
    private ArrayList<Pokemon> pokemon;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pokemon);

        /* Pega a ação de clicar no menu id consultar_menu e traz para a tela ConsultarPokemonActivity */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pokemon = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listaPokemon);
        adapter = new PokemonAdapter(this, pokemon);

        listView.setAdapter(adapter);

        firebase = ConfiguracaoFirebase.getFirebase().child("pokemons");

        valueEventListenerPokemon = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pokemon.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Pokemon novoPokemon = dados.getValue(Pokemon.class);

                    pokemon.add(novoPokemon);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        /*
        Integer[] imagens = {0};
        String[] nomes = {"nome"};
        String[] treinas = {"treina"};
        this.popularListaPokemon(imagens, nomes, treinas);
        */
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerPokemon);
    }

    @Override
    protected void onStart() {
        firebase.addValueEventListener(valueEventListenerPokemon);
        super.onStart();
    }

    /* public void popularListaPokemon(Integer[] imagem, String[] nomePoke, String[] nomeTreina){
        ListCell adapter = new ListCell(ConsultarPokemonActivity.this, imagem, nomePoke, nomeTreina);
        listView = (ListView)findViewById(R.id.listaPokemon);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ConsultarPokemonActivity.this, "clicou", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


}
