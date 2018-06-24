package com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao;

import android.util.Log;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.Interfaces.IUsuarioDao;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.Servicos.ServicosBase;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.Servicos.Singleton;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio.Usuario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Junior_Carvalho on 19/09/2015.
 */
public class UsuarioDao extends ServicosBase implements IUsuarioDao {
//public class UsuarioDao  implements IUsuarioDao {

    //  public class UsuarioDao  {

    //179.252.9.149
    //  private static final String URL = "http://179.252.9.149:8080/WebServiceEclipseAndroid/services/UsuarioDao?wsdl";

    //    private static final String URL = "http://192.168.1.2:8080/WebServiceEclipseAndroid/services/UsuarioDao?wsdl";
//    //private static final String NAMESPACE = "http://android.webservice.com.br";
//    private static final String NAMESPACE = "http://Dao.android.webservice.com.br";
//
//
//    private static final String INSERIR = "inserirUsuario";
//    private static final String EXCLUIR = "excluirUsuario";
//    private static final String ATUALIZAR = "atualizarUsuario";
//    private static final String BUSCAR_TODOS_USUARIOS = "buscarTodosUsuairos";
//    private static final String BUSCAR_POR_ID = "buscaUsuarioPorId";
//
//
//
    private String usuario;
    private String password;

   public  boolean timeOutException=false, httpException=false, genericException=false;

