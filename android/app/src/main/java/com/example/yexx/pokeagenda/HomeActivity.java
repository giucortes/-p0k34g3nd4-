package com.example.yexx.pokeagenda;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menuzim, menu);
//        return true;
//    }

    public void popularListaPokemon(Integer[] imagem, String[] nomePoke, String[] nomeTreina){
        ListCell adapter = new ListCell(HomeActivity.this, imagem, nomePoke, nomeTreina);
        list = (ListView)findViewById(R.id.listaPokemon);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(HomeActivity.this, "clicou", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

