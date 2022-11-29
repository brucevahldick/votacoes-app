package com.example.votacoes_app.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votacoes_app.R;

public class ViewHolderIntegrante extends RecyclerView.ViewHolder {

    public TextView nome, conselho;
    public ImageView imgIntegrante;

    public ViewHolderIntegrante(@NonNull View itemView) {
        super(itemView);
        imgIntegrante = itemView.findViewById(R.id.imgAvatarIntegrante);
        nome = itemView.findViewById(R.id.tvNomeIntegrante);
        conselho = itemView.findViewById(R.id.tvConselhoIntegrante);
    }
}
