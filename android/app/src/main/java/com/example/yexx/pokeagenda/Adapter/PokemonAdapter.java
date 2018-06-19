package com.example.yexx.pokeagenda.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yexx.pokeagenda.Activity.DetalheActivity;
import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.Model.Treinadores;
import com.example.yexx.pokeagenda.R;
import com.example.yexx.pokeagenda.Tools.CircleTransform;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {


    private ArrayList<Pokemon> pokemon;
    private Context context;
    private ImageView imagemPokemonLista;

    private FirebaseStorage storage;
    private StorageReference storageRef;

    public PokemonAdapter(Context c, ArrayList<Pokemon> pokemons) {
        context = c;
        pokemon = pokemons;
        // Init Firebase
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    //cria a lista, o cartaozinho da lista
    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_item, parent, false);
        return new PokemonViewHolder(view);
    }

    //liga os eventos. Quando chama o cartaozinho passa os valores que ele vai carregar
    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {

        //pega a posiçao do objeto pokemon, o nome e a imagem
        final Pokemon pokemon2 = pokemon.get(position);
        holder.txtnomePokemonLista.setText(pokemon2.getNome());
        Picasso.with(context).load(pokemon2.getFotoUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().transform(new CircleTransform()).into(holder.imagemLista);
        holder.txtespeciePokemonLista.setText(pokemon2.getEspecie());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ABRE DETALHES PASSANDO OS PARAMETROS
                openDetailActivity(pokemon2.getFotoUrl(), pokemon2.getNome(), pokemon2.getEspecie(), pokemon2.getAltura(), pokemon2.getPeso(), pokemon2.getTreinadorCadastrou());
            }
        });

    }

    //retorna quantos elementos tem na lista
    @Override
    public int getItemCount() {
        return pokemon.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{

        public TextView txtnomePokemonLista;
        public TextView txtespeciePokemonLista;
        public ImageView imagemLista;

        public Object getItem(int pos) {
            return pokemon.get(pos);
        }

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            txtnomePokemonLista = itemView.findViewById(R.id.nomePokemonLista);
            txtespeciePokemonLista = itemView.findViewById(R.id.especiePokemonLista);
            imagemLista = itemView.findViewById(R.id.imagemPokemonLista);
        }

    }

    //Itens para a lista de detalhes

    //OPEN DETAIL ACTIVITY
    private void openDetailActivity(String fotopokemon, String nomepokemon, String especiepokemon, Double alturapokemon, Double pesopokemon, Treinadores treinador) {
        Intent i=new Intent(context,DetalheActivity.class);
        i.putExtra("FOTO_KEY",fotopokemon);
        i.putExtra("NOME_KEY",nomepokemon);
        i.putExtra("ESPECIE_KEY",especiepokemon);
        i.putExtra("ALTURA_KEY",alturapokemon.toString());
        i.putExtra("PESO_KEY",pesopokemon.toString());
        i.putExtra("TREINADOR_KEY",treinador.getNomeTreinador());

        context.startActivity(i);
    }


}

