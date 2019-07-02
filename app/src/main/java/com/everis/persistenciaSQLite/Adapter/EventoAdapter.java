package com.everis.persistenciaSQLite.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.everis.persistenciaSQLite.Domain.Evento;
import com.everis.persistenciaSQLite.R;

import java.util.ArrayList;

public class EventoAdapter extends ArrayAdapter<Evento> {
    private final Context context;
    private final ArrayList<Evento> elementos;

    public EventoAdapter(Context context, ArrayList<Evento> elementos){
        super(context, R.layout.linha_item, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Evento evento = elementos.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_item, null);

        TextView nomeEvento = (TextView) rowView.findViewById(R.id.txtItemNome);
        TextView descricaoEvento = (TextView) rowView.findViewById(R.id.txtDescricao);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imgLogo);

        String descricao = elementos.get(position).getCidade() + " - " + elementos.get(position).getData();

        nomeEvento.setText(elementos.get(position).getNome());
        descricaoEvento.setText(descricao);
        imagem.setImageResource(elementos.get(position).getImagem());

        return rowView;
    }
}