package com.example.yexx.pokeagenda.Activity;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yexx.pokeagenda.DAO.ConfiguracaoFirebase;
import com.example.yexx.pokeagenda.Model.Treinadores;
import com.example.yexx.pokeagenda.R;
import com.example.yexx.pokeagenda.Tools.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailLogin;
    private EditText edtSenhaLogin;
    private Button loginButton;
    private FirebaseAuth autenticacao;
    private Treinadores treinadores;
    private ProgressDialog progressDialog;
    private Session session;//global variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Cria a sessao
        session = new Session(LoginActivity.this.getApplicationContext());
        if(session.getUser() != null){
            treinadores = session.getUser();
            abrirTelaPrincipal();
        }

        progressDialog = new ProgressDialog(this);

        edtEmailLogin = (EditText) findViewById(R.id.emailLogin);
        edtSenhaLogin = (EditText) findViewById(R.id.senhaLogin);

        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtEmailLogin.getText().toString().equals("") && !edtSenhaLogin.getText().equals("")){

                    treinadores = new Treinadores();
                    treinadores.setEmail(edtEmailLogin.getText().toString());
                    treinadores.setSenha(edtSenhaLogin.getText().toString());

                    validarLogin();

                }else{
                    Toast.makeText(LoginActivity.this,"Preencha os campos de e-mail e senha.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void validarLogin(){

        if(TextUtils.isEmpty(treinadores.getEmail())){
            //verifica se email é vazio
            Toast.makeText(this, "Por favor, insira um e-mail", Toast.LENGTH_SHORT).show();
            //para a função de executar
            return;

        }

        if(TextUtils.isEmpty(treinadores.getSenha())){
            //verifica se senha é vazia
            Toast.makeText(this, "Por favor, insira uma senha", Toast.LENGTH_SHORT).show();
            //para a função de executar
            return;

        }
        
        //se validação ok
        //vamos primeiro mostrar uma progressbar pro progressdialog
        progressDialog.setMessage("Registrando treinador...");
        progressDialog.show();

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(treinadores.getEmail().trim(), treinadores.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser treinadorLogado = autenticacao.getCurrentUser();
                    String treinadorId = treinadorLogado.getUid();

                    DatabaseReference trainers = ConfiguracaoFirebase.getFirebase().child("trainers").child(treinadorId);

                    trainers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot){
                            Treinadores treinador = dataSnapshot.getValue(Treinadores.class);
                            session.setUser(treinador);

                            abrirTelaPrincipal();
                            Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, "Erro: "+databaseError, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Ocorreu um erro. Tente Novamente!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void abrirTelaPrincipal(){
        Intent intentAbrirTelaPrincipal = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intentAbrirTelaPrincipal);
    }
}