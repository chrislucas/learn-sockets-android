package wiselabs.com.br.rest.http.request;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wiselabs.com.br.rest.AsyncResponse;
import wiselabs.com.br.rest.utils.device.UtilsNetworking;
/**
 * Created by C.Lucas on 09/10/2015.
 *
 * https://apps.twitter.com/app/8622233/keys
 */
public class TwitterAuthentication extends AsyncTask<Void, Void, Void> {

    private AsyncResponse asyncResponse;
    private String token;
    private Context context;

    public AsyncResponse getAsyncResponse() {
        return asyncResponse;
    }

    public void setAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private static final String KEY = "ocEL25NbST766M5SYZlSLbcnU"
            ,SECRET = "xmoL0nS4YGMq6IHPawe7suKJTq3lbCo9RU5mZvlVE5hZ2ExO11"
            ,URL_AUTHENTICATION = "https://api.twitter.com/oauth2/token"
            ,URL = "http://wgx.com.br/demo/serrat/api/index.php/usuario?token=wgx@_2k15!&format=json"
            ,URL_TOKEN = "https://api.twitter.com/oauth/request_token"
            ,URL_AUTHORIZE = "https://api.twitter.com/oauth/authorize"
            ,URL_ACCESS = "https://api.twitter.com/oauth/access_token";

    public TwitterAuthentication(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param aVoid The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String token = this.getToken();
        this.getAsyncResponse().getResponse(token);
        return;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            boolean isConnected = UtilsNetworking.testConnection(getContext());
            if(!isConnected) {
                UtilsNetworking.openSettings(getContext(), Settings.ACTION_WIFI_SETTINGS);
            }

            Map<String, String> pair = new HashMap<>();
            pair.put("grant_type", "client_credentials");
            String encode = generateEncodedKey();
            String result = HttpRequest.post(URL_AUTHENTICATION).authorization("Basic "+encode).form(pair).body();
            //Gson gson = new Gson();
            //String json = gson.toJson(result);
            JSONObject jsonToken = new JSONObject(result);
            String token = jsonToken.getString("access_token");
            this.setToken(token);
        } catch(Exception e) {
            Log.e("EXCEPTION_DO_BACKGROUND", e.getMessage());
        }
        return null;
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
