package com.everis.persistenciaSQLite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ImageView;


import com.everis.persistenciaSQLite.DAO.UsuarioDAO;
import com.everis.persistenciaSQLite.Domain.Usuario;

public class LoginActivity extends AppCompatActivity {

    Toolbar myToolbar;
    private UsuarioDAO usuarioDAO;
    private Button btnEntrar;
    private EditText edtMatricula;
    private EditText edtEmail;
    private EditText edtSenha;
    private ImageView logoEveris;

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
        setContentView(R.layout.activity_login);

//        Criando, referenciando e ativando Display
//        myToolbar = findViewById(R.id.tool_bar);
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);

        logoEveris = findViewById(R.id.img_login);
        AlphaAnimation animation = new AlphaAnimation(.1f, 1.0f);
        animation.setDuration(4000);
        logoEveris.startAnimation(animation);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        edtMatricula = (EditText) findViewById(R.id.edtMatricula);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        edtMatricula.addTextChangedListener(textWatcher);
        edtEmail.addTextChangedListener(textWatcher);
        edtSenha.addTextChangedListener(textWatcher);

        btnEntrar.setEnabled(false);
        btnEntrar.setAlpha(.5f);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UsuarioDAO usuarioDAO = new UsuarioDAO(getBaseContext());

                int matricula = Integer.parseInt(edtMatricula.getText().toString());
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                Usuario usuario = new Usuario(matricula, email, senha);

                if(!usuarioDAO.verificarSeUsuarioExiste(email)){
                    usuarioDAO.salvar(usuario);
                    Toast.makeText(getApplicationContext(), "NOVO USUARIO CADASTRADO!", Toast.LENGTH_LONG).show();
                }

                usuario = usuarioDAO.buscarPorEmail(email);

                Intent intent = new Intent(LoginActivity.this, ListarEventosActivity.class);
                intent.putExtra("idUsuarioAtivo", usuario.getId());
                startActivity(intent);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.votar:
//
//                Toast.makeText(LoginActivity.this, "Opção Voltar",Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }
}