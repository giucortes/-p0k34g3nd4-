package com.example.yexx.pokeagenda.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexx.pokeagenda.DAO.ConfiguracaoFirebase;
import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.Model.Treinadores;
import com.example.yexx.pokeagenda.R;
import com.example.yexx.pokeagenda.Tools.CircleTransform;
import com.example.yexx.pokeagenda.Tools.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.nio.LongBuffer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class DetalheActivity extends AppCompatActivity {

    private Treinadores treinador;
    private TextView txtEspeciePokemon;
    private TextView txtNomePokemon;
    private TextView txtNomeTreinador;
    private TextView txtPesoPokemon;
    private TextView txtAlturaPokemon;
    private ImageView imagemDetalhe;
    private FloatingActionButton favoritarButton;
    private FirebaseAuth autenticacao;
    private DatabaseReference firebase;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        //Set treinador
        session = new Session(DetalheActivity.this.getApplicationContext());
        treinador = session.getUser();

        // Init View
        favoritarButton = (FloatingActionButton)findViewById(R.id.favoritarButton);
        txtNomePokemon = (TextView) findViewById(R.id.txtNomePokemon);
        txtEspeciePokemon = (TextView) findViewById(R.id.txtEspeciePokemon);
        txtNomeTreinador = (TextView) findViewById(R.id.txtNomeTreinador);
        txtPesoPokemon = (TextView) findViewById(R.id.txtPesoPokemon);
        txtAlturaPokemon = (TextView) findViewById(R.id.txtAlturaPokemon);
        imagemDetalhe = (ImageView) findViewById(R.id.imagemDetalhe);

        //GET INTENT DO ADAPTER

        Intent i = this.getIntent();

        //RECEBE OS DADOS

        String fotoP = i.getExtras().getString("FOTO_KEY");
        final String nomeP = i.getExtras().getString("NOME_KEY");
        String especieP = i.getExtras().getString("ESPECIE_KEY");
        String alturaP = i.getExtras().getString("ALTURA_KEY");
        String pesoP = i.getExtras().getString("PESO_KEY");
        String treinadorP = i.getExtras().getString("TREINADOR_KEY");

        //JUNTAR OS DADOS

        Picasso.with(this).load(fotoP).placeholder(R.mipmap.ic_launcher).fit().centerCrop().transform(new CircleTransform()).into(imagemDetalhe);
        txtNomePokemon.setText(nomeP);
        txtEspeciePokemon.setText(especieP);
        txtAlturaPokemon.setText(alturaP);
        txtPesoPokemon.setText(pesoP);
        txtNomeTreinador.setText(treinadorP);

        String treinadorPokemonFavorito = treinador.getPokemonFavorito();
        comparaPokemon(nomeP,treinadorPokemonFavorito);

        favoritarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                treinador = session.getUser();
                String treinadorPokemonFavorito = treinador.getPokemonFavorito();

                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                FirebaseUser treinadorLogado = autenticacao.getCurrentUser();
                String treinadorId = treinadorLogado.getUid();

                firebase = ConfiguracaoFirebase.getFirebase().child("trainers");

                if(!comparaPokemon(nomeP,treinadorPokemonFavorito)) {
                    firebase.child(treinadorId).child("pokemonFavorito").setValue(nomeP);
                    favoritarButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
                    treinador.setPokemonFavorito(nomeP);
                    Snackbar.make(view, "Pokémon escolhido como favorito", Snackbar.LENGTH_SHORT)
                            .setAction("Favoritado", null).show();
                } else {
                    firebase.child(treinadorId).child("pokemonFavorito").setValue("");
                    favoritarButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
                    treinador.setPokemonFavorito("");
                    Snackbar.make(view, "Pokémon favorito removido", Snackbar.LENGTH_SHORT)
                            .setAction("Desfavoritado", null).show();
                }

                session.setUser(treinador);

            }
        });

    }

    //Se pokemon do banco é igual ao pokemon favoritado entao marca a estrelinha amarela
    public boolean comparaPokemon(String pokemonAtual, String pokemonFavorito){

        if (pokemonAtual.equals(pokemonFavorito)) {
            favoritarButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
            return true;
        }
        return false;
    }

}
