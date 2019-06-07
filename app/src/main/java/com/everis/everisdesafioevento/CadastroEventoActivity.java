package com.everis.everisdesafioevento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everis.everisdesafioevento.DAO.EventoDAO;
import com.everis.everisdesafioevento.Domain.Evento;

public class CadastroEventoActivity extends AppCompatActivity {

    Button btnCECadastrar;
    Button btnCECancelar;
    EditText edtCEEvento;
    EditText edtCECidade;
    EditText edtCELocal;
    EditText edtCEData;
    EditText edtCEHora;
    EditText edtCEVagas;
    private long idUsuarioAtivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
        }

        btnCECadastrar = (Button) findViewById(R.id.btnIECadastrar);
        btnCECancelar = (Button) findViewById(R.id.btnCECancelar);

        btnCECancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroEventoActivity.this, ListActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                startActivity(intent);
            }
        });

        btnCECadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventoDAO eventoDAO = new EventoDAO(getBaseContext());
                Evento evento;

                edtCEEvento = (EditText) findViewById(R.id.edtCENome);
                edtCECidade = (EditText) findViewById(R.id.edtCECidade);
                edtCELocal = (EditText) findViewById(R.id.edtCELocal);
                edtCEData = (EditText) findViewById(R.id.edtCEData);
                edtCEHora = (EditText) findViewById(R.id.edtCEHorario);
                edtCEVagas = (EditText) findViewById(R.id.CEedtVagas);

                String nomeEvento = edtCEEvento.getText().toString();
                String cidade = edtCECidade.getText().toString();
                String local = edtCELocal.getText().toString();
                String[] string = edtCEData.getText().toString().split("/");
                String dataFormatada = string[2] + "-" + string[1] + "-" + string[0];
                String hora = edtCEHora.getText().toString();
                int vagas = Integer.parseInt(edtCEVagas.getText().toString());
                int imagem = R.drawable.logo01;

                evento = new Evento(nomeEvento, local, cidade, dataFormatada, hora, imagem, vagas);

                if(eventoDAO.salvar(evento)){
                    Toast.makeText(getApplicationContext(), "EVENTO CADASTRADO!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "EVENTO NÃO FOI CADASTRADO!", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(CadastroEventoActivity.this, ListActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                startActivity(intent);
            }
        });
    }
}
