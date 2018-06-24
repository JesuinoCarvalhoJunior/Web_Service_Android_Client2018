package com.wsandroid.jcarvalhojr.webserviceandroidclient.Logins;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.UsuarioDao;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpersconnectivity.ConnectivityServices;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.MainActivity;
import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;


public class LoginActivity extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtPassword;
    private Resources resources;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        Button btnEnter = (Button) findViewById(R.id.login_btn_enter);

        initViews();


        btnEnter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.login_btn_enter) {

                    if(ConnectivityServices.isNetworkAvailable(context)) {
                        if (validateFields()) {
                            UsuarioDao dao = new UsuarioDao();

                            boolean autenticarUser = dao.Autenticar(edtUser.getText().toString().trim(), edtPassword.getText().toString().trim());

                              if (autenticarUser) {
                                // Toast.makeText(this, "Autenticado com sucesso!", Toast.LENGTH_LONG).show();
                                Toast.makeText(LoginActivity.this, resources.getString(R.string.login_auth_ok), Toast.LENGTH_LONG).show();

                                //retorna lista inicial
                                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(it);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Erro ao cadastrar", Toast.LENGTH_LONG).show();
                            }
                        }
                        //
                    }else{
                        Toast.makeText(LoginActivity.this, R.string.error_conexao_indisponivel, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void callClearErrors(Editable s) {
        if (!s.toString().isEmpty()) {
            clearErrorFields(edtUser);
        }
    }

    private boolean validateFields() {
        String user = edtUser.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        return (!isEmptyFields(user, pass) && hasSizeValid(user, pass));
    }

    private boolean isEmptyFields(String user, String pass) {
        if (TextUtils.isEmpty(user)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_required));
            return true;
        } else if (TextUtils.isEmpty(pass)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.login_password_required));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String user, String pass) {

        if (!(user.length() > 3)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_size_invalid));
            return false;
        } else if (!(pass.length() > 5)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.login_pass_size_invalid));
            return false;
        }
        return true;
    }


     //   * Limpa os ícones e as mensagens de erro dos campos desejados
     //        * @param editTexts lista de campos do tipo EditText

    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }


    private void initViews() {
        resources = getResources();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callClearErrors(s);
            }
        };

        edtUser = (EditText) findViewById(R.id.login_edt_user);
        edtUser.addTextChangedListener(textWatcher);
        edtPassword = (EditText) findViewById(R.id.login_edt_password);
        edtPassword.addTextChangedListener(textWatcher);
        // Button btnEnter = (Button) findViewById(R.id.login_btn_enter);
        // btnEnter.setOnClickListener(this);
    }
}


