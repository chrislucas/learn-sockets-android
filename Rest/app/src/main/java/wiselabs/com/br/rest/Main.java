package wiselabs.com.br.rest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Main extends AppCompatActivity {

    private EditText textSearch;
    private ListView resultSearch;
    private Button sendSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textSearch = (EditText) findViewById(R.id.text_search);
        this.sendSearch = (Button) findViewById(R.id.button_search);
        this.resultSearch = (ListView) findViewById(R.id.result_list);
    }

    public void toSearch(View view) {

    }
}
