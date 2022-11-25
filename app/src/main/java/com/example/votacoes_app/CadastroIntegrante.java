package com.example.votacoes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.votacoes_app.model.Integrante;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

public class CadastroIntegrante extends AppCompatActivity {

    private Integrante integrante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_integrante);

        ImageButton imgIntegrante   = findViewById(R.id.imgCadIntegrante);

        EditText edCpf              =   findViewById(R.id.edCpf);
        EditText edNome             =   findViewById(R.id.edCadNome);
        EditText edConselho         =   findViewById(R.id.edCadConselho);
        EditText edContato          =   findViewById(R.id.edCadContato);
        EditText edSenha            =   findViewById(R.id.edCadSenha);

        Button btSalvar             =   findViewById(R.id.btSalvarIntegrante);
        Button btVoltar             =   findViewById(R.id.btVoltarIntegrante);

        btSalvar.setOnClickListener(v -> {

            String cpf      =   edCpf.getText().toString();
            String nome     =   edNome.getText().toString();
            String conselho =   edConselho.getText().toString();
            String contato  =   edContato.getText().toString();
            String senha    =   edSenha.getText().toString();

            if( cpf.isEmpty() || nome.isEmpty() || conselho.isEmpty()
                    || contato.isEmpty() || senha.isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Preencha todos os campos!",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            integrante = new Integrante(cpf, nome, conselho, contato, senha);

            cadastrarIntegrante(integrante);
        });

        btVoltar.setOnClickListener(v -> {
            Intent i = new Intent(CadastroIntegrante.this, IndexIntegrantes.class);
            startActivity(i);
        });

        imgIntegrante.setOnClickListener(v -> {
            //@todo alterar p/ utilizar a camera
            selecionarFoto();
        });

    }

    private void selecionarFoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(takePictureIntent);
    }

    private void cadastrarIntegrante(Integrante i) {

        FirebaseFirestore.getInstance()
                .collection("integrantes")
                .add(i)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Integrante cadastrado com sucesso!",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        Intent i = new Intent(CadastroIntegrante.this, IndexIntegrantes.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Erro ao cadastrar integrante!",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

}