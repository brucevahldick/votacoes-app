package com.example.votacoes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RedirectCards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect_cards);

        Button btVoltar = findViewById(R.id.btCardVoltar);
        Button btReunioes = findViewById(R.id.btGerenciarReunioes);
        Button btIntegrantes = findViewById(R.id.btGerenciarIntegrante);

        btIntegrantes.setOnClickListener(v -> {
            Intent i = new Intent(RedirectCards.this, IndexIntegrantes.class);
            startActivity(i);
        });


        btReunioes.setOnClickListener(v -> {
            Intent i = new Intent(RedirectCards.this, IndexReunioes.class);
            startActivity(i);
        });

        btVoltar.setOnClickListener(v -> {
            Intent i = new Intent(RedirectCards.this, TelaInicial.class);
            startActivity(i);
        });
    }
}