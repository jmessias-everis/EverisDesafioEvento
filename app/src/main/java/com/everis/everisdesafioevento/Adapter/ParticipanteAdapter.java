package com.everis.everisdesafioevento.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.everis.everisdesafioevento.Domain.Evento;
import com.everis.everisdesafioevento.Domain.Participante;
import com.everis.everisdesafioevento.R;

import java.util.ArrayList;

public class ParticipanteAdapter extends ArrayAdapter<Participante> {
        private final Context context;
        private final ArrayList<Participante> elementos;

        public ParticipanteAdapter(Context context, ArrayList<Participante> elementos){
            super(context, R.layout.linha_participante, elementos);
            this.context = context;
            this.elementos = elementos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Participante participante = elementos.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.linha_participante, null);

            TextView nomeParticipante = (TextView) rowView.findViewById(R.id.txtParticipanteNome);
            TextView emailParticipante = (TextView) rowView.findViewById(R.id.txtEmailParticipante);
//            ImageView imagem = (ImageView) rowView.findViewById(R.id.imgLogo);

//            String descricao = elementos.get(position).getCidade() + " - " + elementos.get(position).getData();

            nomeParticipante.setText(elementos.get(position).getNome());
            emailParticipante.setText(elementos.get(position).getEmail());

            return rowView;
        }
    }
