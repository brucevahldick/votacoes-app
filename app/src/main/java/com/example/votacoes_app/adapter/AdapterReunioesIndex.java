package com.example.votacoes_app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.votacoes_app.CadastroReunioes;
import com.example.votacoes_app.R;
import com.example.votacoes_app.RedirectCards;
import com.example.votacoes_app.TelaInicial;
import com.example.votacoes_app.model.Reuniao;
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

import java.util.ArrayList;

public class AdapterReunioesIndex extends BaseAdapterReunioes{
    public AdapterReunioesIndex(Context context, ArrayList<Reuniao> reunioes) {
        super(context, reunioes);
    }

    @Override
    public void updateButtonEvent(Reuniao reuniao) {
        Intent intent = new Intent(context, CadastroReunioes.class);
        intent.putExtra("Reuniao", reuniao);
        context.startActivity(intent);
    }

    @Override
    public void removeButtonEvent(Reuniao reuniao, int position){
        final Dialog modal = new Dialog(context);

        modal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        modal.setContentView(R.layout.modal_remove_reuniao);
        modal.setCancelable(true);

        TextView data      = modal.findViewById(R.id.tvReuniaoDataModal);
        TextView conselho  = modal.findViewById(R.id.tvReuniaoConselhoModal);

        Button btCancelar  = modal.findViewById(R.id.btCancelarModalReuniao);
        Button btConfirmar = modal.findViewById(R.id.btConfirmarModalReuniao);

        data.setText(reuniao.getData());
        conselho.setText(reuniao.getConselho());

        btCancelar.setOnClickListener(v -> {
            modal.hide();
        });

        btConfirmar.setOnClickListener(v -> {
            confirmRemove(reuniao, position);
            modal.hide();
        });

        modal.show();
    }


    public void confirmRemove(Reuniao reuniao, int position) {
        FirebaseFirestore.getInstance().collection("reuniao").document(reuniao.getId())
                .delete();

        reunioes.remove(position);
        notifyItemRemoved(position);
    }
}
