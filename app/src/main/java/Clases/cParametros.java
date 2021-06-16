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
            String resultString = "";
                result.append("{");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    result.append("\"");
                    result.append(entry.getKey());
                    result.append("\"");
                    result.append(":");
                    result.append("\"");
                    result.append(entry.getValue());
                    result.append("\"");
                    result.append(",");
                }
                resultString = result.toString();
                resultString = resultString.length() > 0
                        ? resultString.substring(0, resultString.length() - 1)
                        : resultString;
                resultString += "}";

//            String resultString = result.toString();
//            return resultString.length() > 0
//                    ? resultString.substring(0, resultString.length() - 1)
//                    : resultString;
            return resultString;
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
