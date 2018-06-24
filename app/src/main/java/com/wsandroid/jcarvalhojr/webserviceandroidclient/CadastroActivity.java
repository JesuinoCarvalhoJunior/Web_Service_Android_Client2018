package com.wsandroid.jcarvalhojr.webserviceandroidclient;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.UsuarioDao;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio.Usuario;
import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;


/**
 * Created by Junior_Carvalho on 20/09/2015.
 */
public class CadastroActivity extends AppCompatActivity {

    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cadastro);

        // recupera os dados
        final EditText edtNome = findViewById(R.id.edtNome);
        final EditText edtIdade = findViewById(R.id.edtIdade);
        final EditText edtLogin = findViewById(R.id.edtLogin);
        final EditText edtSenha = findViewById(R.id.edtSenha);

        Button btnCadastro = findViewById(R.id.btnConfirmar);
        Button btnVoltar = findViewById(R.id.btnVoltar);
        Button btnabreTelaPesquisa = findViewById(R.id.btnabreTelaPesquisa);
        Button btnAcessar = findViewById(R.id.btnAcessar);

        Button btnAbreTelaAlterar = findViewById(R.id.btnAbreTelaAlterar);

        // onclick Cadastro
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomecad = edtNome.getText().toString();
                String idadecad = edtIdade.getText().toString();

                String logincad = edtLogin.getText().toString();
                String senhacad = edtSenha.getText().toString();

                if (TextUtils.isEmpty(nomecad) || TextUtils.isEmpty(idadecad) ||TextUtils.isEmpty(logincad)||TextUtils.isEmpty(senhacad) )  {
                    if (nomecad.isEmpty()) {
                        edtNome.requestFocus();
                        edtNome.setError(Html.fromHtml("<font color='#00FF00'>Informe o nome</font>"));
                        return;
                    } else if (idadecad.isEmpty()) {
                        edtIdade.requestFocus();
                        edtIdade.setError(Html.fromHtml("<font color='#145A14'>Informe a idade</font>"));
                        return;
                    }else if (logincad.isEmpty()){
                        edtLogin.requestFocus();
                        edtLogin.setError(Html.fromHtml("<font color='#145A14'>Informe o login</font>"));
                        return;
                    }else if (senhacad.isEmpty()){
                        edtSenha.requestFocus();
                        edtSenha.setError(Html.fromHtml("<font color='#145A14'>Informe a senha</font>"));
                        return;
                    }

                }

                UsuarioDao dao = new UsuarioDao();

                boolean resultado = dao.inserirUsuario
                        (new Usuario (0, edtNome.getText().toString().trim(),
                                        Integer.parseInt(edtIdade.getText().toString().trim()),
                                edtLogin.getText().toString().trim(),
                                edtSenha.getText().toString().trim())
                        );

                if (resultado) {
                    Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // click botao voltar
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cadstro Activity  chama a MainActivity
               Intent it = new Intent(CadastroActivity.this, MainActivityLogin.class);

                startActivity(it);
            }
        });

        btnabreTelaPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cadstro Activity  chama a MainActivity
                Intent it = new Intent(CadastroActivity.this, BuscaPorIdActivity.class);
                startActivity(it);
            }
        });


        btnAbreTelaAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(CadastroActivity.this, AtualizarDadosActivity.class);
                startActivity(it);
            }
        });

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(CadastroActivity.this, MainActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                it.putExtra("Exit", true);
                startActivity(it);

            }
        });

    }
    @Override
    public void onBackPressed(){
        // Cadstro Activity  chama a MainActivity
        Intent it = new Intent(getApplicationContext(), MainActivityLogin.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        it.putExtra("Exit", true);
        startActivity(it);
    }

}

