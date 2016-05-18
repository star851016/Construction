package temp.chiu.com.clinico.global;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    // Preferences file
    private static final String PREFERENCES_FILE = "prefs";

    private static final String TOKEN = "token";

    public static void saveToken(Context context, String data) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN, data);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN, "");
    }
}
