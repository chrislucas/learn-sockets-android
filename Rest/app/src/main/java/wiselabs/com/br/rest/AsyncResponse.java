package wiselabs.com.br.rest;

import java.util.List;

/**
 * Created by christoffer on 10/13/15.
 */
public interface AsyncResponse<T>  {
    public <T> void getResponse(T t);
    public <T> void getResponse(T[] t);
    public <T> void getResponse(List<T> list);
}
