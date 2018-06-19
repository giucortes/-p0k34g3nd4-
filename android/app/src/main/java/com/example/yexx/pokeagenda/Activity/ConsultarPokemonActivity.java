package com.example.yexx.pokeagenda.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

    EditText pesquisarPokemon;
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

        pesquisarPokemon = (EditText) findViewById(R.id.pesquisarEdt);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        progressCircle = findViewById(R.id.progress_circular);

        pokemon = new ArrayList<>();
        firebase = ConfiguracaoFirebase.getFirebase().child("pokemons");

        pesquisarPokemon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //adapter que faz a pesquisa
                setAdapterPesquisar(editable.toString());
            }
        });

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

    //puxa todos os pokemons do banco, conecta.
    private void setAdapterPesquisar(final String searchedString) {
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Limpar lista para cada pesquisa nova
                 * */
                pokemon.clear();
                //remove os cartaozinho
                recyclerView.removeAllViews();
                //reseta o counter
                int counter = 0;

                /*
                 * Procura todos os pokemons que batam com o valor inserido na busca
                 * */
                //pra cada chave da lista ele pega os filhotinho
                for (DataSnapshot dadosfirebase : dataSnapshot.getChildren()) {

                    Pokemon pokemonItem = dadosfirebase.getValue(Pokemon.class);

                    if(!searchedString.isEmpty()){

                        //Se o nome do pokemon encontrado for igual ao que ta digitado ele adiciona no arraylist
                        if (pokemonItem.getNome().toLowerCase().contains(searchedString.toLowerCase())) {
                            pokemon.add(pokemonItem);
                            counter++;
                        } else if (pokemonItem.getNome().toLowerCase().contains(searchedString.toLowerCase())) {
                            pokemon.add(pokemonItem);
                            counter++;
                        }

                    }else{
                        pokemon.add(pokemonItem);
                    }

                    /*
                     * Pega no maximo 15 resultados
                     * */
                    if (counter == 15)
                        break;
                    }

                    //instancia pra todos os elementos do arraylist
                pokeAdapter = new PokemonAdapter(ConsultarPokemonActivity.this, pokemon);
                recyclerView.setAdapter(pokeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
