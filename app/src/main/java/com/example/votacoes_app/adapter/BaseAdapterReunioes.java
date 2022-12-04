package com.example.votacoes_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votacoes_app.CadastroReunioes;
import com.example.votacoes_app.R;
import com.example.votacoes_app.model.Reuniao;

import java.util.ArrayList;

public abstract class BaseAdapterReunioes extends RecyclerView.Adapter<ViewHolderReunioes>{

    protected Context context;
    protected ArrayList<Reuniao> reunioes;

    public BaseAdapterReunioes(Context context, ArrayList<Reuniao> reunioes) {
        this.context = context;
        this.reunioes = reunioes;
    }

    @NonNull
    @Override
    public ViewHolderReunioes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reuniao, parent, false);
        ViewHolderReunioes ViewHolderReunioes = new ViewHolderReunioes(view);
        return ViewHolderReunioes;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReunioes holder, int position) {
        Reuniao reuniao = reunioes.get(position);
        holder.conselho.setText(reuniao.getConselho());
        holder.data.setText(reuniao.getData());
        holder.hora.setText(reuniao.getHora());

        holder.update.setOnClickListener(v -> {
            updateButtonEvent(reuniao, holder);
        });

        holder.remove.setOnClickListener(v -> {
            removeButtonEvent(reuniao, holder);
        });
    }

    @Override
    public int getItemCount() {
        return reunioes.size();
    }

    public abstract void updateButtonEvent(Reuniao reuniao, ViewHolderReunioes holder);

    public abstract void removeButtonEvent(Reuniao reuniao, ViewHolderReunioes holder);
}














