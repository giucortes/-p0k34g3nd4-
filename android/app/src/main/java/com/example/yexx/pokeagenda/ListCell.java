package com.example.yexx.pokeagenda;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gazip on 30/05/2018.
 */

public class ListCell extends ArrayAdapter<String>{
    private final Activity context;
    private final Integer[] imagem;
    private final String[] nomePokemon;
    private final String[] nomeTreinador;

    public ListCell(Activity context, Integer[] imagem, String[] nomePokemon, String[] nomeTreinador){
        super(context, R.layout.list_cell, nomePokemon);
        this.context = context;
        this.imagem = imagem;
        this.nomePokemon = nomePokemon;
        this.nomeTreinador = nomeTreinador;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_cell, null, true);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.imagemPoke);
        TextView txtNomePoke = (TextView) rowView.findViewById(R.id.nomePoke);
        TextView txtNomeTreina = (TextView)rowView.findViewById(R.id.nomeTreina);
        imageView.setImageResource(imagem[position]);
        txtNomePoke.setText(nomePokemon[position]);
        txtNomeTreina.setText(nomeTreinador[position]);
        return rowView;
    }
}


















