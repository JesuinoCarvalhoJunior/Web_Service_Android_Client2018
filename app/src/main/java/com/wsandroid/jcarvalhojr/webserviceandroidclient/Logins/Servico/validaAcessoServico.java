package com.wsandroid.jcarvalhojr.webserviceandroidclient.Logins.Servico;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.UsuarioDao;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio.Usuario;

/**
 * Created by JCarvalhoJr on 04/03/2018.
 */

public class validaAcessoServico extends AppCompatActivity {


    public static boolean validaAcesso(final Context context, final String user, final String pass) {

        boolean sucesso;

        UsuarioDao dao = new UsuarioDao();

        if (dao.Autenticar(user.trim(), pass.trim())) {
            sucesso = true;
        } else {
            sucesso = false;
        }
        return sucesso;
    }


    public static boolean atualizaDadosServico(final Context context, final String nome, final String idade) {

        UsuarioDao dao = new UsuarioDao();

        boolean sucesso = dao.atualizarUsuario(
                (new Usuario(0, nome, Integer.parseInt(idade))
                )
        );

        if (sucesso) {
            sucesso = true;
        } else {
            sucesso = false;
        }
        return sucesso;
    }

}