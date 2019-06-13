package com.everis.everisdesafioevento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.everis.everisdesafioevento.Mask.Mask;

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

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            checarCamposVazios();
        }
    };

    private void checarCamposVazios() {
        String tedtNome = edtNome.getText().toString();
        String tedtEmail = edtEmail.getText().toString();
        String tTelefone = edtTelefone.getText().toString();

        if(tedtNome.equals("")||tedtEmail.equals("")||tTelefone.equals(""))

        {
            btnCadastrar.setEnabled(false);
            btnCadastrar.setAlpha(.5f);
        } else

        {
            btnCadastrar.setEnabled(true);
            btnCadastrar.setAlpha(1.0f);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricao_evento);

        Bundle extras = getIntent().getExtras();
//        Outra forma de receber os dados.
//        idUsuarioAtivo = getIntent().getLongExtra("idUsuarioAtivo", -1);
        idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
        Evento evento = (Evento) extras.get("eventoSelec");

        txtNomeEvento = findViewById(R.id.txtNomeEvento);
        txtCidadeEData = findViewById(R.id.txtCidadeData);
        txtLocalEHorario = findViewById(R.id.txtLocalHorario);
        qntdVagas = findViewById(R.id.ie_txtQtdVagas);

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

        edtNome = findViewById(R.id.edtIENome);
        edtEmail = findViewById(R.id.edtIEEmail);
        edtTelefone = findViewById(R.id.edtIETelefone);
        swConhece = findViewById(R.id.swConheceTema);
        btnCadastrar = findViewById(R.id.btnIECadastrar);

        edtTelefone.addTextChangedListener(Mask.insert("(##)#####-####", edtTelefone));

        edtNome.addTextChangedListener(textWatcher);
        edtEmail.addTextChangedListener(textWatcher);
        edtTelefone.addTextChangedListener(textWatcher);

        btnCadastrar.setEnabled(false);
        btnCadastrar.setAlpha(.5f);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();
                String telefone = edtTelefone.getText().toString();
                Boolean conhecimento = swConhece.isChecked();

                Participante participante = new Participante(nome, email, telefone, conhecimento, idUsuarioAtivo);

                if(!participanteDAO.verificarSeParticipanteExiste(participante.getEmail())){
                    participanteDAO.salvar(participante);
                }

                Registro registro = new Registro(participanteDAO.buscarIdPorEmail(email), evento.getId());
                if(registroDAO.salvar(registro)){
                    Toast.makeText(getApplicationContext(), "REGISTRO REALIZADO!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "REGISTRO NÃO FOI REALIZADO!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(InscricaoEventoActivity.this, ListarEventosActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                startActivity(intent);
            }
        });
    }
}