package com.example.yexx.pokeagenda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.R;

import java.util.ArrayList;


public class PokemonAdapter extends ArrayAdapter<Pokemon> {

    private ArrayList<Pokemon> pokemon;
    private Context context;

    public PokemonAdapter(Context c, ArrayList<Pokemon> objects) {
        super(c,0, objects);
        this.context = c;
        this.pokemon = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if(pokemon != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_cell, parent, false);

            ImageView imagemPokemonLista = (ImageView) view.findViewById(R.id.imagemPokemonLista);
            TextView nomePokemonLista = (TextView) view.findViewById(R.id.nomePokemonLista);
            TextView  nomeTreinadorLista = (TextView) view.findViewById(R.id.nomeTreinadorLista);

            Pokemon pokemon2 = pokemon.get(position);
            nomePokemonLista.setText(pokemon2.getNome());
            nomeTreinadorLista.setText(pokemon2.getTreinadorCadastrou());
            imagemPokemonLista.setImageURI(pokemon2.getFoto());
        }
        return view;
    }
}
