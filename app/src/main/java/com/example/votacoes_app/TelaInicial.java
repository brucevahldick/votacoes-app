package com.example.votacoes_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.votacoes_app.adapter.AdapterReunioesIndex;
import com.example.votacoes_app.adapter.AdapterReunioesTelaInicial;
import com.example.votacoes_app.adapter.BaseAdapterReunioes;
import com.example.votacoes_app.model.Reuniao;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        RecyclerView recyclerView   = findViewById(R.id.rvTelaInicial);
        ArrayList<Reuniao> reunioes = new ArrayList<Reuniao>();
        BaseAdapterReunioes adapter     = new AdapterReunioesTelaInicial(this, reunioes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TelaInicial.this,
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
                                if(reuniao.getConselho().equalsIgnoreCase(Login.usuarioLogado.getConselho())) {
                                    reuniao.setId(dc.getDocument().getId());
                                    reunioes.add(reuniao);
                                }

                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


       Button btGerenciar = findViewById(R.id.btGerenciar);

       if(Login.usuarioLogado.getTipo() == 2){
           btGerenciar.setVisibility(View.INVISIBLE);
       } else {
           btGerenciar.setOnClickListener(v -> {
               Intent i = new Intent(TelaInicial.this, RedirectCards.class);
               startActivity(i);
           });
       }


    }
}