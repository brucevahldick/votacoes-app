package com.example.votacoes_app.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.votacoes_app.R;
import com.example.votacoes_app.adapter.LoadingDialog;
import com.example.votacoes_app.model.ItemPauta;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

@SuppressWarnings("ALL")
public class AddDialogFragment extends DialogFragment {

    private Button btAdicionar;
    private EditText processo, taxaAprovacao;
    //private String idReuniao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_create_item, container);

        btAdicionar = view.findViewById(R.id.btAddItem);
        processo    = view.findViewById(R.id.edProcessoItem);
        taxaAprovacao = view.findViewById(R.id.edQtdVotosItem);

        btAdicionar.setOnClickListener(v -> {
            String processoNome = processo.getText().toString();
            int taxa         = Integer.parseInt(taxaAprovacao.getText().toString());

            Bundle bundle = this.getArguments();
            String idReuniao = bundle.getString("idReuniao");

            ItemPauta itemPauta = new ItemPauta(processoNome, taxa, idReuniao);

            FirebaseFirestore.getInstance().collection("item")
                    .add(itemPauta)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("ItemPauta: ", e.getMessage().toString());
                        }
                    });
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
