package com.example.votacoes_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votacoes_app.adapter.AdapterItemPauta;
import com.example.votacoes_app.fragments.AddDialogFragment;
import com.example.votacoes_app.model.ItemPauta;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReuniaoPauta extends AppCompatActivity {

    private Button btHome, btAddPauta;
    private RecyclerView recyclerView;
    private AdapterItemPauta adapter;
    private ArrayList<ItemPauta> itemsPauta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuniao_pauta);

        btHome = findViewById(R.id.btHome);
        btAddPauta = findViewById(R.id.btAddPauta);

        recyclerView = findViewById(R.id.rcItemPauta);
        itemsPauta = new ArrayList<>();
        adapter = new AdapterItemPauta(this, itemsPauta);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReuniaoPauta.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        String reuniao_id = getIntent().getStringExtra("Reuniao");

        FirebaseFirestore.getInstance()
                        .collection("item")
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if(error != null) {
                                            Log.i("Items: ", error.getMessage());
                                            return;
                                        }

                                        for (DocumentChange dc : value.getDocumentChanges()) {
                                            if(dc.getType() == DocumentChange.Type.ADDED){
                                                ItemPauta itemPauta = dc.getDocument().toObject(ItemPauta.class);
                                                if(itemPauta.getIdReuniao().equalsIgnoreCase(reuniao_id)){
                                                    itemPauta.setId(dc.getDocument().getId());
                                                    itemsPauta.add(itemPauta);
                                                }
                                            }
                                            adapter.notifyDataSetChanged();
                                        }

                                    }
                                });

        btHome.setOnClickListener( v -> {
            Intent i = new Intent(ReuniaoPauta.this, TelaInicial.class);
            startActivity(i);
        });

        if(Login.usuarioLogado.getTipo() == 2){
            btAddPauta.setVisibility(View.GONE);
        }

        btAddPauta.setOnClickListener(v -> {

            String idReuniao = getIntent().getStringExtra("Reuniao");

            Bundle bundle = new Bundle();
            bundle.putString("idReuniao", idReuniao);
            AddDialogFragment addDialogFragment = new AddDialogFragment();
            addDialogFragment.setArguments(bundle);
            addDialogFragment.show(getSupportFragmentManager(), addDialogFragment.getTag());

        });
    }
}
