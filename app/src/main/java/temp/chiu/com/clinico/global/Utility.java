package temp.chiu.com.clinico.global;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import temp.chiu.com.clinico.R;

public final class Utility {
    private final static String TAG = "Utility";

    public static void showCommonDialog(Context context, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage(msg)
                .setPositiveButton(R.string.g_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }

    public static void showCommonDialog(Context context, String title, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.g_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }

    public static ProgressDialog newCommonProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setCancelable(false);

        return progressDialog;
    }

}
