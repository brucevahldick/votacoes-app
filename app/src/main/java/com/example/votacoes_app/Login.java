package com.example.votacoes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btLogin = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(v -> {
            EditText cpfFild = (EditText) findViewById(R.id.inputCpf);
            EditText senhaFild = (EditText) findViewById(R.id.inputSenha);

            String txtCpf = cpfFild.getText().toString();
            String txtSenha = senhaFild.getText().toString();

            //TO-DO: aplicar verificação de login e cpf quando o cadastro de integrantes estiver pronto
            if(txtCpf.equals("123") && txtSenha.equals("123")) {
                Intent i = new Intent(Login.this, TelaInicial.class);
                startActivity(i);
            }else{
                Toast.makeText(getApplicationContext(),"CPF ou senha incorretos",Toast.LENGTH_SHORT).show();
            }
        });
    }
}