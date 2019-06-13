package com.everis.everisdesafioevento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class DadosDoParticipanteActivity extends AppCompatActivity {
    private Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_do_participante);

        mytoolbar = findViewById(R.id.tool_bar);

    }
}
