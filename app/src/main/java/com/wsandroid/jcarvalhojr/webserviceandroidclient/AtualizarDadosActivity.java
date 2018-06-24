package com.wsandroid.jcarvalhojr.webserviceandroidclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.Servicos.Singleton;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.UsuarioDao;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio.Usuario;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpersconnectivity.ConnectivityServices;
import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;

public class AtualizarDadosActivity extends AppCompatActivity {

    private String nomeAlterar;
    private String idadeAterar;
    private Integer codigoAlterar;

    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityatualizardados);

        // recupera os dados
        final EditText edtCodId = findViewById(R.id.edtCodId);
        final EditText edtNomeAlterar = findViewById(R.id.edtNomeAlterar);
        final EditText edtIdadeAlterar = findViewById(R.id.edtIdadeAlterar);


        Button btnPesqIdAlterar = findViewById(R.id.btnPesqIdAlterar);
        Button btnAlterarDados = findViewById(R.id.btnAlterarDados);
        Button btnVoltartelaListar = findViewById(R.id.btnVoltartelaListar);

        progressBar = (ProgressBar) findViewById(R.id.progressBarAtualizaDados);

        // onclick Cadastro
        btnPesqIdAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText edtCodigoId = findViewById(R.id.edtCodId);

                if (!edtCodigoId.getText().toString().isEmpty()) {

                    UsuarioDao dao = new UsuarioDao();
                    Usuario user = dao.buscaUsuarioPorId(Integer.parseInt(edtCodId.getText().toString()));

                    edtNomeAlterar.setText(user.getNome());

                    edtIdadeAlterar.setText(Integer.toString(user.getIdade()));

                } else {
                    String codigo = edtCodigoId.getText().toString();
                    if (TextUtils.isEmpty(codigo)) {
                        edtCodigoId.setError(Html.fromHtml("<font color='#145A14'>Informe o código</font>"));
                        //edtCodigoId.requestFocus();
                        return;
                    }
                }
            }
        });


        btnAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoAlterar = Integer.parseInt(edtCodId.getText().toString().trim());
                nomeAlterar = edtNomeAlterar.getText().toString().trim();
                idadeAterar = edtIdadeAlterar.getText().toString().trim();

                if (TextUtils.isEmpty(nomeAlterar) || TextUtils.isEmpty(idadeAterar)) {
                    if (nomeAlterar.isEmpty()) {
                        edtNomeAlterar.requestFocus();
                        edtNomeAlterar.setError(Html.fromHtml("<font color='#145A14'>Informe o nome</font>"));
                        return;
                    } else if (idadeAterar.isEmpty()) {
                        edtIdadeAlterar.requestFocus();
                        edtIdadeAlterar.setError(Html.fromHtml("<font color='#145A14'>Informe a idade</font>"));
                        return;
                    }

                }
                atualizaDadosTaskExecute();
            }
        });

/*        btnLimparPesqId.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
               edtCodigoId.requestFocus();
                edtCodigoId.setError(null);
                edtCodigoId.setText("");
                edtNomePesquisado.setText("");
                edtIdadePesquisada.setText("");
            }
        });*/


        // click botao voltar
        btnVoltartelaListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cadstro Activity  chama a MainActivity
                Intent it = new Intent(AtualizarDadosActivity.this, MainActivityLogin.class);
                startActivity(it);
            }
        });
    }

    public void atualizaDadosTaskExecute() {
        atualizaDadosTask task = new atualizaDadosTask();
        task.execute();
    }


    public class atualizaDadosTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            UsuarioDao dao = new UsuarioDao();
            return dao.atualizarUsuario(
                    (new Usuario(codigoAlterar, nomeAlterar, Integer.parseInt(idadeAterar), "", "")
                    )
            );
        }

        @Override
        protected void onPostExecute(Boolean atualizouDados) {
            progressBar.setVisibility(View.GONE);
            if (atualizouDados) {
                Toast.makeText(AtualizarDadosActivity.this, "registro alterados com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                if (Singleton.timeOut) {
                    progressBar.setVisibility(View.GONE);
                    ConnectivityServices.alertDialog(AtualizarDadosActivity.this, "Time out", getResources().getString(R.string.error_timeout));
                }
                if (Singleton.httpException) {
                    progressBar.setVisibility(View.GONE);
                    ConnectivityServices.alertDialog(AtualizarDadosActivity.this, "Conectividade", getResources().getString(R.string.error_conexao_indisponivel));
                }
                if (Singleton.genericException) {
                    progressBar.setVisibility(View.GONE);
                    ConnectivityServices.alertDialog(AtualizarDadosActivity.this, "Informação", getResources().getString(R.string.error_generico));
                }
            }
        }
    }
}
