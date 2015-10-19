package wiselabs.com.br.rest;

/**
 * Created by christoffer on 10/13/15.
 */
public class User extends Entity {

    private String name;
    private String login;
    private String imei[];

    public String[] getImei() {
        return imei;
    }

    public void setImei(String imei[]) {
        this.imei = imei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        String imei[] = getImei();
        return getName() + ":" + getId() + " " + imei[0] + " " + imei[1];
    }
}
