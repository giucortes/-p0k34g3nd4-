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

    private static SharedPreferences prefs;
    private static Gson gson;
    final static String TRAINER = "trainer";

    public Session(Context cntx) {
        if (prefs == null)
            prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
        if (gson == null)
            gson = new Gson();
    }

    public boolean isLogged(){
        String user = prefs.getString(TRAINER,"");
        return user != null;
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

    public boolean logout() {
        return prefs.edit().putString(TRAINER, null).commit();
    }

}
