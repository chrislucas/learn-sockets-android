package wiselabs.com.br.connection;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import sockets.SocketSendMessage;
import wiselabs.com.br.connection.async.SocketConnectionAsync;
import wiselabs.com.br.connection.utils.Device;

public class ActivitySendMessage extends AppCompatActivity {

    private static final String EXTRA_INFO = "livro";
    private static final String IP = "localhost"; //"10.0.2.2";
    private static final Integer PORT = 7777;
    private Button sendMessage;
    private EditText message;
    private TextView anserMessage;

    public TextView getAnserMessage() {
        return anserMessage;
    }

    public void setAnserMessage(String text) {
        if(this.anserMessage != null)
            this.anserMessage.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.message = (EditText) findViewById(R.id.socketMessage);
        this.sendMessage = (Button) findViewById(R.id.sendSocketMessage);
        this.anserMessage = (TextView) findViewById(R.id.answerMessage);

        this.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void sendMessage(View view) {
        String message = this.message != null ? this.message.getText().toString() : "Nao ha mensagem";
        String address = Device.getAddressDevice();
        final Activity that = this;
        new SocketConnectionAsync(that, "192.168.0.101", PORT).execute(message);
        return;
    }
}
