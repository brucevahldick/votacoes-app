package com.example.votacoes_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votacoes_app.CadastroReunioes;
import com.example.votacoes_app.MainActivity;
import com.example.votacoes_app.R;
import com.example.votacoes_app.RedirectCards;
import com.example.votacoes_app.model.Integrante;
import com.example.votacoes_app.model.Reuniao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterReunioes extends RecyclerView.Adapter<ViewHolderReunioes>{

    private Context context;
    private ArrayList<Reuniao> reunioes;

    public AdapterReunioes(Context context, ArrayList<Reuniao> reunioes) {
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
            Intent intent = new Intent(context, CadastroReunioes.class);
            intent.putExtra("Reuniao", reuniao);
            context.startActivity(intent);
        });

        holder.remove.setOnClickListener(v -> {
            Intent intent = new Intent(context, CadastroReunioes.class);
            intent.putExtra("Reuniao", reuniao);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return reunioes.size();
    }
}














