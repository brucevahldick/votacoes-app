package com.example.votacoes_app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votacoes_app.CadastroIntegrante;
import com.example.votacoes_app.IndexIntegrantes;
import com.example.votacoes_app.R;
import com.example.votacoes_app.model.Integrante;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterIntegrantes extends RecyclerView.Adapter<ViewHolderIntegrante> {

    private Context context;
    private ArrayList<Integrante> integrantes;

    public AdapterIntegrantes(Context context, ArrayList<Integrante> integrantes) {
        this.context = context;
        this.integrantes = integrantes;
    }

    @NonNull
    @Override
    public ViewHolderIntegrante onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_integrante, parent,
                false);

        ViewHolderIntegrante viewHolderIntegrante = new ViewHolderIntegrante(view);

        return viewHolderIntegrante;
    }

    @Override
    public int getItemCount() {
        return integrantes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIntegrante holder, int position) {
        Integrante integrante = integrantes.get(position);
        holder.nome.setText(integrante.getNome());
        holder.conselho.setText(integrante.getConselho());
        Picasso.get()
                .load(integrante.getImgageId())
                .resize(60,60)
                .centerCrop()
                .into(holder.imgIntegrante);

        holder.update.setOnClickListener( v -> {
            Intent intent = new Intent(context, CadastroIntegrante.class);
            intent.putExtra("Integrante", integrante);
            context.startActivity(intent);
        });

        holder.remove.setOnClickListener( v -> {
            showRemoveModal( integrante, position);
        });


    }

    public void showRemoveModal(Integrante integrante, int position){
        final Dialog modal = new Dialog(context);

        modal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        modal.setContentView(R.layout.modal_remove_integrante);
        modal.setCancelable(true);

        TextView nome      = modal.findViewById(R.id.tvIntegranteNomeModal);
        TextView conselho  = modal.findViewById(R.id.tvIntegranteConselhoModal);

        Button btCancelar  = modal.findViewById(R.id.btCancelarModalIntegrante);
        Button btConfirmar = modal.findViewById(R.id.btConfirmarModalIntegrante);

        nome.setText(integrante.getNome());
        conselho.setText(integrante.getConselho());

        btCancelar.setOnClickListener(v -> {
            modal.hide();
        });

        btConfirmar.setOnClickListener(v -> {
            removeIntegrante(integrante, position);
            modal.hide();
        });

        modal.show();
    }


    public void removeIntegrante(Integrante integrante, int position){

        FirebaseFirestore.getInstance().collection("integrantes").document(integrante.getId())
                .delete();

        StorageReference imageRefence = FirebaseStorage.getInstance().getReferenceFromUrl(integrante.getImgageId());

        imageRefence.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("Imagem: ", "Deu boa");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Imagem: ", e.getMessage().toString());
            }
        });

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(integrante.getEmail(), integrante.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete();
                    }
                });

        integrantes.remove(position);
        notifyItemRemoved(position);
    }
}
