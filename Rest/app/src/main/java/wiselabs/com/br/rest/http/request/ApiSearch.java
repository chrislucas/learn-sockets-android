package wiselabs.com.br.rest.http.request;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wiselabs.com.br.rest.AsyncResponse;
import wiselabs.com.br.rest.User;

import wiselabs.com.br.rest.utils.device.UtilsNetworking;

/**
 * Created by christoffer on 10/13/15.
 */
public class ApiSearch extends AsyncTask<String, Void, List<User>> {

    private AsyncResponse asyncResponse;
    private Context context;
    private ProgressDialog pDialog;

    public Context getContext() {
        return context;
    }

    public AsyncResponse getAsyncResponse() {
        return asyncResponse;
    }

    public void setAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }


    public ApiSearch(Context context) {
        this.context = context;
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(getContext() != null)
            this.pDialog = new ProgressDialog(getContext());
        this.pDialog.show();
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param strings The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);
        if(users != null) {
            this.getAsyncResponse().getResponse(users);
        }
        this.pDialog.dismiss();
        return;
    }

    /**
     * Runs on the UI thread after {@link #publishProgress} is invoked.
     * The specified values are the values passed to {@link #publishProgress}.
     *
     * @param values The values indicating progress.
     * @see #publishProgress
     * @see #doInBackground
     */
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
    protected List<User> doInBackground(String... urls) {
        List<User> list = new ArrayList<>();
        try {
            boolean isConnected = UtilsNetworking.testConnection(getContext());
            if(!isConnected) {
                UtilsNetworking.openSettings(getContext(), Settings.ACTION_WIFI_SETTINGS);
            }

            String result = HttpRequest.get(urls[0]).body();
            Gson gson = new Gson();
            String json = gson.toJson(result);
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String login = object.getString("login");
                String name = object.getString("nome");
                String pwd = object.getString("senha");
                int idUser = object.getInt("id_usuario");
                String imei1 = object.getString("imei_1");
                String imei2 = object.getString("imei_2");

                User user = new User();
                user.setId(idUser);
                user.setLogin(login);
                user.setName(name);
                user.setImei(new String[] {imei1, imei2});
                list.add(user);
            }
        } catch(Exception e) {
            Log.e("EXCEPTION_BACKGROUND_TASK ".concat(getContext().getPackageName()), e.getMessage(), e);
        }
        return list;
    }

}
