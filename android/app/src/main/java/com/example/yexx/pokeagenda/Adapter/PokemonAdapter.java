package com.example.yexx.pokeagenda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.R;
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

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_item, parent, false);



        return new PokemonViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {

            Pokemon pokemon2 = pokemon.get(position);
            holder.txtnomePokemonLista.setText(pokemon2.getNome());
            Picasso.with(context).load(pokemon2.getFotoUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.imagemLista);

        if (pokemon2.getTreinadorCadastrou() != null) {
            holder.txtnomeTreinadorLista.setText(pokemon2.getTreinadorCadastrou().getEmail());    // TODO colocar nome
        }

    }

    @Override
    public int getItemCount() {
        return pokemon.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{

        public TextView txtnomePokemonLista;
        public TextView txtnomeTreinadorLista;
        public ImageView imagemLista;



        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            txtnomePokemonLista = itemView.findViewById(R.id.nomePokemonLista);
            txtnomeTreinadorLista = itemView.findViewById(R.id.nomeTreinadorLista);
            imagemLista = itemView.findViewById(R.id.imagemPokemonLista);
        }
    }
}
