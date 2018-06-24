package com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;

/**
 * Created by JCarvalhoJr on 12/03/2018.
 */

public class getBitmapFactoryHelper {

    public static Bitmap getBitmapFactory(Context context, int icone) {
        // Create a Bitmap image starting from the star.png into the "/res/drawable/" directory:
        Bitmap myBitmap = BitmapFactory.decodeResource(context.getResources(), icone);
        return myBitmap;

    }
    /* exemplo uso
     Bitmap icon;
     icon = getBitmapFactory(context, R.drawable.ic_action_remove);
     */

}
