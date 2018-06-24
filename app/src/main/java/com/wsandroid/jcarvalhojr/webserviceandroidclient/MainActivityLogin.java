package com.wsandroid.jcarvalhojr.webserviceandroidclient;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Swipe.SwipeController;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Swipe.SwipeControllerActions;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Adapter.UsuarioAdapter;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.Servicos.Singleton;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.UsuarioDao;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio.Usuario;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpersconnectivity.ConnectivityServices;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Sincronizar.ItemClickListener;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Swipe.SwipeControllerActions;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Swipe.SwipeHelper;
import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;

import java.util.ArrayList;

public class MainActivityLogin extends AppCompatActivity implements ItemClickListener {

    SwipeController swipeController = null;
    //  private static boolean alreadyOpen = false;
    protected RecyclerView recyclerViewLista;
    //   ArrayList<Usuario> lista;
    //   ArrayAdapter<Usuario> adpUsuario;
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    //
    //var global
    //   private ListView lvUsuario;
    private ArrayList<Usuario> listaUsuarios;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView.LayoutManager mLayoutManager;

    private UsuarioAdapter usuarioAdapter;

    private boolean inMemory = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);


/*        inicilizarRecyclerViewLista();
        inicializaUsuarioAdapter();*/
        //inicilizarRecyclerViewLista();

        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //  UsuarioDao dao = new UsuarioDao();
        //usuarios = dao.buscarTodosUsuairos();
     //   inicializaUsuarioAdapter();

        setupRecyclerView();
        if (savedInstanceState == null) {
            inMemory = false;
            atualizaListaTask();
          /*       UsuarioDao dao = new UsuarioDao();
            listaUsuarios = dao.buscarTodosUsuairos();*/

            Log.d("ExemploWebService", "onReume --");
        } else {
            inMemory = true;
            listaUsuarios = savedInstanceState.getParcelableArrayList("listaUsuario");
        }

   //     inicializaUsuarioAdapter();

        setupRecyclerView();


