package com.everis.persistenciaSQLite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.everis.persistenciaSQLite.DAO.EventoDAO;
import com.everis.persistenciaSQLite.DAO.ParticipanteDAO;
import com.everis.persistenciaSQLite.DAO.UsuarioDAO;
import com.everis.persistenciaSQLite.Domain.Evento;
import com.everis.persistenciaSQLite.Adapter.EventoAdapter;
import com.everis.persistenciaSQLite.Domain.Participante;
import com.everis.persistenciaSQLite.Domain.Usuario;

import java.util.ArrayList;
import java.util.Collections;

public class ListarEventosActivity extends AppCompatActivity {

    private ArrayList<Evento> listaDeEventos = new ArrayList<>();
    private EventoDAO eventoDAO = null;
    private UsuarioDAO usuarioDAO = null;
    private Usuario usuario;
    private Participante participante;
    private ParticipanteDAO participanteDAO = null;
    private ArrayAdapter adapter;
    private ListView listEventos;
    private long idUsuarioAtivo;
    Button le_bt_novoEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_eventos);

        le_bt_novoEvento = (Button) findViewById(R.id.btnLENovoEvento);
        eventoDAO = new EventoDAO(getBaseContext());
        usuarioDAO = new UsuarioDAO(getBaseContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
        }

        if(!usuarioDAO.buscarPorId(idUsuarioAtivo).isAdmin()) {
            le_bt_novoEvento.setVisibility(View.INVISIBLE);
        }

        listEventos = (ListView) findViewById(R.id.lvEventos);

        listEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(ListarEventosActivity.this, InscricaoEventoActivity.class);

                Evento eventoSelecionado = (Evento) listEventos.getItemAtPosition(position);
                intent2.putExtra("eventoSelec", eventoSelecionado);
                intent2.putExtra("idUsuarioAtivo", idUsuarioAtivo);

                startActivity(intent2);
            }
        });

        listEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent2 = new Intent(ListarEventosActivity.this, ListarParticipantesActivity.class);

                participante = new Participante();
                Evento eventoSelecionado = (Evento) listEventos.getItemAtPosition(position);
                intent2.putExtra("eventoSelec", eventoSelecionado);
                intent2.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                startActivity(intent2);
                return true;
            }
        });

        le_bt_novoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ListarEventosActivity.this, CadastroEventoActivity.class);
                intent3.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                startActivity(intent3);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        listaDeEventos = eventoDAO.buscarTodos();
        Collections.sort(listaDeEventos);


        if(!listaDeEventos.isEmpty()){
            adapter = new EventoAdapter(this, listaDeEventos);
            listEventos.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "Não há eventos cadastrados!", Toast.LENGTH_LONG).show();
        }
    }
}