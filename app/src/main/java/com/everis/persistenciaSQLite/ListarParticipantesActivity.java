package com.everis.persistenciaSQLite;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.everis.persistenciaSQLite.Adapter.ParticipanteAdapter;
import com.everis.persistenciaSQLite.DAO.EventoDAO;
import com.everis.persistenciaSQLite.DAO.ParticipanteDAO;
import com.everis.persistenciaSQLite.DAO.RegistroDAO;
import com.everis.persistenciaSQLite.Domain.Evento;
import com.everis.persistenciaSQLite.Domain.Participante;

import java.util.ArrayList;
import java.util.Collections;

public class ListarParticipantesActivity extends AppCompatActivity {
    TextView txtNomeEvento;
    TextView txtCidadeEData;
    TextView txtLocalEHorario;
    TextView tqntdVagas;
    Button btnVoltar;
    Button btnEditar;
    Button btnDeletar;

    AlertDialog alertDialog;
    private ArrayList<Participante> listaDeParticipantes = new ArrayList<>();
    private ParticipanteDAO participanteDAO = null;
    private ArrayAdapter adapter;
    private ListView listEventos;
    private long idParticipante;
    //    private ListView listaDeParticipantes;
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
        qntParticipantes = registroDAO.contParticipantesPorIdEvento(eventoSelecionado.getId());

        btnVoltar = (Button) findViewById(R.id.btnLPVoltar);
        btnEditar = (Button) findViewById(R.id.btnLPEditar);
        btnDeletar = findViewById(R.id.btnLPDeletar);
        txtNomeEvento = (TextView) findViewById(R.id.txtLPNomeEvento);
        txtCidadeEData = (TextView) findViewById(R.id.txtLPCidadeData);
        txtLocalEHorario = (TextView) findViewById(R.id.txtLPLocalHorario);
        tqntdVagas = (TextView) findViewById(R.id.txtLPQtdVagas);

        String cidadeEData = eventoSelecionado.getCidade() + " - " + eventoSelecionado.getData();
        String localEHorario = eventoSelecionado.getLocal() + " - " + eventoSelecionado.getHorario();
        String descVagas = "Vagas: " + qntParticipantes + "/ " + eventoSelecionado.getVagas();

        txtNomeEvento.setText(eventoSelecionado.getNome());
        txtCidadeEData.setText(cidadeEData);
        txtLocalEHorario.setText(localEHorario);
        tqntdVagas.setText(descVagas);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarParticipantesActivity.this, ListarEventosActivity.class);
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
            eventoSelecionado = (Evento) extras.get("eventoSelec");
        }
        final Evento evento = new Evento();
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListarParticipantesActivity.this);
                builder.setTitle("Deletar");
                builder.setMessage("Deseja apagar este evento?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        eventoDAO.deletarEvento(eventoDAO.buscarPorId(eventoSelecionado.getId()));
                        Toast.makeText(getApplicationContext(), "Evento deletado!" + arg1, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ListarParticipantesActivity.this, ListarEventosActivity.class);
                        intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        if (!listaParticipantes.isEmpty()) {
            ArrayAdapter adapter = new ParticipanteAdapter(ListarParticipantesActivity.this, listaParticipantes);
            participanteList.setAdapter(adapter);

            participanteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final Participante participanteSelecionado = (Participante) participanteList.getItemAtPosition(position);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ListarParticipantesActivity.this);
                    builder.setTitle("Deletar");
                    builder.setMessage("Deseja apagar este participante?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            registroDAO.inativarParticipanteNoEvento(registroDAO.buscarPorId(participanteSelecionado.getId()));
                            Toast.makeText(getApplicationContext(), "Participante foi deletado!" + arg1, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ListarParticipantesActivity.this, ListarParticipantesActivity.class);
                            intent.putExtra("eventoSelec", eventoSelecionado);
                            intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                    return true;
                }
            });
        } else {
            participanteList.setAdapter(null);
            Toast.makeText(getApplicationContext(), "Não há participantes cadastrados neste evento!", Toast.LENGTH_LONG).show();

        }
    }
}