package com.wsandroid.jcarvalhojr.webserviceandroidclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.Servicos.Singleton;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.UsuarioDao;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpers.MensagemHelper;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpersconnectivity.ConnectivityServices;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpersconnectivity.GetTypeNetwork;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Logins.Servico.validaAcessoServico;
import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;

import java.lang.ref.WeakReference;

import static com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpers.MensagemHelper.clearErrorFields;
import static com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpers.MensagemHelper.clearFields;


public class MainActivity extends AppCompatActivity {
    private LinearLayout ln;
    private EditText edtUser;
    private EditText edtPassword;
    private Resources resources;
    private String tipoConexaoAtual;

    //    private ProgressDialog dialogo = null;
    private String versaoinfo;

    private static final String TAG = "StackTrace";
    private SwipeRefreshLayout swipeLayout;
    private LinearLayout fw_loading_layout;
    private WeakReference<Activity> weakActivity;

    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "<< onCreate >>");
        TextView versao = (TextView) findViewById(R.id.versao);
        versao.setText(MensagemHelper.getVersionName(this));
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        versaoinfo = versao.getText().toString();

        TextView tipoConexao = (TextView) findViewById(R.id.tipoConexao);

        tipoConexao.setText(GetTypeNetwork.getTypeNetwork(this));



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Log.d(TAG, " VERSION.SDK_INT");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar titulo = getSupportActionBar();
        titulo.setTitle("Júnior");

        Button btnEnter = (Button) findViewById(R.id.login_btn_enter);
        initViews();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, " setOnClickListener");
                if (v.getId() == R.id.login_btn_enter)
                    //       Snackbar.make(ln, "Olá", Snackbar.LENGTH_SHORT).show();

                    // ---0 - VISIBLE; 4 - INVISIBLE; 8 - GONE---
                    progressBar.setVisibility(v.GONE);

                if (ConnectivityServices.isNetworkAvailable(MainActivity.this)) {
                    if (validateFields()) { // && validaAcessoServico.validaAcesso(edtUser.getText().toString().trim(), edtPassword.getText().toString().trim())) {

                        progressBar.setVisibility(v.VISIBLE);
                        new Thread() {
                            @Override
                            public void run() {

                                try {
                                    //       synchronized (this) {
                                    if (validaAcessoServico.validaAcesso(MainActivity.this, edtUser.getText().toString().trim(), edtPassword.getText().toString().trim())) {

                                        showSuccess();
                                        /*runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // progressBar.setVisibility(View.GONE);
                                                //retorna lista inicial
                                                Intent it = new Intent(MainActivity.this, MainActivityLogin.class);
                                                startActivity(it);
                                                finish();
                                            }
                                        });*/

                                    } else {
                                        showError();
                  /*                      runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (Singleton.timeOut) {
                                                    progressBar.setVisibility(View.GONE);
                                                    //Toast.makeText(MainActivity.this, "Time out!", Toast.LENGTH_SHORT).show();
                                                    ConnectivityServices.alertDialog(MainActivity.this, "Time out", "Servidor não responde!");
                                                }
                                                if (Singleton.httpException) {
                                                    progressBar.setVisibility(View.GONE);
                                                    //Toast.makeText(MainActivity.this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
                                                    ConnectivityServices.alertDialog(MainActivity.this, "Conectividade", "Verifique sua internet ou Wi-Fi!");
                                                }
                                                if (Singleton.genericException) {
                                                    progressBar.setVisibility(View.GONE);
                                                    //Toast.makeText(MainActivity.this, "Tente mais tarde!", Toast.LENGTH_SHORT).show();
                                                    ConnectivityServices.alertDialog(MainActivity.this, "Informação", "Tente mais tarde!");
                                                }
                                            }
                                        });*/
                                    }
                                } catch (Exception e) {
                                    progressBar.setVisibility(v.GONE);
                                    ConnectivityServices.alertDialog(MainActivity.this, "Erro", e.getMessage());
                                    Log.e("Thread Acesso", "Erro: " + e.getMessage(), e);
                                }
                            }
                        }.start();

                    } else {
                        progressBar.setVisibility(v.GONE);
                        // clearFields(edtUser, edtPassword);
                        edtUser.requestFocus(); //seta o foco para o campo user
                        //validateFields();
                        Toast.makeText(MainActivity.this, resources.getString(R.string.login_auth_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(v.GONE);
                    Toast.makeText(MainActivity.this, R.string.error_conexao_indisponivel, Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void callClearErrors(Editable s) {
        Log.d(TAG, " callClearErrors");
        if (!s.toString().isEmpty()) {
            clearErrorFields(edtUser);
        }
    }

    public boolean validateFields() {
        Log.d(TAG, " validateFields");
        String login = edtUser.getText().toString().trim();
        String senha = edtPassword.getText().toString().trim();

        return (!isEmptyFields(login, senha) && hasSizeValid(login, senha));
    }


    // private boolean isEmptyFields(String user, String pass) {
    public boolean isEmptyFields(String user, String pass) {
        Log.d(TAG, " isEmptyFields");
        if (TextUtils.isEmpty(user)) {
            edtUser.requestFocus(); //seta o foco para o campo user
            //  edtUser.setError(resources.getString(R.string.login_user_required));
            edtUser.setError(Html.fromHtml("<font color='#00FF00'>informe o login</font>"));
            return true;

        } else if (TextUtils.isEmpty(pass)) {
            edtPassword.requestFocus(); //seta o foco para o campo password
            //  edtPassword.setError(resources.getString(R.string.login_password_required));
            edtPassword.setError(Html.fromHtml("<font color='#00FF00'>informe a senha</font>"));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String user, String pass) {
        Log.d(TAG, " hasSizeValid");

        if (!(user.length() > 1)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_size_invalid));
            return false;
        } else if (!(pass.length() > 1)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.login_pass_size_invalid));
            return false;
        }
        return true;
    }

    /**
     * Limpa os ícones e as mensagens de erro dos campos desejados
     *
     * @param editTexts lista de campos do tipo EditText
     */
/*    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }*/
    private void initViews() {
        Log.d(TAG, " initViews");
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


    public ProgressDialog dialog = null;

    //  private void ProgressDialogo(String title, String message) {
    private void ProgressDialogo(String title, String message, String buttonText) {
        Log.d(TAG, " ProgressDialogo");
        dialog = new ProgressDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);

        dialog.setButton(buttonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// Use either finish() or return() to either close the activity or just the dialog
                dialog.dismiss();
                //  return;
            }
        });
        dialog.show();
    }

    /*
        private void ProgressDialogo(String title, String message) {
            final ProgressDialog pd = new ProgressDialog(this);

            // Set progress dialog style spinner
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

          *//*  // Set the progress dialog title and message
        pd.setTitle("Aguarde");
        pd.setMessage("acessando...");*//*

        // Set the progress dialog background color
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

        pd.setIndeterminate(false);

        // Finally, show the progress dialog
        pd.show();
    }*/
