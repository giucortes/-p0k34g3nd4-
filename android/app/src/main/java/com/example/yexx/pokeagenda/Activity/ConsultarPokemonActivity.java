package com.example.yexx.pokeagenda.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yexx.pokeagenda.Adapter.PokemonAdapter;
import com.example.yexx.pokeagenda.DAO.ConfiguracaoFirebase;
import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConsultarPokemonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter pokeAdapter;
    private ArrayList<Pokemon> pokemon;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerPokemon;
    private ProgressBar progressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        /* Pega a ação de clicar no menu id consultar_menu e traz para a tela ConsultarPokemonActivity */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressCircle = findViewById(R.id.progress_circular);

        pokemon = new ArrayList<>();
        firebase = ConfiguracaoFirebase.getFirebase().child("pokemons");

        valueEventListenerPokemon = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pokemon.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Pokemon novoPokemon = dados.getValue(Pokemon.class);

                    pokemon.add(novoPokemon);
                }

                pokeAdapter = new PokemonAdapter(ConsultarPokemonActivity.this, pokemon);
                recyclerView.setAdapter(pokeAdapter);
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ConsultarPokemonActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressCircle.setVisibility(View.INVISIBLE);
            }
        };


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
}
