package com.everis.everisdesafioevento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.everis.everisdesafioevento.DAO.EventoDAO;
import com.everis.everisdesafioevento.Domain.Evento;
import com.everis.everisdesafioevento.Adapter.EventoAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ListActivity extends AppCompatActivity {

    private ArrayList<Evento> listaDeEventos = new ArrayList<>();
    private EventoDAO eventoDAO = null;
    private ArrayAdapter adapter;
    private ListView listEventos;
    private long idUsuarioAtivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        eventoDAO = new EventoDAO(getBaseContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
        }

        listEventos = (ListView) findViewById(R.id.lvEventos);

        listEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(ListActivity.this, InscricaoEventoActivity.class);

                Evento eventoSelecionado = (Evento) listEventos.getItemAtPosition(position);
                intent2.putExtra("eventoSelec", eventoSelecionado);
                intent2.putExtra("idUsuarioAtivo", idUsuarioAtivo);

                startActivity(intent2);
            }
        });

//        IMPLEMENTAR
//        listEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                return false;
//            }
//        });

        Button le_novoEvento = (Button) findViewById(R.id.btnLENovoEvento);
        le_novoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ListActivity.this, CadastroEventoActivity.class);
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