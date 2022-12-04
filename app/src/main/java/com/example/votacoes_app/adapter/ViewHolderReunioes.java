package com.example.votacoes_app.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votacoes_app.MainActivity;
import com.example.votacoes_app.R;
import com.example.votacoes_app.RedirectCards;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewHolderReunioes extends RecyclerView.ViewHolder{

    public TextView conselho, data, hora;
    public FloatingActionButton remove, update;
    public LinearLayout optLayout;

    public ViewHolderReunioes(@NonNull View itemView) {
        super(itemView);

        conselho  = itemView.findViewById(R.id.tvNomeConselho);
        data      = itemView.findViewById(R.id.tvNomeData);
        hora      = itemView.findViewById(R.id.tvNomeHora);
        remove    = itemView.findViewById(R.id.fabRemove);
        update    = itemView.findViewById(R.id.fabUpdate);
        optLayout = itemView.findViewById(R.id.optLayout);
    }
}
