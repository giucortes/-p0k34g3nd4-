package com.example.yexx.pokeagenda.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yexx.pokeagenda.DAO.ConfiguracaoFirebase;
import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.Model.Treinadores;
import com.example.yexx.pokeagenda.R;
import com.example.yexx.pokeagenda.Tools.Session;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class CadastrarPokemonActivity extends AppCompatActivity {

    private EditText nomePokemon, especiePokemon, pesoPokemon, alturaPokemon;
    private Button imagemPokemonButton, cadastrarPokemonButton;
    private ImageView imagemCarregada;
    private Pokemon pokemon;
    private DatabaseReference firebase;
    private Uri filePath;
    private String path;
    private Session session;
    private final int PICK_IMAGE_REQUEST = 71;


    //Firebase variável
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pokemon);

        /* Pega a ação de clicar no menu id cadastrar_menu e traz para a tela CadastrarPokemonActivity */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* TODO: TEM QUE PASSAR O NOME DO TREINADOR QUE CADASTROU O POKEMON. TEM QUE INCLUIR A VALIDAÇÃO DOS CAMPOS E ZERAR O FORMULÁRIO QUANDO TERMINA DE CADASTRAR */

        // Init Firebase

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        path = null;

        // Init View
        nomePokemon = (EditText) findViewById(R.id.nomePokemon);
        especiePokemon = (EditText) findViewById(R.id.especiePokemon);
        pesoPokemon = (EditText) findViewById(R.id.pesoPokemon);
        alturaPokemon = (EditText) findViewById(R.id.alturaPokemon);

        imagemCarregada = (ImageView) findViewById(R.id.imagemCarregada);

        imagemPokemonButton = (Button) findViewById(R.id.imagemPokemonButton);
        cadastrarPokemonButton = (Button) findViewById(R.id.cadastrarPokemonButton);

        //Init Pokemon
        pokemon = new Pokemon();

        imagemPokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregarImagem();
            }
        });

        cadastrarPokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enviarImagemSalvarPoke();
            }
        });

    }

    private boolean salvarPokemon (Pokemon pokemon){
        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("pokemons");
            firebase.child(pokemon.getNome()).setValue(pokemon);

            Toast.makeText(CadastrarPokemonActivity.this, "Pokemon inserido com sucesso!", Toast.LENGTH_SHORT).show();

            return true;


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    private void carregarImagem(){
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Imagem Selecionada"), PICK_IMAGE_REQUEST);
    }

    private String getCaminhoArquivo (Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime =  MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    private void enviarImagemSalvarPoke(){
        if (filePath != null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Enviando...");
            progressDialog.show();

            path = "images/" + UUID.randomUUID().toString();

            StorageReference ref = storageReference.child(path);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(CadastrarPokemonActivity.this, "Imagem enviada", Toast.LENGTH_SHORT).show();
                            salvarPoke(taskSnapshot.getDownloadUrl().toString());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(CadastrarPokemonActivity.this, "Falhou em carregar imagem." +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Enviando "+(int)progress+"%");
                }
            });

        }else{
            Toast.makeText(this, "Nenhum arquivo selecionado.", Toast.LENGTH_SHORT).show();
        }

    }

    private void salvarPoke(String imgPath){
        pokemon.setNome(nomePokemon.getText().toString());
        pokemon.setEspecie(especiePokemon.getText().toString());
        pokemon.setPeso(Double.valueOf(pesoPokemon.getText().toString()));
        pokemon.setAltura(Double.valueOf(alturaPokemon.getText().toString()));

        session = new Session(CadastrarPokemonActivity.this.getApplicationContext());
        Treinadores trainer = session.getUser();
        pokemon.setTreinadorCadastrou(trainer);

        pokemon.setFotoUrl(imgPath);
        salvarPokemon(pokemon);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {
            filePath = data.getData();
            Picasso.with(this).load(filePath).into(imagemCarregada);

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imagemCarregada.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}




