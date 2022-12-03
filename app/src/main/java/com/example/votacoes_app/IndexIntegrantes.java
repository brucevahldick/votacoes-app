package com.example.votacoes_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.votacoes_app.adapter.AdapterIntegrantes;
import com.example.votacoes_app.model.Integrante;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class IndexIntegrantes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterIntegrantes adapter;
    private ArrayList<Integrante> integrantes;
    private ArrayList<Integrante> integranteFiltro;
    private Button btCadIntegrante, btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_integrantes);

        btCadIntegrante = findViewById(R.id.btCadIntegrante);
        btVoltar = findViewById(R.id.btIndexVoltar);

        recyclerView = findViewById(R.id.rvIntegrantes);
        integrantes = new ArrayList<Integrante>();
        adapter = new AdapterIntegrantes(this, integrantes);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(IndexIntegrantes.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore.getInstance()
                        .collection("integrantes")
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (error != null){
                                            Log.i("Erro", error.getMessage());
                                            return;
                                        }

                                        for (DocumentChange dc : value.getDocumentChanges()){
                                            if(dc.getType() == DocumentChange.Type.ADDED){
                                                Integrante integrante = dc.getDocument().toObject(Integrante.class);
                                                integrantes.add(integrante);

                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                });

        btCadIntegrante.setOnClickListener(v ->{
            Intent i = new Intent(IndexIntegrantes.this, CadastroIntegrante.class);
            startActivity(i);
        });

        btVoltar.setOnClickListener(v -> {
            Intent i = new Intent(IndexIntegrantes.this, RedirectCards.class);
            startActivity(i);
        });


    }

}