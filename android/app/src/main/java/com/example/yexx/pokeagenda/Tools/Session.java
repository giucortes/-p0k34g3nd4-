package com.example.yexx.pokeagenda.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.yexx.pokeagenda.DAO.ConfiguracaoFirebase;
import com.example.yexx.pokeagenda.Model.Treinadores;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class Session {

    private SharedPreferences prefs;

    final String TRAINER = "trainer";
    Gson gson;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
        gson = new Gson();
    }

    public void setUser(Treinadores trainer) {
        String strTrainer = gson.toJson(trainer);
        prefs.edit().putString(TRAINER, strTrainer).commit();
    }

    public Treinadores getUser() {
        String json = prefs.getString(TRAINER,"");
        Treinadores trainer = gson.fromJson(json, Treinadores.class);
        return trainer;
    }
}
