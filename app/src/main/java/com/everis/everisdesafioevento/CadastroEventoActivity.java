package com.everis.everisdesafioevento;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.everis.everisdesafioevento.DAO.EventoDAO;
import com.everis.everisdesafioevento.Domain.Evento;
import com.everis.everisdesafioevento.Mask.Mask;

import java.util.Calendar;

public class CadastroEventoActivity extends AppCompatActivity {

    Button btnCECadastrar;
    Button btnCECancelar;
    EditText edtCEEvento;
    EditText edtCECidade;
    EditText edtCELocal;
    TextView txtCEData;
    TextView txtCEHora;
    EditText edtCEVagas;
    private long idUsuarioAtivo;
    DatePickerDialog datePickerDialog;

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

    private void checarCamposVazios(){
        edtCEEvento = "oi";
//        EditText edtCECidade;
//        EditText edtCELocal;
//        TextView txtCEData;
//        TextView txtCEHora;
//        EditText edtCEVagas;

        if (edtCEEvento.equals("")){

        }
        String sMatricula = (String) edtMatricula.getText().toString();
        String sEmail = (String) edtEmail.getText().toString();
        String sSenha = (String) edtSenha.getText().toString();


        if(sMatricula.equals("") || sEmail.equals("") || sSenha.equals("")){
            btnEntrar.setEnabled(false);
            btnEntrar.setAlpha(.5f);
        } else {
            btnEntrar.setEnabled(true);
            btnEntrar.setAlpha(1.0f);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
        }
        edtCEEvento = findViewById(R.id.edtCENome);
        edtCECidade = findViewById(R.id.edtCECidade);
        edtCELocal = findViewById(R.id.edtCELocal);
        txtCEData = findViewById(R.id.txCEData);
        txtCEHora = findViewById(R.id.txCEHorario);
        edtCEVagas = findViewById(R.id.CEedtVagas);
        btnCECadastrar = findViewById(R.id.btnIECadastrar);
        btnCECancelar = findViewById(R.id.btnCECancelar);

//        edtCEData.addTextChangedListener(Mask.insert("##/##/####", edtCEData));
//        edtCEHora.addTextChangedListener(Mask.insert("##:##", edtCEHora));

        txtCEData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int cAno = calendar.get(Calendar.YEAR);
                int cMes = calendar.get(Calendar.MONTH);
                int cDia = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(CadastroEventoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                                txtCEData.setText(dia + "/" + (mes + 1) + "/" + ano);
                            }
                        }, cAno, cMes, cDia);
                datePickerDialog.show();
            }
        });

        txtCEHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarTime = Calendar.getInstance();
                int tHora = calendarTime.get(Calendar.HOUR_OF_DAY);
                int tMinuto = calendarTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(CadastroEventoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                        String horarioFormatado = hora + ":" + String.format("%02d", minuto);
                        txtCEHora.setText(horarioFormatado);
                    }
                }, tHora, tMinuto, true);
                timePickerDialog.show();
            }
        });

        btnCECancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroEventoActivity.this, ListarEventosActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                startActivity(intent);
            }
        });

        btnCECadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventoDAO eventoDAO = new EventoDAO(getBaseContext());
                Evento evento;


                String nomeEvento = edtCEEvento.getText().toString();
                String cidade = edtCECidade.getText().toString();
                String local = edtCELocal.getText().toString();
                String[] string = txtCEData.getText().toString().split("/");
                String dataFormatada = string[2] + "-" + string[1] + "-" + string[0];
                String hora = txtCEHora.getText().toString();
                int vagas = Integer.parseInt(edtCEVagas.getText().toString());
                int imagem = R.drawable.logo01;

                evento = new Evento(nomeEvento, local, cidade, dataFormatada, hora, imagem, vagas);

                if (eventoDAO.salvar(evento)) {
                    Toast.makeText(getApplicationContext(), "EVENTO CADASTRADO!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "EVENTO NÃO FOI CADASTRADO!", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(CadastroEventoActivity.this, ListarEventosActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                startActivity(intent);
            }
        });
    }
}