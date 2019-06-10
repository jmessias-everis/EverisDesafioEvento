package com.everis.everisdesafioevento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.everis.everisdesafioevento.DAO.EventoDAO;
import com.everis.everisdesafioevento.DAO.RegistroDAO;
import com.everis.everisdesafioevento.Domain.Evento;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.w3c.dom.Text;

import java.util.concurrent.TimeoutException;

public class EditarEventoActivity extends AppCompatActivity {

    RegistroDAO registroDAO;
    EventoDAO eventoDAO;
    TextView resumoNomeEvento;
    TextView resumoLocalHorario;
    TextView resumoCidadeData;
    TextView resumoVagas;
    TextView edtNomeEvento;
    TextView edtLocalEvento;
    TextView edtCidadeEvento;
    TextView edtDataEvento;
    TextView edtHorarioEvento;
    TextView edtQtdVagas;
    Button btnCancelar;
    Button btnEditar;

    private long idUsuarioAtivo;
    Evento eventoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);

        registroDAO = new RegistroDAO(getBaseContext());
        eventoDAO = new EventoDAO(getBaseContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            idUsuarioAtivo = extras.getLong("idUsuarioAtivo");
            eventoSelecionado = (Evento) extras.get("eventoSelec");
        }

        resumoNomeEvento = (TextView) findViewById(R.id.txtEdENomeEvento);
        resumoLocalHorario = (TextView) findViewById(R.id.txtEdELocalHorario);
        resumoCidadeData = (TextView) findViewById(R.id.txtEdECidadeData);
        resumoVagas = (TextView) findViewById(R.id.resumotxtEdEQtdVagas);

        String localHorario = eventoSelecionado.getLocal() + " - "+ eventoSelecionado.getHorario();
        String cidadeData = eventoSelecionado.getCidade() + " - "+ eventoSelecionado.getData();

        edtNomeEvento = (TextView) findViewById(R.id.edtEdENome);
        edtLocalEvento = (TextView) findViewById(R.id.edtEdELocal);
        edtCidadeEvento = (TextView) findViewById(R.id.edtEdECidade);
        edtDataEvento = (TextView) findViewById(R.id.edtEdEData);
        edtHorarioEvento = (TextView) findViewById(R.id.edtEdEHorario);
        edtQtdVagas = (TextView) findViewById(R.id.edtEdEVagas);
        int imagem = (R.id.imgLogo);

        resumoNomeEvento.setText(eventoSelecionado.getNome());
        resumoLocalHorario.setText(localHorario);
        resumoCidadeData.setText(cidadeData);

        String resumoDasVagas = registroDAO.contParticipantesPorIdEvento(eventoSelecionado.getId()) + " / "+ eventoSelecionado.getVagas();
        resumoVagas.setText(resumoDasVagas);
        edtNomeEvento.setText(eventoSelecionado.getNome());
        edtLocalEvento.setText(eventoSelecionado.getLocal());
        edtCidadeEvento.setText(eventoSelecionado.getCidade());
        edtDataEvento.setText(eventoSelecionado.getData());
        edtHorarioEvento.setText(eventoSelecionado.getHorario());
        String qVagas = eventoSelecionado.getVagas() + "";
        edtQtdVagas.setText(qVagas);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditarEventoActivity.this, ListarParticipantesActivity.class);
                intent.putExtra("idUsuarioAtivo", idUsuarioAtivo);
                intent.putExtra("eventoSelec", eventoSelecionado);
                startActivity(intent);
            }
        });

    }
}
