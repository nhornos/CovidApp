package Clases;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class cParametros {
        public static String getParamsString(Map<String, String> params) {
            StringBuilder result = new StringBuilder();

            try{
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    result.append("&");
                }
            } catch (UnsupportedEncodingException e) {
                Log.i("getParamsString()", e.getMessage());
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }

        public static void clearCache(Context context)
        {
            SharedPreferences prefs = context.getSharedPreferences("RespirApp", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefs.edit();
            prefEditor.clear();
            prefEditor.commit();
        }

        public static void addCache(Context context, String key, String value)
        {
            SharedPreferences prefs = context.getSharedPreferences("RespirApp", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefs.edit();
//            prefEditor.clear();
            prefEditor.putString(key, value);
            prefEditor.commit();
        }

        public static String getCache(Context context, String key)
        {
            SharedPreferences prefs = context.getSharedPreferences("RespirApp", Context.MODE_PRIVATE);
            return prefs.getString(key, "");
        }

}
