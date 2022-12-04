package com.example.votacoes_app.adapter;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.example.votacoes_app.model.Reuniao;
import java.util.ArrayList;

public class AdapterReunioesTelaInicial extends BaseAdapterReunioes{
    public AdapterReunioesTelaInicial(Context context, ArrayList<Reuniao> reunioes) {
        super(context, reunioes);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReunioes holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.remove.setVisibility(View.GONE);
    }

    @Override
    public void updateButtonEvent(Reuniao reuniao, ViewHolderReunioes holder) {

    }

    @Override
    public void removeButtonEvent(Reuniao reuniao,  ViewHolderReunioes holder){}
}
