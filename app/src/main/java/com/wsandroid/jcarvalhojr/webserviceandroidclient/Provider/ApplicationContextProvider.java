package com.wsandroid.jcarvalhojr.webserviceandroidclient.Provider;

import android.app.Application;
import android.content.Context;

/**
 * Created by JCarvalhoJr on 12/03/2018.
 * Classe fornecerá o contexto onde estiver no aplicativo.
 */

public class ApplicationContextProvider extends Application {

    /**
     * Mantém uma referência do contexto do aplicativo
     */
    private static Context context;

    /**
     * Retorna o contexto do aplicativo
     *
     * @return contexto de aplicação
     */
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}