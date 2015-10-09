package wiselabs.com.br.rest.http.request;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by C.Lucas on 09/10/2015.
 *
 * https://apps.twitter.com/app/8622233/keys
 */
public class TwitterAuthentication extends AsyncTask<Void, Void, String> {

    private static String KEY = "ocEL25NbST766M5SYZlSLbcnU"
            ,SECRET = "xmoL0nS4YGMq6IHPawe7suKJTq3lbCo9RU5mZvlVE5hZ2ExO11"
            ,URL_AUTHENTICATION = "https://api.twitter.com/oauth2/token"
            ,URL_TOKEN = "https://api.twitter.com/oauth/request_token"
            ,URL_AUTHORIZE = "https://api.twitter.com/oauth/authorize"
            ,URL_ACCESS = "https://api.twitter.com/oauth/access_token";

    public TwitterAuthentication() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String ... result) {

        try {
            Map<String, String> pair = new HashMap<>();
            pair.put("grant_type", "client_credentials");
            String result = HttpRequest.post(URL_AUTHENTICATION)
                    .authorization("BASIC "+generateEncodedKey())
                    .form(pair)
                    .body();
            Gson gson = new Gson();
            String json = gson.toJson(result);
            JSONObject jsonObj = new JSONObject(result);
            result[0] = jsonObj.getString("access_token");
        } catch(Exception e) {
            Log.e("EXCEPTION_DO_BACKGROUND", e.getMessage());
        }

        return result[0];
    }

    private String generateEncodedKey() {
        String token = KEY + ":" + SECRET;
        String encode = Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
        return encode;
    }

    private class Pair<K, V> implements Map.Entry<K, V> {
        private K k;
        private V v;

        public Pair(K k, V v) {
            this.k = k;
            this.v = v;
        }

        /**
         * Returns the key.
         *
         * @return the key
         */
        @Override
        public K getKey() {
            return null;
        }

        /**
         * Returns the value.
         *
         * @return the value
         */
        @Override
        public V getValue() {
            return null;
        }

        /**
         * Sets the value of this entry to the specified value, replacing any
         * existing value.
         *
         * @param object the new value to set.
         * @return object the replaced value of this entry.
         */
        @Override
        public V setValue(V object) {
            return null;
        }
    }
}
