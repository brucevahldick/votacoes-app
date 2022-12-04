package com.example.votacoes_app.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votacoes_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewHolderIntegrante extends RecyclerView.ViewHolder {

    public TextView nome, conselho;
    public ImageView imgIntegrante;
    public FloatingActionButton remove, update;

    public ViewHolderIntegrante(@NonNull View itemView) {
        super(itemView);
        imgIntegrante = itemView.findViewById(R.id.imgAvatarIntegrante);
        nome = itemView.findViewById(R.id.tvNomeIntegrante);
        conselho = itemView.findViewById(R.id.tvConselhoIntegrante);
        remove = itemView.findViewById(R.id.fabRemoveIntegrante);
        update = itemView.findViewById(R.id.fabUpdateIntegrante);
    }
}
