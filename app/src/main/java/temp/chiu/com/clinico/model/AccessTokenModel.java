package temp.chiu.com.clinico.model;

/**
 * Created by xin-ling on 2016/5/15.
 */
public class AccessTokenModel {

    public String id;
    public String token;


    public String getToken() {
        return token == null ? "" : token;
    }
}