    public boolean inserirUsuario(Usuario usuario) {

        SoapObject inserirUsuario = new SoapObject(NAMESPACE, INSERIR);
        SoapObject user = new SoapObject(NAMESPACE, "usuario");

        user.addProperty("id", usuario.getId());
        user.addProperty("idade", usuario.getIdade());
        user.addProperty("nome", usuario.getNome());
        //  user.addProperty("login", usuario.getLogin());
        //  user.addProperty("senha", usuario.getSenha());

        inserirUsuario.addSoapObject(user);

        // adicionar as propiedades no envolope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(inserirUsuario);
        envelope.implicitTypes = true;

        // envia o envelope
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + INSERIR, envelope);
            // cast
            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            // o webserver retorna true ou false ..
            // e SoapPrimitive é do tipo String .. entao
            // converter resposta de String para Boolean
            return Boolean.parseBoolean(resposta.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return false;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return false;
        }
    }

    //
    public boolean atualizarUsuario(Usuario usuario) {

        boolean retorno = false;

        SoapObject atualizarUsuario = new SoapObject(NAMESPACE, ATUALIZAR);

        SoapObject user = new SoapObject(NAMESPACE, "usuario");

        user.addProperty("id", usuario.getId());
        user.addProperty("idade", usuario.getIdade());
        user.addProperty("nome", usuario.getNome());

        atualizarUsuario.addSoapObject(user);

        // adicionar as propiedades no envolope

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(atualizarUsuario);
        envelope.implicitTypes = true;
        //   envelope.bodyOut = atualizarUsuario;


        // envia o envelope
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + ATUALIZAR, envelope);
            // cast
            //
            //
            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            // o webserver retorna true ou false ..
            // e SoapPrimitive é do tipo String .. entao
            // converter resposta de String para Boolean
            return retorno = Boolean.parseBoolean(resposta.toString());

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            Singleton.timeOut = timeOutException=true;
            e.printStackTrace();
        } catch (UnknownHostException e) {
            Singleton.httpException =  httpException=true;
            e.printStackTrace();
        } catch (Exception e) {
            Singleton.genericException =  genericException=true;
            e.printStackTrace();
        }
        return retorno;
    }


    public boolean excluirUsuario(Usuario usuario) {

        SoapObject deletarUsuario = new SoapObject(NAMESPACE, EXCLUIR);

        SoapObject user = new SoapObject(NAMESPACE, "usuario");

        user.addProperty("id", usuario.getId());
        user.addProperty("idade", usuario.getIdade());
        user.addProperty("nome", usuario.getNome());
        //   user.addProperty("login", usuario.getLogin());
        //   user.addProperty("senha", usuario.getSenha());

        deletarUsuario.addSoapObject(user);

        // adicionar as propiedades no emvolope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(deletarUsuario);
        envelope.implicitTypes = true;

        // envia o envelope
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + EXCLUIR, envelope);
            // cast
            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            // o webserver retorna true ou false ..
            // e SoapPrimitive é do tipo String .. entao
            // converter resposta de String para Boolean
            return Boolean.parseBoolean(resposta.toString());

        } catch (IOException e) {
            Log.e("Register SE ex", e.toString());
            e.printStackTrace();
            return false;

        } catch (XmlPullParserException e) {
            Log.e("Register ex", e.toString());
            e.printStackTrace();
            return false;
        }
    }

    //
    public ArrayList<Usuario> buscarTodosUsuairos() {

        ArrayList<Usuario> lista = new ArrayList<Usuario>();

        SoapObject buscarUsuario = new SoapObject(NAMESPACE, BUSCAR_TODOS_USUARIOS);
        SoapSerializationEnvelope envelope;
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscarUsuario);
        envelope.implicitTypes = true;
        // envia o envelope
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + BUSCAR_TODOS_USUARIOS, envelope);
            // lista de usuarios           // cast
            Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();

            // varre o vetor
            for (SoapObject soapObj : resposta) {
                Usuario user = new Usuario();
                user.setId(Integer.parseInt(soapObj.getProperty("id").toString()));
                user.setNome(soapObj.getProperty("nome").toString());
                user.setIdade(Integer.parseInt(soapObj.getProperty("idade").toString()));

                lista.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        return lista;
    }

    //
    public Usuario buscaUsuarioPorId(int id) {

        Usuario user = null;

        SoapObject buscarUsuarioId = new SoapObject(NAMESPACE, BUSCAR_POR_ID);
        buscarUsuarioId.addProperty("id", id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(buscarUsuarioId);
        envelope.implicitTypes = true;

        try {
            // envia o envelope
            HttpTransportSE http = new HttpTransportSE(URL, 5000);
            http.call("urn:" + BUSCAR_POR_ID, envelope);
            // cast
            SoapObject resposta = (SoapObject) envelope.getResponse();

            user = new Usuario();
            user.setId(Integer.parseInt(resposta.getProperty("id").toString()));
            user.setNome(resposta.getProperty("nome").toString());
            user.setIdade(Integer.parseInt(resposta.getProperty("idade").toString()));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    // sobrecarga
    public boolean excluirUsuario(int id) {
        return excluirUsuario(new Usuario(id, "", 0, "", ""));
    }


    //public Usuario Autenticar(String login, String senha) {
    public boolean Autenticar(String login, String senha) {

        Boolean retorno = false;
        // String retorno = "ok";

        SoapObject autenticar = new SoapObject(NAMESPACE, AUTENTICAR);
        autenticar.addProperty("login", login);
        autenticar.addProperty("senha", senha);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(autenticar);
        envelope.implicitTypes = true;
        // envia o envelope
        //    HttpTransportSE http = new HttpTransportSE(URL, 5000);

        try {
            HttpTransportSE http = new HttpTransportSE(URL, 5000);
            // envia o envelope
            http.call("urn:" + AUTENTICAR, envelope);

            // cast
            // o webserver retorna true ou false ..
            // e SoapPrimitive é do tipo String .. entao
            // converter resposta de String para Boolean

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
            // cast
            //  SoapObject resposta = (SoapObject) envelope.getResponse();
            return retorno = Boolean.parseBoolean(resposta.toString());

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            Singleton.timeOut = timeOutException=true;
            e.printStackTrace();
        } catch (UnknownHostException e) {
            Singleton.httpException =  httpException=true;
            e.printStackTrace();
        } catch (Exception e) {
            Singleton.genericException =  genericException=true;
            e.printStackTrace();
        }
        return retorno;
    }

}






/*

    @Override
    public Usuario Autenticar(String login, String senha) {

        Usuario user = null;

        String retorno = "ok";

        SoapObject autenticar = new SoapObject(NAMESPACE, AUTENTICAR);
        autenticar.addProperty("login", login);
        autenticar.addProperty("senha", senha);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(autenticar);
        envelope.implicitTypes = true;


        try {
            // envia o envelope
            HttpTransportSE http = new HttpTransportSE(URL, 5000);
            http.call("urn:" + AUTENTICAR, envelope);

            // cast
            SoapObject resposta = (SoapObject) envelope.getResponse();

            user = new Usuario();

            user.setId(Integer.parseInt(resposta.getProperty("id").toString()));
           user.setNome(resposta.getProperty("nome").toString());
           user.setIdade(Integer.parseInt(resposta.getProperty("idade").toString()));
            user.setLogin(resposta.getProperty("login").toString());
            user.setSenha(resposta.getProperty("senha").toString());


        } catch (IOException e) {
            e.printStackTrace();
            retorno = "ok";
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }*/
//}
