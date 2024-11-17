import android.content.Context;
import android.content.SharedPreferences;

import org.junit.runner.manipulation.Ordering;

public class fuction {



    public static void saveAccessToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences("Access_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("KEY_ACCESS_TOKEN", token);
        editor.apply(); // Save asynchronously
    }

    public static void clearAccessToken(Context context) {
       SharedPreferences prefs = context.getSharedPreferences("Access_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
       editor.remove("KEY_ACCESS_TOKEN");
      editor.apply();
    }

}
