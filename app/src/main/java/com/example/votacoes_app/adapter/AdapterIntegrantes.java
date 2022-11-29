package com.example.votacoes_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votacoes_app.R;
import com.example.votacoes_app.model.Integrante;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterIntegrantes extends RecyclerView.Adapter<ViewHolderIntegrante> {

    private Context context;
    private ArrayList<Integrante> integrantes;

    public AdapterIntegrantes(Context context, ArrayList<Integrante> integrantes) {
        this.context = context;
        this.integrantes = integrantes;
    }

    @NonNull
    @Override
    public ViewHolderIntegrante onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_integrante, parent,
                false);

        ViewHolderIntegrante viewHolderIntegrante = new ViewHolderIntegrante(view);

        return viewHolderIntegrante;
    }

    @Override
    public int getItemCount() {
        return integrantes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIntegrante holder, int position) {
        Integrante integrante = integrantes.get(position);
        holder.nome.setText(integrante.getNome());
        holder.conselho.setText(integrante.getConselho());
        Picasso.get()
                .load(integrante.getImgageId())
                .fit()
                .centerCrop()
                .into(holder.imgIntegrante);
    }

}
