package com.example.yexx.pokeagenda.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexx.pokeagenda.DAO.ConfiguracaoFirebase;
import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.Model.Treinadores;
import com.example.yexx.pokeagenda.R;
import com.example.yexx.pokeagenda.Tools.CircleTransform;
import com.example.yexx.pokeagenda.Tools.Session;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class WelcomeActivity extends AppCompatActivity {

    private Session session;
    private Treinadores treinador;
    private String treinadorNome;
    private String treinadorPokemonFavorito;
    private ImageView imgPokemonFavorito;
    private TextView txtNomeTreinador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txtNomeTreinador = (TextView) findViewById(R.id.nomeTreinador);
        imgPokemonFavorito = (ImageView) findViewById(R.id.imagemPokemonFavoritoView);

        session = new Session(WelcomeActivity.this.getApplicationContext());


    }

    //Impede de usar o botao de voltar do android para sair do welcome
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Para sair, selecione a opção Sair no menu", Toast.LENGTH_SHORT).show();
    }


    //roda na pagina cada vez que volta pra view. Faz parte do ciclo de vida da activity. Entao ele seta a foto e o nome do treinador cada vez que volta pra pagina
    @Override
    protected void onResume(){
        super.onResume();
        setFotoPokemonFavorito();
    }


    //chama o menu xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //faz um switch case pra cada opçao de menu chamando a acivity respectiva
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case R.id.consultar_menu:
                 Intent intent1 = new Intent(this,ConsultarPokemonActivity.class);
                 this.startActivity(intent1);
                 return true;
             case R.id.cadastrar_menu:
                 Intent intent2 = new Intent(this, CadastrarPokemonActivity.class);
                 this.startActivity(intent2);
                 return true;
             case R.id.logout:
                 Intent intent3 = new Intent(this, LoginActivity.class);
                 if (session.logout()) {
                     Toast.makeText(WelcomeActivity.this.getApplicationContext(), "Logout feito com sucesso", Toast.LENGTH_SHORT);
                     this.startActivity(intent3);
                     return true;
                 }
                 return false;
             default:
                 return super.onOptionsItemSelected(item);
         }
    }


    public void setFotoPokemonFavorito(){
        // treinador armazena o usuario
        treinador = session.getUser();
        //treinadorNome armazena o nome do treinador
        treinadorNome = treinador.getNomeTreinador();
        //armazena a foto do poke favorito
        treinadorPokemonFavorito = treinador.getPokemonFavorito();

        //se o usuario for diferente de null entao seta o nome do treinador
        if(session.getUser() != null) {
            txtNomeTreinador.setText(treinadorNome);
        }

        //se o pokemon favorito for diferente de null
        if(treinador.getPokemonFavorito() != null) {
            //se o pokemon favorito for diferente de vazio
            if (!treinador.getPokemonFavorito().equals("")) {


                //armazena o pokemon favorito na chave pokemons no objeto pokemon
                DatabaseReference pokemon = ConfiguracaoFirebase.getFirebase().child("pokemons").child(treinadorPokemonFavorito);
                pokemon.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //pega os valores da classe pokemon
                        Pokemon pokemonFavorito = dataSnapshot.getValue(Pokemon.class);
                        //Usa o picasso para tratar a imagem e url do pokemon favorito
                        Picasso.with(WelcomeActivity.this.getApplicationContext())
                                .load(pokemonFavorito.getFotoUrl())
                                .placeholder(R.drawable.pokeicon)
                                .fit()
                                .centerInside()
                                .transform(new CircleTransform())
                                .into(imgPokemonFavorito);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(WelcomeActivity.this, "Erro: " + databaseError, Toast.LENGTH_SHORT).show();
                    }
                });
            //Se o pokemon favorito nao existir entao joga a imagem da pokebolinha
            } else {
                Picasso.with(WelcomeActivity.this.getApplicationContext())
                        .load(R.drawable.pokeicon)
                        .into(imgPokemonFavorito);
            }
        }

    }

}
