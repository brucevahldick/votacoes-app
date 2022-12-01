package com.example.votacoes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btEntrar = findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, IndexReunioes.class);
            startActivity(i);
        });
    }
}