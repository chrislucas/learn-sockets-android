package wiselabs.com.br.rest.http.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import wiselabs.com.br.rest.R;

/**
 * Created by christoffer on 10/9/15.
 */
public class TwitterTask extends AsyncTask<String, Void, String[]> {

    private ProgressDialog pDialog;

    private final Context context;          // activity que chamous Essa AsyncTask
    private final String accessToken;
    //private static final String ACC_TKN = "3401147921-FpjLwbGvAhIK3pYSeptRldPf5yNm8hYiN9Q3j9u";
    //private static final String ACC_TKN_SCT = "o1R7BHxVF7ka7Hl345Cw3opHJY40nqcqjm3phTopoKyxh";
    private static final String []  URL_SEARCH = {
      "https://api.twitter.com/1.1/search/tweets.json?q="
    };


    public Context getContext() {
        return this.context;
    }

    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
     */
    public TwitterTask(Context context, String accessToken) {
        super();
        this.context = context;
        this.accessToken = accessToken;
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
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
        if(result != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, result);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.activity_main, null);
            if(layout != null) {
                ListView list = (ListView) layout.findViewById(R.id.result_list);
                list.setAdapter(adapter);
            }
        }
        pDialog.dismiss();
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
    protected String[] doInBackground(String... params) {
        String [] tweets = null;
        try {
            String search = params[0];
            if(TextUtils.isEmpty(search)) {
                return null;
            }
            String url = Uri.parse(URL_SEARCH[0] + search).toString();
            String content = HttpRequest.get(url).authorization("Bearer " + getAccessToken()).body();

            Gson gson = new Gson();
            String json = gson.toJson(content);
            JSONObject jsonObj = new JSONObject(content);

            // JSONArra
            JSONArray jsonArray = jsonObj.getJSONArray("statuses");
            tweets = new String[jsonArray.length()];

            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String text = object.getString("text");
                String user = object.getJSONObject("user").getString("screen_name");
                tweets[i] = text + ":" + user;
            }
        } catch(Exception e) {
            Log.e("Exception_DO_BACKGROUND_TASK", e.getMessage());
        }
        return tweets;
    }
}
