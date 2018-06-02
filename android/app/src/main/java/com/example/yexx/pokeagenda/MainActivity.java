package com.example.yexx.pokeagenda;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logar(View view){
        /* Joguei pra welcome só pra testar */
        Intent it = new Intent(this, WelcomeActivity.class);
        startActivity(it);
    }


}
