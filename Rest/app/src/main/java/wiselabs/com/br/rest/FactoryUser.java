package wiselabs.com.br.rest;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christoffer on 10/19/15.
 */
public class FactoryUser implements FactoryRequest {

    @Override
    public List<Entity> create(JSONArray array) {
        List<Entity> list = new ArrayList<>();
        //Gson gson = new Gson();
        for(int i=0; i<array.length(); i++) {
            try {
                JSONObject object = array.getJSONObject(i);
                String login      = object.getString("login");
                String name       = object.getString("nome");
                String pwd        = object.getString("senha");
                int idUser        = object.getInt("id_usuario");
                String imei1      = object.getString("imei_1");
                String imei2      = object.getString("imei_2");
                User user         = new User();
                user.setId(idUser);
                user.setLogin(login);
                user.setName(name);
                user.setImei(new String[] {imei1, imei2});
                list.add(user);
            } catch (JSONException jsonex) {
                Log.e("JSONException", jsonex.getLocalizedMessage());
            }
        }
        return list;
    }
}
