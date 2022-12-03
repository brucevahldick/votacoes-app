package com.example.votacoes_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.example.votacoes_app.adapter.AdapterReunioes;
import com.example.votacoes_app.model.Reuniao;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class IndexReunioes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_reunioes);

        Button btCadReuniao = findViewById(R.id.btCadReuniao);
        Button btVoltar = findViewById(R.id.btVoltarIndexReuniao);

        RecyclerView recyclerView = findViewById(R.id.rvReunioes);
        ArrayList<Reuniao> reunioes = new ArrayList<Reuniao>();
        AdapterReunioes adapter = new AdapterReunioes(this, reunioes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(IndexReunioes.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore.getInstance()
                .collection("reuniao")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.i("Erro", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                Reuniao reuniao = dc.getDocument().toObject(Reuniao.class);
                                reuniao.setId(dc.getDocument().getId());
                                reunioes.add(reuniao);

                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


        btCadReuniao.setOnClickListener(v ->{
            Intent i = new Intent(IndexReunioes.this, CadastroReunioes.class);
            startActivity(i);
        });

        btVoltar.setOnClickListener(v ->{
            Intent i = new Intent(IndexReunioes.this, RedirectCards.class);
            startActivity(i);
        });
    }
}