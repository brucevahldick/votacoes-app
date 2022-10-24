package com.example.votacoes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        Button btGerenciar = findViewById(R.id.btGerenciar);

        btGerenciar.setOnClickListener(v -> {
            Intent i = new Intent(TelaInicial.this, RedirectCards.class);
            startActivity(i);
        });
    }
}