package com.example.votacoes_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.votacoes_app.adapter.LoadingDialog;
import com.example.votacoes_app.model.Integrante;
import com.example.votacoes_app.model.Reuniao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@SuppressWarnings("ALL")
public class CadastroIntegrante extends AppCompatActivity {

    private Integrante integrante;
    private final int CAMERA_REQUEST_CODE = 2;
    private ImageView imgIntegrante;
    private int tipo = 2;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_integrante);

        imgIntegrante   = findViewById(R.id.imgCadIntegrante);

        EditText edCpf              =   findViewById(R.id.edCpf);
        EditText edNome             =   findViewById(R.id.edCadNome);
        EditText edConselho         =   findViewById(R.id.edCadConselho);
        EditText edEmail            =   findViewById(R.id.edCadEmail);
        EditText edContato          =   findViewById(R.id.edCadContato);
        EditText edSenha            =   findViewById(R.id.edCadSenha);

        Button btSalvar             =   findViewById(R.id.btSalvarIntegrante);
        Button btVoltar             =   findViewById(R.id.btVoltarIntegrante);

        Switch swTipo               =   findViewById(R.id.swSecretario);

        loadingDialog               =   new LoadingDialog(CadastroIntegrante.this);

        Integrante inteUpdate = integrante = (Integrante) getIntent().getSerializableExtra("Integrante");
        boolean isUpdate = false;

        if(inteUpdate != null){
            this.integrante = inteUpdate;

            isUpdate = true;

            Picasso.get().load(integrante.getImgageId())
                            .fit()
                            .centerCrop()
                            .into(imgIntegrante);

            edCpf.setText(integrante.getCpf());
            edNome.setText(integrante.getNome());
            edConselho.setText(integrante.getConselho());
            edEmail.setText(integrante.getEmail());
            edContato.setText(integrante.getContato());
            edSenha.setText(integrante.getSenha());

            if(integrante.getTipo() == 1) {
                swTipo.setChecked(true);
            }

            btSalvar.setText("Alterar");
        }

        boolean finalIsUpdate = isUpdate;

        btSalvar.setOnClickListener(v -> {

            String cpf      =   edCpf.getText().toString();
            String nome     =   edNome.getText().toString();
            String conselho =   edConselho.getText().toString();
            String email    =   edEmail.getText().toString();
            String contato  =   edContato.getText().toString();
            String senha    =   edSenha.getText().toString();

            if( cpf.isEmpty() || nome.isEmpty() || conselho.isEmpty() ||
                    email.isEmpty() || contato.isEmpty() || senha.isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Preencha todos os campos!",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else if(finalIsUpdate) {

                String emailAntigo = integrante.getEmail();
                String senhaAntiga = integrante.getSenha();

                integrante.setCpf(cpf);
                integrante.setNome(nome);
                integrante.setEmail(email);
                integrante.setConselho(conselho);
                integrante.setContato(contato);
                integrante.setSenha(senha);

                loadingDialog.startLoadingAnimation();

                alterarImagem(integrante, emailAntigo, senhaAntiga);

            } else {

                integrante = new Integrante(cpf, nome, conselho, email, contato, tipo, senha);
                loadingDialog.startLoadingAnimation();
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    integrante.setUserId(task.getResult().getUser().getUid());
                                } else {
                                    Log.i("Firebase Auth: ", task.getException().toString());
                                }
                            }
                        });

                criaUsuario(integrante);
            }

        });

        swTipo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    tipo = 1;
                }

            }
        });

        btVoltar.setOnClickListener(v -> {
            Intent i = new Intent(CadastroIntegrante.this, IndexIntegrantes.class);
            startActivity(i);
        });

        imgIntegrante.setOnClickListener(v -> {
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

    protected void criaUsuario(Integrante integrante) {
        String fileName = UUID.randomUUID().toString();
        StorageReference storageRef = FirebaseStorage.getInstance().
                getReference("/images/" + fileName);

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
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("Imagem URL:", uri.toString());
                        integrante.setImgageId(uri.toString());
                        salvarUsuario(integrante);
                    }
                });
            }
        });
    }

    private void salvarUsuario(Integrante i) {

        FirebaseFirestore.getInstance()
                .collection("integrantes")
                .add(i)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        loadingDialog.dismissDialog();
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

    private void alterarImagem(Integrante integrante, String email, String senha) {

        StorageReference imageRefence = FirebaseStorage.getInstance().getReferenceFromUrl(integrante.getImgageId());
        imageRefence.delete();

        String fileName = UUID.randomUUID().toString();
        StorageReference storageRef = FirebaseStorage.getInstance().
                getReference("/images/" + fileName);

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
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("Imagem URL:", uri.toString());
                        integrante.setImgageId(uri.toString());
                        alterarDadosIntegrante(integrante, email, senha);
                    }
                });
            }
        });
    }

    private void alterarDadosIntegrante(Integrante i, String email, String senha){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(i.getEmail());
                        user.updatePassword(i.getSenha());

                        FirebaseFirestore.getInstance()
                                .collection("integrantes")
                                .document(i.getId())
                                .set(i)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        loadingDialog.dismissDialog();
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "Integrante alterado com sucesso!",
                                                Toast.LENGTH_SHORT);
                                        toast.show();
                                        Intent i = new Intent(CadastroIntegrante.this, IndexIntegrantes.class);
                                        startActivity(i);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "Falha ao alterar!",
                                                Toast.LENGTH_SHORT);
                                        toast.show();
                                        Log.i("Integrante: ", e.getMessage().toString());
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Integrante", e.getMessage().toString());
                    }
                });
    }
}