/*
    // intent qe define o conteudo que sera compartilhado
    private Intent getDefaultItent() {
        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text*/
/*");
        it.putExtra(Intent.EXTRA_TEXT, "Texto para compartilhar");
        return it;
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, " onCreateOptionsMenu");
        // infla o menu com os botoes da action bar
        //   getMenuInflater().inflate(R.menu.menu_main, menu);

        ///* pesquisa
/*        MenuItem item = menu.findItem(R.id.action_search);
        SearchView pesquisa = (SearchView) MenuItemCompat.getActionView(item);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
           pesquisa.setOnQueryTextListener(onSearch());
        }

        // compartilhar
       MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        share.setShareIntent(getDefaultItent());
        */
        return true;
    }


/*
    // pequisa
    private SearchView.OnQueryTextListener onSearch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    toast("Buscar o texto: " + query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // novo texto
                    return false;
                }
            };
        }
        return null;
    }
*/

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, " onOptionsItemSelected");
        int id = item.getItemId();

        if (id == R.id.action_search) {
            toast("Pesquisar");
            return true;
        } else if (id == R.id.action_refresh) {
            toast("Atualizar");
            return true;
        } else if (id == R.id.action_settings) {
            toast("Configurar");
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_versao) {
            toast("Versão " + versaoinfo);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, " onResume");
        if (getIntent().getBooleanExtra("Exite", false)) {
//
        }

    }

    public void exibirMensagemRodape(View v) {
        // ab.make(ln, "Olá", Snackbar.LENGTH_SHORT).show();
    }


  /*  public void abreTela() {
        //        Snackbar.make(ln, "Olá", Snackbar.LENGTH_SHORT).show();
        // Toast.makeText(this, "Autenticado com sucesso!", Toast.LENGTH_LONG).show();
        Toast.makeText(MainActivity.this, resources.getString(R.string.login_auth_ok), Toast.LENGTH_SHORT).show();

        ProgressDialogo("Aguarde", "Processando....", "Ok");


        //retorna lista inicial
        Intent it = new Intent(MainActivity.this, MainActivityLogin.class);
        startActivity(it);
        finish();
    }*/

    public boolean validaAcesso(String user, String pass) {

        UsuarioDao dao = new UsuarioDao();

        boolean autenticarUser = dao.Autenticar(user.trim(), pass.trim());


        if (dao == null) {
            Toast.makeText(this, "Erro!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (autenticarUser) {
            Toast.makeText(this, "Autenticado!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void showSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                //retorna lista inicial
                Intent it = new Intent(MainActivity.this, MainActivityLogin.class);
                startActivityForResult(it, 1);
               // finish();
            }
        });
    }


    private void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Singleton.timeOut) {
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(MainActivity.this, "Time out!", Toast.LENGTH_SHORT).show();
                    ConnectivityServices.alertDialog(MainActivity.this, "Time out", resources.getString(R.string.error_timeout));
                }
                if (Singleton.httpException) {
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(MainActivity.this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
                    ConnectivityServices.alertDialog(MainActivity.this, "Conectividade", resources.getString(R.string.error_conexao_indisponivel));
                }
                if (Singleton.genericException) {
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(MainActivity.this, "Tente mais tarde!", Toast.LENGTH_SHORT).show();
                    ConnectivityServices.alertDialog(MainActivity.this, "Informação", resources.getString(R.string.error_generico));
                }
            }
        });
    }


    private void showErrorThreads(final int messageId) {
        final Activity activity = weakActivity.get();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String message = activity.getString(messageId);
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // Verfica se o requestCode é o mesmo que foi passado
        if(requestCode==1)
        {
            clearFields(edtUser, edtPassword);
            toast("Acesso finalizado!");
        }
    }
}