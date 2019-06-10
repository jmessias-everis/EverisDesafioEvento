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
import android.widget.Toast;

import com.everis.everisdesafioevento.Adapter.ParticipanteAdapter;
import com.everis.everisdesafioevento.DAO.EventoDAO;
import com.everis.everisdesafioevento.DAO.ParticipanteDAO;
import com.everis.everisdesafioevento.DAO.RegistroDAO;
import com.everis.everisdesafioevento.Domain.Evento;
import com.everis.everisdesafioevento.Domain.Participante;

import java.util.ArrayList;
import java.util.Collections;

public class ListarParticipantesActivity extends AppCompatActivity {
    TextView txtNomeEvento;
    TextView txtCidadeEData;
    TextView txtLocalEHorario;
    TextView tqntdVagas;
    Button btnVoltar;
    Button btnEditar;

    private ArrayList<Participante> listaDeParticipantes = new ArrayList<>();
    private ParticipanteDAO participanteDAO = null;
    private ArrayAdapter adapter;
    private ListView listEventos;
    private long idParticipante;
    private EventoDAO eventoDAO;
    private RegistroDAO registroDAO;
    Participante participante = new Participante();
    Evento eventoSelecionado;
    //    Evento evento = new Evento();
    private long idUsuarioAtivo;
    private ListView participanteList;
    private String email;
    private int qntParticipantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_participantes);

        eventoDAO = new EventoDAO(getBaseContext());
        registroDAO = new RegistroDAO(getBaseContext());
        participanteDAO = new ParticipanteDAO(getBaseContext());
        participanteList = findViewById(R.id.LV_ListarParticipante);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
            eventoSelecionado = (Evento) extras.get("eventoSelec");
        }
        qntParticipantes =  registroDAO.contParticipantesPorIdEvento(eventoSelecionado.getId());

        btnVoltar = (Button) findViewById(R.id.btnLPVoltar);
        btnEditar = (Button) findViewById(R.id.btnLPEditar);
        txtNomeEvento = (TextView) findViewById(R.id.txtLPNomeEvento);
        txtCidadeEData = (TextView) findViewById(R.id.txtLPCidadeData);
        txtLocalEHorario = (TextView) findViewById(R.id.txtLPLocalHorario);
        tqntdVagas = (TextView) findViewById(R.id.txtLPQtdVagas);

        String cidadeEData = eventoSelecionado.getCidade() + " - " + eventoSelecionado.getData();
        String localEHorario = eventoSelecionado.getLocal() + " - " + eventoSelecionado.getHorario();
        String descVagas = "Vagas: "+ qntParticipantes + "/ " + eventoSelecionado.getVagas();

        txtNomeEvento.setText(eventoSelecionado.getNome());
        txtCidadeEData.setText(cidadeEData);
        txtLocalEHorario.setText(localEHorario);
        tqntdVagas.setText(descVagas);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarParticipantesActivity.this, ListActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                startActivity(intent);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarParticipantesActivity.this, EditarEventoActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                intent.putExtra("eventoSelec", eventoSelecionado);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Participante> listaParticipantes = registroDAO.listarParticipantesDoEvento(eventoSelecionado.getId());
        Collections.sort(listaParticipantes);

        if(!listaParticipantes.isEmpty()){
            ArrayAdapter adapter = new ParticipanteAdapter(this, listaParticipantes);
            participanteList.setAdapter(adapter);

        }else {
            Toast.makeText(getApplicationContext(), "Evento sem participante.", Toast.LENGTH_SHORT).show();
        }

    }
}