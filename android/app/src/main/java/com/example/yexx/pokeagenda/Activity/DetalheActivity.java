package com.example.yexx.pokeagenda.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yexx.pokeagenda.R;

import androidx.appcompat.app.AppCompatActivity;


public class DetalheActivity extends AppCompatActivity {
    private TextView txtEspeciePokemon;
    private TextView txtNomePokemon;
    private TextView txtNomeTreinador;
    private TextView txtPesoPokemon;
    private TextView txtAlturaPokemon;
    private ImageView imagemDetalhe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        // Init View
        txtNomePokemon = (TextView) findViewById(R.id.txtNomePokemon);
        txtEspeciePokemon = (TextView) findViewById(R.id.txtEspeciePokemon);
        txtNomeTreinador = (TextView) findViewById(R.id.txtNomeTreinador);
        txtPesoPokemon = (TextView) findViewById(R.id.txtPesoPokemon);
        txtAlturaPokemon = (TextView) findViewById(R.id.txtAlturaPokemon);

        imagemDetalhe = (ImageView) findViewById(R.id.imagemDetalhe);


    }
}
