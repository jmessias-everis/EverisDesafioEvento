package com.everis.everisdesafioevento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.everis.everisdesafioevento.DAO.EventoDAO;
import com.everis.everisdesafioevento.DAO.ParticipanteDAO;
import com.everis.everisdesafioevento.Domain.Evento;
import com.everis.everisdesafioevento.Domain.Participante;

import java.util.ArrayList;

public class ListarParticipantesActivity extends AppCompatActivity {
    TextView txtNomeEvento;
    TextView txtCidadeEData;
    TextView txtLocalEHorario;
    TextView qntdVagas;

    private ArrayList<Participante> listaDeParticipantes = new ArrayList<>();
    private ParticipanteDAO participanteDAO = null;
    private ArrayAdapter adapter;
    private ListView listEventos;
    private long idParticipante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_participantes);

        participanteDAO = new ParticipanteDAO(getBaseContext());





    }
}
