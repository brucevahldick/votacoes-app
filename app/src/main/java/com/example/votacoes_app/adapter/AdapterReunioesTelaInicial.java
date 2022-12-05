package com.example.votacoes_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.votacoes_app.Login;
import com.example.votacoes_app.ReuniaoPauta;
import com.example.votacoes_app.model.Reuniao;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterReunioesTelaInicial extends BaseAdapterReunioes{
    public AdapterReunioesTelaInicial(Context context, ArrayList<Reuniao> reunioes) {
        super(context, reunioes);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReunioes holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.remove.setVisibility(View.GONE);
        holder.optLayout.setGravity(Gravity.CENTER);
    }

    @Override
    public void updateButtonEvent(Reuniao reuniao) {
        if(Login.usuarioLogado.getTipo() != 2 ){
            renderMembro(reuniao);
        }else{
            renderSecretario(reuniao);
        }
    }

    @Override
    public void removeButtonEvent(Reuniao reuniao,int position){}

    public void renderSecretario(Reuniao reuniao){
        if(!Login.usuarioLogado.getNome().equalsIgnoreCase(reuniao.getSecretario())){
            exibeErro("Você não é o secretário desta reunião");
            return;
        }

        updateReuniaoAberta(reuniao);
        redirect(reuniao);
    }

    public void renderMembro(Reuniao reuniao){
        if(reuniao.getStatus() != 2){
            exibeErro("Reunião ainda não aberta");
            return;
        }

        redirect(reuniao);
    }

    public void exibeErro(String msg){
        Toast toast = Toast.makeText(this.context,
                msg,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    private void updateReuniaoAberta(Reuniao r) {
        r.setStatus(2);

        FirebaseFirestore.getInstance()
                .collection("reuniao")
                .document(r.getId())
                .set(r);
    }

    public void redirect(Reuniao reuniao){
        Intent intent = new Intent(this.context, ReuniaoPauta.class);
        intent.putExtra("Reuniao", reuniao);
        context.startActivity(intent);
    }
}
