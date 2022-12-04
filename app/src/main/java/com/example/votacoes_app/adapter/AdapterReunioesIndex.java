package com.example.votacoes_app.adapter;

import android.content.Context;
import android.content.Intent;

import com.example.votacoes_app.CadastroReunioes;
import com.example.votacoes_app.model.Reuniao;

import java.util.ArrayList;

public class AdapterReunioesIndex extends BaseAdapterReunioes{
    public AdapterReunioesIndex(Context context, ArrayList<Reuniao> reunioes) {
        super(context, reunioes);
    }

    @Override
    public void updateButtonEvent(Reuniao reuniao, ViewHolderReunioes holder) {
        Intent intent = new Intent(context, CadastroReunioes.class);
        intent.putExtra("Reuniao", reuniao);
        context.startActivity(intent);
    }

    @Override
    public void removeButtonEvent(Reuniao reuniao, ViewHolderReunioes holder){
        Intent intent = new Intent(context, CadastroReunioes.class);
        intent.putExtra("Reuniao", reuniao);
        context.startActivity(intent);
    }
}