/*        inicializaUsuarioAdapter();
        inicializaSwiper();*/


        // ArrayList<Usuario> lista;

        //  lista = new ArrayList<>();

    /*    if (savedInstanceState == null) {
            inMemory = false;
            UsuarioDao dao = new UsuarioDao();
            usuarios = dao.buscarTodosUsuairos();
            Log.d("ExemploWebService", "onReume --");
        } else {
            inMemory = true;
            usuarios = savedInstanceState.getParcelableArrayList("listaUsuario");
        }
*/

        //  lvUsuario = findViewById(R.id.lstvUsuarios);
        //  adpUsuario = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, lista);
        //  lvUsuario.setAdapter(adpUsuario);

        //creating recyclerview adapter
        //     UsuarioAdapter adapter = new UsuarioAdapter(this, usuarios);

        //setting adapter to recyclerview
        //     recyclerView.setAdapter(adapter);


        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        //  UsuarioDao dao = new UsuarioDao();
        //Usuario user = dao.buscaUsuarioPorId(29);
        //Log.d("ExemploWebService", user.toString());

        //  Usuario user = dao.Autenticar("abc","abc");
        // Log.d("ExemploWebService", user.getId() + user.getNome() + user.getLogin() + user.getNome());

        // Swipe to Refresh
        swipeLayout = findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //verifica conexao com intenet
                if (ConnectivityServices.isNetworkAvailable(MainActivityLogin.this)) {
                    // Atualiza ao fazer o gesto Pull to Refresh
            /*        UsuarioDao dao = new UsuarioDao();
                    ArrayList<Usuario> usuarios = dao.buscarTodosUsuairos();
                    Log.d("Pull refresh", "<< Pull refresh >>");
                    usuarioAdapter = new UsuarioAdapter(usuarios);
                    recyclerViewLista.setAdapter(usuarioAdapter);
                    swipeLayout.setRefreshing(false);*/
                    Log.d("Pull refresh", "<< Pull refresh >>");
                    inicilizarRecyclerViewLista();
                    atualizaListaTask();
                    inicializaUsuarioAdapter();
                 //   inicializaSwiper();
                    swipeLayout.setRefreshing(false);
                } else {
                    Toast.makeText(MainActivityLogin.this, R.string.error_conexao_indisponivel, Toast.LENGTH_LONG).show();
                    swipeLayout.setRefreshing(false);
                }
            }
        });
        swipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);


        //  Button btAbreCadastro = findViewById(R.id.btnAbreTelaCadastro);

        //  lvUsuario = findViewById(R.id.lstvUsuarios);
        // new Sincronismo().execute();

        // click botao
   /*     btAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivityLogin.this, CadastroActivity.class);
                startActivity(it);
            }
        });*/


       /* lvUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView adapter, View view, int posicao, long id) {

                Usuario user = (Usuario) adapter.getItemAtPosition(posicao);
                long codigo = user.getId();
                //Intent it = new Intent(getBaseContext(), Detalhes_Activity.class);
                Intent it = new Intent(MainActivityLogin.this, Detalhes_Activity.class);
                //     it.putExtra("idCliente", id);
                it.putExtra("Codigo", codigo);
                startActivityForResult(it, 1);
                startActivity(it);
                //finish();
            }
        });*/


    }

    private void inicializaSwiper() {
    /*
    efeito deslizada da esquerda para direita
    para remover um item da lista
     */
        //  ItemTouchHelper.Callback callback = new SwipeHelper(usuarioAdapter, this);
        ItemTouchHelper.Callback callback = new SwipeHelper(usuarioAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerViewLista);
    }

    private void inicializaUsuarioAdapter() {
        usuarioAdapter = new UsuarioAdapter(listaUsuarios);
      //  recyclerViewLista.setAdapter(usuarioAdapter);
        usuarioAdapter.setClickListener(this);
    }

    private void inicilizarRecyclerViewLista() {
        recyclerViewLista = (RecyclerView) findViewById(R.id.recyclerViewLista);
        recyclerViewLista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerViewLista.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
      //  dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.dividerdrawable));
        recyclerViewLista.addItemDecoration(dividerItemDecoration);
        recyclerViewLista.setLayoutManager(linearLayoutManager);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().getBooleanExtra("Exite", false)) {
//
        }
        if (!inMemory) {//&& (listaUsuarios.size() <= 0)) {
            atualizaListaTask();
       //     inicializaUsuarioAdapter();
        //    inicializaSwiper();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // infla o menu com os botoes da action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // pesquisa
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView pesquisa = (SearchView) MenuItemCompat.getActionView(item);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            pesquisa.setOnQueryTextListener(onSearch());
        }
       /* // compartilhar
       MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        share.setShareIntent(getDefaultItent());
        */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, "Selecionou em configuração", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (item.getItemId() == R.id.action_detalhes) {
            Toast.makeText(this, "Selecionou dealhes", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // pequisa
    private SearchView.OnQueryTextListener onSearch() {
        //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
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
        //}
    }

    // intent qe define o conteudo que sera compartilhado
    private Intent getDefaultItent() {
        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/*");
        it.putExtra(Intent.EXTRA_TEXT, "Texto para compartilhar");
        return it;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("listaUsuario", listaUsuarios);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void finish() {
        Intent intente = new Intent();
        setResult(1, intente);
        super.finish();
    }


    @Override
    public void onClick(View view, int position) {

        final Usuario user = listaUsuarios.get(position);
        long codigo = user.getId();
        Intent it = new Intent(MainActivityLogin.this, Detalhes_Activity.class);
        it.putExtra("Codigo", codigo);
        startActivityForResult(it, 1);
        startActivity(it);
        //finish();
    }

    public void atualizaListaTask() {
        if (ConnectivityServices.isNetworkAvailable(MainActivityLogin.this)) {
            MainActivityLogin.atualizaListaTask task = new MainActivityLogin.atualizaListaTask();
            task.execute();
        } else {
            Toast.makeText(MainActivityLogin.this, R.string.error_conexao_indisponivel, Toast.LENGTH_LONG).show();
        }
    }


    public class atualizaListaTask extends AsyncTask<Void, Void, ArrayList<Usuario>> {

        @Override
        protected void onPreExecute() {
            swipeLayout = findViewById(R.id.swipeToRefresh);
        }

        @Override
        protected ArrayList<Usuario> doInBackground(Void... voids) {
            inMemory = false;
            UsuarioDao dao = new UsuarioDao();
            listaUsuarios = dao.buscarTodosUsuairos();
            Log.d("ExemploWebService", "onReume --");
            return listaUsuarios;
        }

        @Override
        protected void onPostExecute(ArrayList<Usuario> listaObtidaUsuarios) {
            swipeLayout.setRefreshing(false);
            if (listaObtidaUsuarios.size() > 0) {
           //     usuarioAdapter = new UsuarioAdapter(listaObtidaUsuarios);
            //  recyclerViewLista.setAdapter(usuarioAdapter);
            //  inicializaUsuarioAdapter();
              //  inicializaUsuarioAdapter();
               // inicializaSwiper();
                setupRecyclerView();
                Toast.makeText(MainActivityLogin.this, "Total de registros: " + listaUsuarios.size(), Toast.LENGTH_LONG).show();
            } else {
                if (Singleton.timeOut) {
                    //  progressBar.setVisibility(View.GONE);
                    ConnectivityServices.alertDialog(MainActivityLogin.this, "Time out", getResources().getString(R.string.error_timeout));
                }
                if (Singleton.httpException) {
                    //   progressBar.setVisibility(View.GONE);
                    ConnectivityServices.alertDialog(MainActivityLogin.this, "Conectividade", getResources().getString(R.string.error_conexao_indisponivel));
                }
                if (Singleton.genericException) {
                    //   progressBar.setVisibility(View.GONE);
                    ConnectivityServices.alertDialog(MainActivityLogin.this, "Informação", getResources().getString(R.string.error_generico));
                }
            }
        }
    }

    private void setupRecyclerView() {
        recyclerViewLista = (RecyclerView) findViewById(R.id.recyclerViewLista);
        usuarioAdapter = new UsuarioAdapter(listaUsuarios);
        //  recyclerViewLista.setAdapter(usuarioAdapter);
        usuarioAdapter.setClickListener(this);

        recyclerViewLista.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewLista.setAdapter(usuarioAdapter);

       // recyclerViewLista = (RecyclerView) findViewById(R.id.recyclerViewLista);
        recyclerViewLista.setHasFixedSize(true);

       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerViewLista.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

       // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        //  dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.dividerdrawable));
      //  recyclerViewLista.addItemDecoration(dividerItemDecoration);
      //  recyclerViewLista.setLayoutManager(linearLayoutManager);
      //  recyclerViewLista.setAdapter(usuarioAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                usuarioAdapter.removeItem(position);
                usuarioAdapter.notifyItemRemoved(position);
                usuarioAdapter.notifyItemRangeChanged(position, usuarioAdapter.getItemCount());
            }
            @Override
            public void onLeftClicked(int position) {
                final Usuario user = listaUsuarios.get(position);
                long codigo = user.getId();
                Intent it = new Intent(MainActivityLogin.this, Detalhes_Activity.class);
                it.putExtra("Codigo", codigo);
                startActivityForResult(it, 1);
                startActivity(it);
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerViewLista);

        recyclerViewLista.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

}

//    boolean resultado = dao.inserirUsuario(new Usuario(0,"Lina", 34));
//      Log.d("ExemploWebService", resultado + "");

//      ArrayList<Usuario> lista = dao.buscarTodosUsuairos();
//    Log.d("ExemploWebService", lista.toString());

//Usuario user = dao.buscaUsuarioPorId(5);
//        Log.d("ExemploWebService", user.toString());

//   boolean retorno = dao.atualizarUsuario(new Usuario(4, "Lina 1", 35));
//   Log.d("ExemploWebService", retorno + "");

//  boolean res = dao.excluirUsuario(6);
// Log.d("ExemploWebService", res + "");


//      ArrayList<Usuario> lista = dao.buscarTodosUsuairos();
//    Log.d("ExemploWebService", lista.toString());
