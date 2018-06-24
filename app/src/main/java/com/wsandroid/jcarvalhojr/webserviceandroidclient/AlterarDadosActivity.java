package com.wsandroid.jcarvalhojr.webserviceandroidclient;

import android.support.v7.app.AppCompatActivity;


public class AlterarDadosActivity extends AppCompatActivity {


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityatualizardados);*/

/*        // recupera os dados
        final EditText edtCodigoId = (EditText) findViewById(R.id.edtCodigoId);
        final EditText edtNomePesquisado = (EditText) findViewById(R.id.edtNomeAlterar);
        final EditText edtIdadePesquisada = (EditText) findViewById(R.id.edtIdadePesquisada);


        Button btnPesquisarId = (Button) findViewById(R.id.btnPesquisarId);
        Button btnVoltartelaListar = (Button) findViewById(R.id.btnVoltartelaListar);
        Button btnLimparPesqId = (Button) findViewById(R.id.btnLimparPesqId);
        Button btnAlterar = (Button) findViewById(R.id.btnAlterar);*/

      /*  // onclick Cadastro
        btnPesquisarId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText edtCodigoId = (EditText) findViewById(R.id.edtCodigoId);


                if (!edtCodigoId.getText().toString().isEmpty()) {

                    UsuarioDao dao = new UsuarioDao();
                    Usuario user = dao.buscaUsuarioPorId(Integer.parseInt(edtCodigoId.getText().toString()));


                    edtNomePesquisado.setText(user.getNome());
                    //ed.setText(Integer.toString(x));
                    //ed.setText(String.valueOf(x));
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


     btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
*//**//**//*

                String codigo = edtCodigoId.getText().toString();
                String nome = edtNomePesquisado.getText().toString();
                String idade = edtIdadePesquisada.getText().toString();

                if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(idade))
                {
                    if (nome.isEmpty()){
                        edtNomePesquisado.requestFocus();
                        edtNomePesquisado.setError(Html.fromHtml("<font color='#145A14'>Informe o nome</font>"));
                        return;
                    } else if (idade.isEmpty()){
                        edtIdadePesquisada.requestFocus();
                        edtIdadePesquisada.setError(Html.fromHtml("<font color='#145A14'>Informe a idade</font>"));
                        return;
                    }

                }

                UsuarioDao dao = new UsuarioDao();


                boolean retorno = dao.atualizarUsuario(
                        (new Usuario(
                                Integer.parseInt(edtCodigoId.getText().toString()),
                                edtNomePesquisado.getText().toString(),
                                Integer.parseInt(edtIdadePesquisada.getText().toString())
                        )
                        )
                );

                if (retorno) {
                    Toast.makeText(AlterarDadosActivity.this, "registro alterados com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AlterarDadosActivity.this, "Erro ao alterar registro", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLimparPesqId.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
               edtCodigoId.requestFocus();
                edtCodigoId.setError(null);
                edtCodigoId.setText("");
                edtNomePesquisado.setText("");
                edtIdadePesquisada.setText("");*//**//**//**//*
            }
        });


        // click botao voltar
        btnVoltartelaListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // Cadstro Activity  chama a MainActivity
                Intent it = new Intent(AlterarDadosActivity.this, MainActivity.class);
                startActivity(it);*//**//*
            }
        });
    }*/
}
