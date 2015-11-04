package wiselabs.com.br.rest.factory;

import org.json.JSONArray;

import java.util.List;

import wiselabs.com.br.rest.entities.Entity;

/**
 * Created by christoffer on 10/17/15.
 */
public interface FactoryRequest {
    public List<Entity> create(JSONArray array);
}
