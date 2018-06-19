package com.example.yexx.pokeagenda.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexx.pokeagenda.DAO.ConfiguracaoFirebase;
import com.example.yexx.pokeagenda.Model.Pokemon;
import com.example.yexx.pokeagenda.Model.Treinadores;
import com.example.yexx.pokeagenda.R;
import com.example.yexx.pokeagenda.Tools.CircleTransform;
import com.example.yexx.pokeagenda.Tools.Session;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class CadastrarPokemonActivity extends AppCompatActivity {

    private EditText nomePokemon, especiePokemon, pesoPokemon, alturaPokemon;
    private Button imagemGaleriaButton, imagemCameraButton,cadastrarPokemonButton;
    private ImageView imagemCarregada;
    private Bitmap fotoPokemon;
    private Pokemon pokemon;
    private DatabaseReference firebase;
    private Uri filePath;
    private String path;
    private Session session;
    private final int PICK_IMAGE_REQUEST = 71;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


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

        imagemGaleriaButton = (Button) findViewById(R.id.imagemGaleriaButton);
        imagemCameraButton = (Button) findViewById(R.id.imagemCameraButton);
        cadastrarPokemonButton = (Button) findViewById(R.id.cadastrarPokemonButton);



        //Init Pokemon
        pokemon = new Pokemon();

        imagemGaleriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregarImagemGaleria();
            }
        });

        imagemCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.CAMERA
                    }, MY_CAMERA_PERMISSION_CODE);
                } else {
                    carregarImagemCamera();
                }
            }
        });

        cadastrarPokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pesoTv = pesoPokemon.getText().toString();
                Double pesoValidacao = !pesoTv.equals("") ? Double.parseDouble(pesoTv) : -1.0;

                String alturaTv = alturaPokemon.getText().toString();
                Double alturaValidacao = !alturaTv.equals("") ? Double.parseDouble(alturaTv) : -1.0;

                if(nomePokemon.getText().toString().equals("")) {
                    Toast.makeText(CadastrarPokemonActivity.this, "Favor inserir um nome", Toast.LENGTH_SHORT).show();
                    return;
                } else if (especiePokemon.getText().toString().equals("")) {
                    Toast.makeText(CadastrarPokemonActivity.this, "Favor inserir uma especie", Toast.LENGTH_SHORT).show();
                    return;
                } else if ( pesoValidacao <= 0 || pesoValidacao > 999.99 ) {
                    Toast.makeText(CadastrarPokemonActivity.this, "Favor inserir um peso", Toast.LENGTH_SHORT).show();
                    return;
                } else if ( alturaValidacao <= 0 || alturaValidacao > 20.00){
                    Toast.makeText(CadastrarPokemonActivity.this, "Favor inserir uma altura", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    enviarImagemSalvarPoke();
                }
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

    private void carregarImagemGaleria(){
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Imagem Selecionada"), PICK_IMAGE_REQUEST);
    }

    private void carregarImagemCamera(){
        Intent it =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        filePath = Uri.fromFile(getOutputMediaFile());
        it.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
        startActivityForResult(it, 0);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), "CameraDemo");

        // Se a versão do APK for maior que 24, voce ignora o erro que deveria dar
        // A versão 23 pede pelo protcolo contains:// mas a gente quer usar o file://
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
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

            try {
                fotoPokemon = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            } catch (IOException e) {
                Toast.makeText(CadastrarPokemonActivity.this, "Ocorreu um erro:" + e, Toast.LENGTH_SHORT).show();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            fotoPokemon.compress(Bitmap.CompressFormat.PNG, 60, baos);
            InputStream fotoFinal = new ByteArrayInputStream(baos.toByteArray());

            path = "images/" + UUID.randomUUID().toString();

            StorageReference ref = storageReference.child(path);
            ref.putStream(fotoFinal)
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
                        progressDialog.setMessage("Enviando foto ...");
                    }
                });
        } else{
            Toast.makeText(this, "Nenhum foto selecionada.", Toast.LENGTH_SHORT).show();
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

        if(resultCode == RESULT_OK) {
            if(requestCode == PICK_IMAGE_REQUEST) {
                filePath = data.getData();
            }
            Picasso.with(this).load(filePath)
                    .fit()
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(imagemCarregada);
        }

    }

}




