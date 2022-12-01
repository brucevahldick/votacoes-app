package com.example.votacoes_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.votacoes_app.model.Integrante;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class CadastroIntegrante extends AppCompatActivity {

    private Integrante integrante;
    private final int CAMERA_REQUEST_CODE = 2;
    private ImageView imgIntegrante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_integrante);

        imgIntegrante   = findViewById(R.id.imgCadIntegrante);

        EditText edCpf              =   findViewById(R.id.edCadCpf);
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
            } else {
                integrante = new Integrante(cpf, nome, conselho, contato, senha);
                salvarImagem();
                cadastrarIntegrante(integrante);

            }


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
        startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE){

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgIntegrante.setImageBitmap(imageBitmap);

        }

    }

    protected void salvarImagem() {
        String fileName = UUID.randomUUID().toString();
        StorageReference storageRef = FirebaseStorage.getInstance().
                getReference("/images/" + fileName);

        integrante.setImgageId(fileName);

        imgIntegrante.setDrawingCacheEnabled(true);
        imgIntegrante.buildDrawingCache();

        Bitmap bitmap = ((BitmapDrawable) imgIntegrante.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Falha ao enviar imagem",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i("Imagem:", taskSnapshot.getMetadata().toString());
            }
        });
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