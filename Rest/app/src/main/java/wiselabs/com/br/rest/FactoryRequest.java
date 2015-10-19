package wiselabs.com.br.rest;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by christoffer on 10/17/15.
 */
public interface FactoryRequest {
    public List<Entity> create(JSONArray array);
}
