package temp.chiu.com.clinico.model;

/**
 * Created by xin-ling on 2016/5/15.
 */
public class WelcomeModel {
    public String id;
    public String name;
    public String email;
    public String account;
    public String status;
    public String role;


    public WelcomeModel() {
    }

    public WelcomeModel(String id, String name, String email, String account, String status, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.account = account;
        this.status = status;
        this.role = role;
    }

    public String getName() {
        return name == null ? "" : name;
    }
}
