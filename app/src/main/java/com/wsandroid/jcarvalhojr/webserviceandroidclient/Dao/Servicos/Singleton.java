package com.wsandroid.jcarvalhojr.webserviceandroidclient.Dao.Servicos;

/**
 * Created by JCarvalhoJr on 07/03/2018.
 */

public class Singleton {

    private static Singleton instance = new Singleton();
    // ...
    // defina suas variáveis do Login

    public static boolean timeOut = false;
    public static boolean httpException=false;
    public static boolean  genericException=false;

    private Singleton() {
    }

    public static Singleton getInstance()  {
        return instance;
    }
    // getters e setters para variáveis do Login (se for usar com threads use: synchronized)

}

