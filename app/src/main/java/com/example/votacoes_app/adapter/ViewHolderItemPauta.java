package com.example.votacoes_app.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votacoes_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewHolderItemPauta extends RecyclerView.ViewHolder {

    public TextView processo, votos, status, taxa;
    public FloatingActionButton favoravel, contrario;

    public ViewHolderItemPauta(@NonNull View itemView){
        super(itemView);

        processo    = itemView.findViewById(R.id.tvProcesso);
        votos       = itemView.findViewById(R.id.tvVotos);
        taxa        = itemView.findViewById(R.id.tvTaxa);
        status      = itemView.findViewById(R.id.tvStatus);

        favoravel   = itemView.findViewById(R.id.btFavoravel);
        contrario   = itemView.findViewById(R.id.btContrario);
    }
}
