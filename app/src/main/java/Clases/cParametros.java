package Clases;

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
}
