package com.example.votacoes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votacoes_app.model.Integrante;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Login extends AppCompatActivity {

    public static Integrante usuarioLogado;

    Button btLogin;
    EditText edEmail, edSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin                 = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(v -> {
             edEmail            =   findViewById(R.id.edEmail);
             edSenha            =   findViewById(R.id.edSenha);

            String email        =   edEmail.getText().toString();
            String senha        =   edSenha.getText().toString();

            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, senha)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseFirestore.getInstance()
                                    .collection("integrantes")
                                    .whereEqualTo("userId", authResult.getUser().getUid())
                                    .limit(1)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<Integrante> usuario = queryDocumentSnapshots.toObjects(Integrante.class);
                                            Integrante integrante = usuario.get(0);
                                            usuarioLogado = integrante;

                                            Intent intent = new Intent(Login.this, TelaInicial.class);
                                            startActivity(intent);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Login: ", e.getMessage().toString());
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Dados inválidos! Verifque e-mail ou senha",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
        });
    }
}