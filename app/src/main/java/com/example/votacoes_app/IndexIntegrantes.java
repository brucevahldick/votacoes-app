package com.example.votacoes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class IndexIntegrantes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_integrantes);

        Button btCadIntegrante = findViewById(R.id.btCadIntegrante);

        btCadIntegrante.setOnClickListener(v ->{
            Intent i = new Intent(IndexIntegrantes.this, CadastroIntegrante.class);
            startActivity(i);
        });
    }
}