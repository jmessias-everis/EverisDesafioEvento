package com.everis.everisdesafioevento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.everis.everisdesafioevento.Domain.Evento;
import com.everis.everisdesafioevento.Domain.Participante;
import com.everis.everisdesafioevento.Domain.Registro;
import com.everis.everisdesafioevento.DAO.ParticipanteDAO;
import com.everis.everisdesafioevento.DAO.RegistroDAO;

public class InscricaoEventoActivity extends AppCompatActivity {
    TextView txtNomeEvento;
    TextView txtCidadeEData;
    TextView txtLocalEHorario;
    TextView qntdVagas;
    EditText edtNome;
    EditText edtEmail;
    EditText edtTelefone;
    Switch swConhece;
    Button btnCadastrar;
    long idUsuarioAtivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricao_evento);

        Bundle extras = getIntent().getExtras();

        idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
        Evento evento = (Evento) extras.get("eventoSelec");


        txtNomeEvento = (TextView) findViewById(R.id.txtNomeEvento);
        txtCidadeEData = (TextView) findViewById(R.id.txtCidadeData);
        txtLocalEHorario = (TextView) findViewById(R.id.txtLocalHorario);
        qntdVagas = (TextView) findViewById(R.id.ie_txtQtdVagas);

        String cidadeEData = evento.getCidade() + " - " + evento.getData();
        String localEHorario = evento.getLocal() + " - " + evento.getHorario();
        String descVagas = "Total de vagas: " + evento.getVagas();

        txtNomeEvento.setText(evento.getNome());
        txtCidadeEData.setText(cidadeEData);
        txtLocalEHorario.setText(localEHorario);
        qntdVagas.setText(descVagas);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        final Evento evento = (Evento) intent.getSerializableExtra("eventoSelec");
        idUsuarioAtivo = (long) intent.getSerializableExtra("idUsuarioAtivo");

        final ParticipanteDAO participanteDAO = new ParticipanteDAO(getBaseContext());
        final RegistroDAO registroDAO = new RegistroDAO(getBaseContext());

        btnCadastrar = (Button) findViewById(R.id.btnIECadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtNome = (EditText) findViewById(R.id.edtIENome);
                edtEmail = (EditText) findViewById(R.id.edtIEEmail);
                edtTelefone = (EditText) findViewById(R.id.edtIETelefone);
                swConhece = (Switch) findViewById(R.id.swConheceTema);

                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();
                String telefone = edtTelefone.getText().toString();
                Boolean conhecimento = swConhece.isChecked();

                Participante participante = new Participante(nome, email, telefone, conhecimento, idUsuarioAtivo);

                if(!participanteDAO.verificarSeParticipanteExiste(participante.getEmail())){
                    participanteDAO.salvar(participante);
                }

                Registro registro = new Registro(evento.getId(), participanteDAO.buscarIdPorEmail(email));
                if(registroDAO.salvar(registro)){
                    Toast.makeText(getApplicationContext(), "REGISTRO REALIZADO!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "REGISTRO NÃO FOI REALIZADO!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(InscricaoEventoActivity.this, ListActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                startActivity(intent);
            }
        });
    }
}