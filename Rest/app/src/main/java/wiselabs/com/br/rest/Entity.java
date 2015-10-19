package wiselabs.com.br.rest;

/**
 * Created by christoffer on 10/16/15.
 */
public abstract class Entity {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public abstract String toString();
}
