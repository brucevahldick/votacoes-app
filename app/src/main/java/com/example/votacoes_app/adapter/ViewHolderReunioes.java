package com.example.votacoes_app.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.votacoes_app.R;

public class ViewHolderReunioes extends RecyclerView.ViewHolder{

    public TextView conselho, data, hora, opcoes;

    public ViewHolderReunioes(@NonNull View itemView) {
        super(itemView);

        conselho = itemView.findViewById(R.id.tvNomeConselho);
        data = itemView.findViewById(R.id.tvNomeData);
        hora = itemView.findViewById(R.id.tvNomeHora);
        opcoes = itemView.findViewById(R.id.tvNomeOpcoes);

    }
}
