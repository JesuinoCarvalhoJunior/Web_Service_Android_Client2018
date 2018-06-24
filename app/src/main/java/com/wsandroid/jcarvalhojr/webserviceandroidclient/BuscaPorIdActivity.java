package com.wsandroid.jcarvalhojr.webserviceandroidclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.UsuarioDao;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio.Usuario;
import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;


public class BuscaPorIdActivity extends AppCompatActivity implements Runnable, View.OnClickListener {

    private EditText edtCodigoId;
    private EditText edtNomePesquisado;
    private EditText edtIdadePesquisada;

    private Button btnPesquisarId;
    private Button btnVoltartelaListar;
    private Button btnLimparPesqId;
    private Handler handler = new Handler();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitybuscaporid);

        // recupera os dados
        edtCodigoId = findViewById(R.id.edtCodigoId);
        edtNomePesquisado = findViewById(R.id.edtNomePesquisado);
        edtIdadePesquisada = findViewById(R.id.edtIdadePesquisada);


        btnPesquisarId = findViewById(R.id.btnPesquisarId);
        btnVoltartelaListar = findViewById(R.id.btnVoltartelaListar);
        btnLimparPesqId = findViewById(R.id.btnLimparPesqId);


        btnPesquisarId.setOnClickListener(this);
        btnVoltartelaListar.setOnClickListener(this);
        btnLimparPesqId.setOnClickListener(this);

        LimparCampos();
/*
        // onclick Cadastro
        btnPesquisarId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText edtCodigoId = (EditText) findViewById(R.id.edtCodigoId);

                if (!edtCodigoId.getText().toString().isEmpty()) {

                    UsuarioDao dao = new UsuarioDao();
                    Usuario user = dao.buscaUsuarioPorId(Integer.parseInt(edtCodigoId.getText().toString().trim()));


                    edtNomePesquisado.setText(user.getNome());
                    //ed.setText(Integer.toString(x));
                    //ed.setText(String.valueOf(x));
                    //edtIdadePesquisada.setText(String.valueOf(user.getIdade()));
                    edtIdadePesquisada.setText(Integer.toString(user.getIdade()));
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
*/

        /*
        btnLimparPesqId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtCodigoId.requestFocus();
                edtCodigoId.setError(null);
                edtCodigoId.setText("");
                edtNomePesquisado.setText("");
                edtIdadePesquisada.setText("");
            }
        });

        */
        // click botao voltar
        btnVoltartelaListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cadstro Activity  chama a MainActivity
                Intent it = new Intent(BuscaPorIdActivity.this, MainActivityLogin.class);
                startActivity(it);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPesquisarId) {

            dialog = new ProgressDialog(this);
            dialog.setTitle("Pesquisa");
            dialog.setMessage("Aguarde...");
            dialog.show();

            Thread thread = new Thread(this);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                dialog.dismiss();
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.btnLimparPesqId) {
            LimparCampos();
        }
    }

    @Override
    public void run() {
        final EditText edtCodigoId = findViewById(R.id.edtCodigoId);

        try {
            if (!edtCodigoId.getText().toString().isEmpty()) {

                UsuarioDao dao = new UsuarioDao();
                final Usuario user = dao.buscaUsuarioPorId(Integer.parseInt(edtCodigoId.getText().toString().trim()));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        edtNomePesquisado.setText(user.getNome());
                        //ed.setText(Integer.toString(x));
                        //ed.setText(String.valueOf(x));
                        edtIdadePesquisada.setText(Integer.toString(user.getIdade()));
                    }
                });
            } else {
                String codigo = edtCodigoId.getText().toString();
                if (TextUtils.isEmpty(codigo)) {
                    edtCodigoId.setError(Html.fromHtml("<font color='#145A14'>Informe o código</font>"));
                    //edtCodigoId.requestFocus();
                }
            }
        } catch (Exception e) {
            dialog.dismiss();
            Log.e("Pesquisa ID", "Erro", e);
        } finally {
            dialog.dismiss();
        }
    }


    public void LimparCampos() {
        edtCodigoId.requestFocus();
        edtCodigoId.setError(null);
        edtCodigoId.setText("");
        edtNomePesquisado.setText("");
        edtIdadePesquisada.setText("");
    }
}
