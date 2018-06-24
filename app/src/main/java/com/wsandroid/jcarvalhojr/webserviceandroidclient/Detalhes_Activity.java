package com.wsandroid.jcarvalhojr.webserviceandroidclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.UsuarioDao;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio.Usuario;
import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;

public class Detalhes_Activity extends AppCompatActivity implements View.OnClickListener {

    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydetalhes);

        // recupera os dados
        final EditText edtCodDetalhe = findViewById(R.id.edtCodDetalhe);
        final EditText edtNomeDetalhe = findViewById(R.id.edtNomeDetalhe);
        final EditText edtIdadeDetalhe = findViewById(R.id.edtIdadeDetalhe);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);

        edtCodDetalhe.setInputType(InputType.TYPE_NULL);
        edtNomeDetalhe.setInputType(InputType.TYPE_NULL);
        edtIdadeDetalhe.setInputType(InputType.TYPE_NULL);

        long idCli;
        idCli = getIntent().getLongExtra("Codigo", 0);

        UsuarioDao dao = new UsuarioDao();
        Usuario user = dao.buscaUsuarioPorId((int) idCli);

        edtCodDetalhe.setText(String.valueOf(idCli));
        edtNomeDetalhe.setText(user.getNome());
        //ed.setText(Integer.toString(x));
        //ed.setText(String.valueOf(x));
        edtIdadeDetalhe.setText(Integer.toString(user.getIdade()));

        // click botao
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity chama a Cadstro Activity
                Intent it = new Intent(Detalhes_Activity.this, MainActivityLogin.class);
                startActivity(it);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // click botao
        if (v.getId() == R.id.btnVoltar) {

            // MainActivity chama a Cadstro Activity
            Intent it = new Intent(Detalhes_Activity.this, MainActivityLogin.class);
            startActivity(it);
            finish();
        }
    }


  /*          @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhes_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
