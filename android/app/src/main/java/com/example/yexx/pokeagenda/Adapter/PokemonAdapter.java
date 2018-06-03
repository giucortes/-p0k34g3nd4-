package com.example.yexx.pokeagenda.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import androidx.annotation.NonNull;


public class PokemonAdapter extends ArrayAdapter<Pokemon> {

    private ArrayList<Pokemon> pokemon;
    private Context context;
    private ImageView imagemPokemonLista;

    private FirebaseStorage storage;
    private StorageReference storageRef;

    public PokemonAdapter(Context c, ArrayList<Pokemon> objects) {
        super(c,0, objects);
        this.context = c;
        this.pokemon = objects;
        // Init Firebase
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if(pokemon != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_cell, parent, false);

            imagemPokemonLista = (ImageView) view.findViewById(R.id.imagemPokemonLista);
            TextView nomePokemonLista = (TextView) view.findViewById(R.id.nomePokemonLista);
            TextView  nomeTreinadorLista = (TextView) view.findViewById(R.id.nomeTreinadorLista);

            Pokemon pokemon2 = pokemon.get(position);
            nomePokemonLista.setText(pokemon2.getNome());
            if (pokemon2.getTreinadorCadastrou() != null) {
                nomeTreinadorLista.setText(pokemon2.getTreinadorCadastrou().getEmail());    // TODO colocar nome
            }

            // pegar URL de download
            Log.d("PIC", pokemon2.getFoto());
            storageRef.child( pokemon2.getFoto() ).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imagemPokemonLista.setImageURI( uri );
                    Log.d("URI", uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    exception.printStackTrace();
                }
            });

        }
        return view;
    }
}
