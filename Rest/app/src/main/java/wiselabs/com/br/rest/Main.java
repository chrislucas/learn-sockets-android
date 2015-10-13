package wiselabs.com.br.rest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import wiselabs.com.br.rest.http.request.ApiSearch;
import wiselabs.com.br.rest.http.request.TwitterAuthentication;
import wiselabs.com.br.rest.http.request.TwitterTask;

public class Main extends AppCompatActivity implements AsyncResponse {

    private EditText textSearch;
    private ListView resultSearch;
    private Button sendSearch, sendSearch2;
    private String response;
    private String responses[];
    private List<User> listResponse;
    private AsyncResponse<String> delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textSearch = (EditText) findViewById(R.id.text_search);
        this.sendSearch = (Button) findViewById(R.id.button_search);
        this.sendSearch2 = (Button) findViewById(R.id.button_search_api);
        this.resultSearch = (ListView) findViewById(R.id.result_list);
        TwitterAuthentication taskAuthtentication = new TwitterAuthentication();
        taskAuthtentication.setAsyncResponse(this);
        taskAuthtentication.execute();
        return;
    }

    public void toSearch(View view) {
        if(textSearch != null) {
            String param = textSearch.getText().toString();
            if(this.response != null) {
                TwitterTask taskSearch = new TwitterTask(this, this.response);
                taskSearch.setAsyncResponse(this);
                taskSearch.execute(param);
                if(this.responses != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, this.responses);
                    this.resultSearch.setAdapter(adapter);
                }

            } else {
                Toast.makeText(this, "ACCESS_TOKEN NULL", Toast.LENGTH_LONG).show();
            }
        }
        return;
    }

    public void toSearchAPI(View view) {
        ApiSearch apiSearch = new ApiSearch(this);
        apiSearch.setAsyncResponse(this);
        apiSearch.execute();
        if(this.listResponse != null) {
            ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
                    android.R.layout.simple_list_item_1, this.listResponse);
            this.resultSearch.setAdapter(adapter);
        }
        return;
    }

    @Override
    public void getResponse(Object response) {
        if(response != null) {
            this.response = response.toString();
        }
        return;
    }

    @Override
    public void getResponse(Object[] responses) {
        if(responses != null) {
            this.responses = (String[]) responses;
        }
    }

    @Override
    public void getResponse(List list) {
        if(list != null) {
            this.listResponse = list;
        }
    }
}
