package wiselabs.com.br.rest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import wiselabs.com.br.rest.sensor.AccelerometerListener;
import wiselabs.com.br.rest.utils.device.UtilsNetworking;

public class Main extends AppCompatActivity implements AsyncResponse, AccelerometerListener {

    private EditText textSearch;
    private ListView resultSearch;
    private Button sendSearch, sendSearch2;
    private String response;
    private String responses[];
    private List<User> listResponse;
    private AsyncResponse<String> delegate;

    @Override
    public void onShake(float force) {

    }

    @Override
    public void onAccelerationChange(float x, float y, float z) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UtilsNetworking.getPhoneManager(this);


        this.textSearch = (EditText) findViewById(R.id.text_search);
        this.sendSearch = (Button) findViewById(R.id.button_search);
        this.sendSearch2 = (Button) findViewById(R.id.button_search_api);
        this.resultSearch = (ListView) findViewById(R.id.result_list);
        this.resultSearch.setLongClickable(true);
        this.resultSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                return;
            }
        });

        this.resultSearch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Hold on", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        TwitterAuthentication taskAuthtentication = new TwitterAuthentication(this);
        taskAuthtentication.setAsyncResponse(this);
        taskAuthtentication.execute();
        return;
    }

    public void toSearch(View view) {
        if(textSearch != null) {
            String param = textSearch.getText().toString();
            if(this.response != null) {
                String token = this.response;
                TwitterTask taskSearch = new TwitterTask(this, token);
                taskSearch.setAsyncResponse(this);
                taskSearch.execute(param);
            } else {
                Toast.makeText(this, "ACCESS_TOKEN NULL", Toast.LENGTH_LONG).show();
            }
        }
        return;
    }

    public void toSearchAPI(View view) {
        ApiSearch apiSearch = new ApiSearch(this);
        apiSearch.setAsyncResponse(this);
        String url = "http://wgx.com.br/demo/serrat/api/index.php/usuario?token=wgx@_2k15!&format=json";
        apiSearch.execute(url);
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
            if(responses != null) {
                fillListPosResponse((String[])responses);
            }
        }
    }

    @Override
    public void getResponse(List list) {
        if(list != null) {
            this.listResponse = list;
            if(list != null) {
                fillListPosResponse(this.listResponse);
            }
        }
    }

    public void fillListPosResponse(List list) {
        if(list != null) {
            ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
                    android.R.layout.simple_list_item_1, this.listResponse);
            this.resultSearch.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        return;
    }

    public void fillListPosResponse(String[] objects) {
        if(objects != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, objects);
            this.resultSearch.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
