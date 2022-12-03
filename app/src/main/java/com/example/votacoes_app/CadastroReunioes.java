package com.example.votacoes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.votacoes_app.model.Reuniao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import org.json.JSONObject;

import java.util.HashMap;

public class CadastroReunioes extends AppCompatActivity {

    private Reuniao reuniao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_reunioes);

        Spinner spQuorum             = findViewById(R.id.spQuorum);
        String[] items               = new String[]{"Maioria simples", "Maioria qualificada 3/5", "Maioria qualificada 2/3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spQuorum.setAdapter(adapter);

        EditText edLocalReu       = findViewById(R.id.edCadLocal);
        EditText edData           = findViewById(R.id.edCadData);
        EditText edHora           = findViewById(R.id.edCadHora);
        EditText edCadConselhoReu = findViewById(R.id.edCadConselhoReu);
        EditText edCadSec         = findViewById(R.id.edCadSec);

        Button btSalvar = findViewById(R.id.btSalvarReunioes);
        Button btVoltar = findViewById(R.id.btVoltarCadReuniao);

        Reuniao reuUpdade = reuniao = (Reuniao) getIntent().getSerializableExtra("Reuniao");
        boolean isUpdate = false;
        if(reuUpdade !=null){
            this.reuniao = reuUpdade;

            isUpdate = true;

            edLocalReu.setText(reuniao.getLocal());
            edData.setText(reuniao.getData());
            edHora.setText(reuniao.getHora());
            edCadConselhoReu.setText(reuniao.getConselho());
            edCadSec.setText(reuniao.getSecretario());

            btSalvar.setText("Alterar");
        }

        boolean finalIsUpdate = isUpdate;
        btSalvar.setOnClickListener(v -> {

            String local      = edLocalReu.getText().toString();
            String data       = edData.getText().toString();
            String hora       = edHora.getText().toString();
            String quorum     = spQuorum.getSelectedItem().toString();
            String conselho   = edCadConselhoReu.getText().toString();
            String secretario = edCadSec.getText().toString();

            if (local.isEmpty() || data.isEmpty() || hora.isEmpty()
                    || quorum.isEmpty() || conselho.isEmpty() || secretario.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Preencha todos os campos!",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else {

                if(finalIsUpdate) {
                    reuniao.setConselho(conselho);
                    reuniao.setData(data);
                    reuniao.setHora(hora);
                    reuniao.setQuorum(quorum);
                    reuniao.setSecretario(secretario);
                    reuniao.setLocal(local);

                    alterarReuniao(reuniao);
                }else {
                    reuniao = new Reuniao(conselho, data, hora, local, quorum, secretario);
                    cadastrarReuniao(reuniao);
                }
            }
        });

        btVoltar.setOnClickListener(v -> voltarIndex());
    }

    private void cadastrarReuniao(Reuniao r) {
        FirebaseFirestore.getInstance()
                .collection("reuniao")
                .add(r)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Reunião cadastrada com sucesso!",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        voltarIndex();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Erro ao cadastrar reunião!",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

    private void alterarReuniao(Reuniao r) {
        EditText edCadSec = findViewById(R.id.edCadSec);
        edCadSec.setText(reuniao.getId());

        FirebaseFirestore.getInstance()
                .collection("reuniao")
                .document(r.getId())
                .set(r);
    }

    private void voltarIndex(){
        Intent i = new Intent(CadastroReunioes.this, IndexReunioes.class);
        startActivity(i);
    }
}