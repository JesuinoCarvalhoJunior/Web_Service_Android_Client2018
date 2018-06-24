package com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Junior_Carvalho on 15/10/2015.
 */
public class MensagemHelper extends Activity {


    public static ProgressDialog dialog = null;
    static Context ct;
    private TextView versaoinfo;

    public static void ProgressDialogo(String title, String message) {
        dialog = new ProgressDialog(ct);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setButton("Ok", new DialogInterface.OnClickListener() {
            //  dialog.setButton(buttonText,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// Use either finish() or return() to either close the activity or just the dialog
                dialog.dismiss();
                //  return;
            }
        });
        dialog.show();
    }

    /* Lê a versão do app */
    public static String getVersionName(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        String packageName = activity.getPackageName();
        String versionName;
        try {
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            versionName = info.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            versionName = "N/A";
        }
        return versionName;
    }

    /**
     * Limpa os ícones e as mensagens de erro dos campos desejados
     *
     * @param editTexts lista de campos do tipo EditText
     */
    public static void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }

    public static void clearFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setText("");
        }
    }
    public static void showMessage(final Activity activity, final String title, final String body, final String okLabel)
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(activity)
                        .setTitle(title)
                        .setMessage(body)
                        .setCancelable(false)
                        .setPositiveButton(okLabel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }

}