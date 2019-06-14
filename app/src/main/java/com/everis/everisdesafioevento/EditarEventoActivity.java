package com.everis.everisdesafioevento;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.everis.everisdesafioevento.DAO.EventoDAO;
import com.everis.everisdesafioevento.DAO.RegistroDAO;
import com.everis.everisdesafioevento.Domain.Evento;
import com.everis.everisdesafioevento.Mask.Mask;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.concurrent.TimeoutException;

public class EditarEventoActivity extends AppCompatActivity {

    RegistroDAO registroDAO;
    EventoDAO eventoDAO;
    TextView resumoNomeEvento;
    TextView resumoLocalHorario;
    TextView resumoCidadeData;
    TextView resumoVagas;
    EditText edtNomeEvento;
    EditText edtLocalEvento;
    TextView edtDataEvento;
    TextView edtHorarioEvento;
    EditText edtQtdVagas;
    Button btnCancelar;
    Button btnEditar;
    Evento evento;
    Spinner spnCidade;
    DatePickerDialog datePickerDialog;

    private long idUsuarioAtivo;
    Evento eventoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        registroDAO = new RegistroDAO(getBaseContext());
        eventoDAO = new EventoDAO(getBaseContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
            eventoSelecionado = (Evento) extras.get("eventoSelec");
        }

        resumoNomeEvento = findViewById(R.id.txtEdENomeEvento);
        resumoLocalHorario = findViewById(R.id.txtEdELocalHorario);
        resumoCidadeData = findViewById(R.id.txtEdECidadeData);
        resumoVagas = findViewById(R.id.resumotxtEdEQtdVagas);
        btnEditar = findViewById(R.id.btnEdEEditar);
        btnCancelar = findViewById(R.id.btnEdECancelar);

        String localHorario = eventoSelecionado.getLocal() + " - " + eventoSelecionado.getHorario();
        String cidadeData = eventoSelecionado.getCidade() + " - " + eventoSelecionado.getData();

        edtNomeEvento = findViewById(R.id.edtEdENome);
        edtLocalEvento = findViewById(R.id.edtEdELocal);
        spnCidade = findViewById(R.id.spinner_EECidade);
        edtDataEvento = findViewById(R.id.edtEdEData);
        edtHorarioEvento = findViewById(R.id.edtEdEHorario);
        edtQtdVagas = findViewById(R.id.edtEdEVagas);
        int imagem = (R.id.imgLogo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_cidades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCidade.setAdapter(adapter);

//        edtDataEvento.addTextChangedListener(Mask.insert("##/##/####", edtDataEvento));
//        edtHorarioEvento.addTextChangedListener(Mask.insert("##:##", edtHorarioEvento));

        resumoNomeEvento.setText(eventoSelecionado.getNome());
        resumoLocalHorario.setText(localHorario);
        resumoCidadeData.setText(cidadeData);

        String resumoDasVagas = registroDAO.contParticipantesPorIdEvento(eventoSelecionado.getId()) + " / " + eventoSelecionado.getVagas();
        resumoVagas.setText(resumoDasVagas);
        edtNomeEvento.setText(eventoSelecionado.getNome());
        edtLocalEvento.setText(eventoSelecionado.getLocal());

        int spinnerPosition = adapter.getPosition(eventoSelecionado.getCidade());
        spnCidade.setSelection(spinnerPosition);

        edtDataEvento.setText(eventoSelecionado.getData());
        edtHorarioEvento.setText(eventoSelecionado.getHorario());
        String qVagas = eventoSelecionado.getVagas() + "";
        edtQtdVagas.setText(qVagas);

        edtDataEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int cAno = calendar.get(Calendar.YEAR);
                int cMes = calendar.get(Calendar.MONTH);
                int cDia = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditarEventoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                                edtDataEvento.setText(dia + "/" + (mes + 1) + "/" + ano);
                            }
                        }, cAno, cMes, cDia);
                datePickerDialog.show();
            }
        });

        edtHorarioEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarTime = Calendar.getInstance();
                int tHora = calendarTime.get(Calendar.HOUR_OF_DAY);
                int tMinuto = calendarTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(EditarEventoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                        String horarioFormatado = hora + ":" + String.format("%02d", minuto);
                        edtHorarioEvento.setText(horarioFormatado);
                    }
                }, tHora, tMinuto, true);
                timePickerDialog.show();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditarEventoActivity.this, ListarParticipantesActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                intent.putExtra("eventoSelec", eventoSelecionado);
                startActivity(intent);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventoDAO eventoDAO = new EventoDAO(getBaseContext());
//                Evento evento;

                String nomeEvento = edtNomeEvento.getText().toString();
                String cidade = spnCidade.getSelectedItem().toString();
                String local = edtLocalEvento.getText().toString();
                String[] string = edtDataEvento.getText().toString().split("/");
                String dataFormatada = string[2] + "-" + string[1] + "-" + string[0];
                String hora = edtHorarioEvento.getText().toString();
                int vagas = Integer.parseInt(edtQtdVagas.getText().toString());
                int imagem = R.drawable.logo01;


                evento = new Evento(nomeEvento, local, cidade, dataFormatada, hora, imagem, vagas);
                evento.setId(eventoSelecionado.getId());
                if (eventoDAO.salvarEdicaoEvento(evento)) {
                    Toast.makeText(getApplicationContext(), "EVENTO EDITADO COM SUCESSO!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "EVENTO NÃO FOI EDITADO!", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(EditarEventoActivity.this, ListarParticipantesActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                intent.putExtra("eventoSelec", evento);
                startActivity(intent);
            }
        });
    }
}