package com.example.votacoes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class IndexReunioes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_reunioes);

        Button btCadReuniao = findViewById(R.id.btCadReuniao);

        btCadReuniao.setOnClickListener(v ->{
            Intent i = new Intent(IndexReunioes.this, CadastroReunioes.class);
            startActivity(i);
        });
    }
}