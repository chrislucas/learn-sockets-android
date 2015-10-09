package wiselabs.com.br.connection.async;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import sockets.SocketSendMessage;
import wiselabs.com.br.connection.R;

/**
 * Created by C.Lucas on 08/10/2015.
 */
// AsyncTask<Params, Progress, Result>
    /**
     * The three types used by an asynchronous task are the following:
     * Params, the type of the parameters sent to the task upon execution.
     * Progress, the type of the progress units published during the background computation.
     * Result, the type of the result of the background computation.
     * */
public class SocketConnectionAsync extends AsyncTask<String, Integer, String> {



    private Activity activity;                  // activity que chamou a asynctask
    private String address;
    private Integer port;

    public Activity getActivity() {
        return activity;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPort() {
        return port;
    }

        /**
     * onPreExecute(), invoked on the UI thread before the task is executed.
     * This step is normally used to setup the task, for instance by showing a progress bar in the user interface.
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * onPostExecute(Result), invoked on the UI thread after
     * the background computation finishes. The result of the background
     * computation is passed to this step as a parameter.
     * */
    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        if(getActivity() != null) {
            TextView answer = (TextView) getActivity().findViewById(R.id.answerMessage);
            if(answer != null) {
                answer.setText(message);
            }
        }
        return;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String address = getAddress();
            Integer port = getPort();
            SocketSendMessage socket = new SocketSendMessage(address, port);
            socket.sendMessage(params[0]);

        } catch(IOException ioex) {
            Log.e("IOException", ioex.getMessage());
        }
        publishProgress(params.length);
        return params[0];
    }

    public SocketConnectionAsync(Activity activity, String address, Integer port) {
        super();
        this.activity = activity;
        this.address = address;
        this.port = port;
    }

    /*
    *
    *  invoked on the UI thread after a call to publishProgress(Progress...).
    *  The timing of the execution is undefined. This method is used to display any
    *  form of progress in the user interface while the background computation is still
    *  executing. For instance, it can be used to animate a progress bar or show
    *  logs in a text field.
    * */
    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress[0]);
    }

    @Override
    protected void onCancelled(String message) {
        super.onCancelled(message);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
