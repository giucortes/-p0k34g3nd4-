package com.example.yexx.pokeagenda.Controller;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yexx.pokeagenda.DAO.ConfiguracaoFirebase;
import com.example.yexx.pokeagenda.Model.Treinadores;
import com.example.yexx.pokeagenda.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailLogin;
    private EditText edtSenhaLogin;
    private Button loginButton;
    private FirebaseAuth autenticacao;
    private Treinadores treinadores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logar(View view){
        /* Joguei pra welcome só pra testar
        Intent it = new Intent(this, WelcomeActivity.class);
        startActivity(it);
        */
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

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(treinadores.getEmail(), treinadores.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void abrirTelaPrincipal(){
        Intent intentAbrirTelaPrincipal = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intentAbrirTelaPrincipal);
    }
}
