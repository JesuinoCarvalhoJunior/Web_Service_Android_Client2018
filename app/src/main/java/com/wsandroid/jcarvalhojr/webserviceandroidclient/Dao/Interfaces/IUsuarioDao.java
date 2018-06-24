package com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.Interfaces;


import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio.Usuario;

import java.util.ArrayList;

/**
 * Created by Junior_Carvalho on 04/10/2015.
 */
public interface IUsuarioDao {

    boolean inserirUsuario(Usuario usuario);

    //
    boolean atualizarUsuario(Usuario usuario);

    //
    boolean excluirUsuario(Usuario usuario);

    //
    ArrayList<Usuario> buscarTodosUsuairos();

    //
    Usuario buscaUsuarioPorId(int id);

    // sobrecarga
    boolean excluirUsuario(int id);
// **
    //     Usuario Autenticar(String login, String senha);

    boolean Autenticar(String login, String senha);

   // boolean validaAcesso(String user, String pass);

}
