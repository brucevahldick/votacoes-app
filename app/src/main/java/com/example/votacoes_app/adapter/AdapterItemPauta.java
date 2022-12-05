package com.example.votacoes_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.votacoes_app.Login;
import com.example.votacoes_app.R;
import com.example.votacoes_app.model.ItemPauta;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdapterItemPauta extends RecyclerView.Adapter<ViewHolderItemPauta> {

    private Context context;
    private ArrayList<ItemPauta> itemsPauta;

    public AdapterItemPauta(Context context, ArrayList<ItemPauta> itemsPauta) {
        this.context = context;
        this.itemsPauta = itemsPauta;
    }

    @NonNull
    @Override
    public ViewHolderItemPauta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_item_pauta, parent,
                false);

        ViewHolderItemPauta viewHolderItemPauta = new ViewHolderItemPauta(view);

        return viewHolderItemPauta;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItemPauta holder, int position) {
        ItemPauta itemPauta = itemsPauta.get(position);
        holder.processo.setText(itemPauta.getProcesso());
        holder.votos.setText(String.valueOf(itemPauta.getFavoravel()));
        holder.status.setText(itemPauta.getStatus());

        if(Login.usuarioLogado.getTipo() == 1) {
            holder.favoravel.setVisibility(View.GONE);

        }

        if(itemPauta.getStatus().equalsIgnoreCase("Concluído")){
            holder.favoravel.setVisibility(View.GONE);
            holder.contrario.setVisibility(View.GONE);
        }

        holder.favoravel.setOnClickListener( v -> {
            itemPauta.addVotoFavoravel();
            if(itemPauta.getFavoravel() == itemPauta.getTaxa_aprovacao()){
                itemPauta.setStatus("Concluído");
            }
            FirebaseFirestore.getInstance().collection("item")
                            .document(itemPauta.getId())
                                    .set(itemPauta)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    holder.favoravel.setVisibility(View.GONE);
                                                    holder.contrario.setVisibility(View.GONE);

                                                }
                                            });


        });

        holder.contrario.setOnClickListener( v-> {
            if(Login.usuarioLogado.getTipo() == 1){

                FirebaseFirestore.getInstance().collection("item").document(itemPauta.getId())
                        .delete();

                itemsPauta.remove(position);
                notifyItemRemoved(position);
            } else if (itemPauta.getFavoravel() > 0) {
                itemPauta.addVotoContrario();

                FirebaseFirestore.getInstance().collection("item")
                        .document(itemPauta.getId())
                        .set(itemPauta)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                holder.favoravel.setVisibility(View.GONE);
                                holder.contrario.setVisibility(View.GONE);

                            }
                        });

            }


        });

    }

    @Override
    public int getItemCount() {
        return itemsPauta.size();
    }
}
