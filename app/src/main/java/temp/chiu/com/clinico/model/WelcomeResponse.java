package temp.chiu.com.clinico.model;

/**
 * Created by xin-ling on 2016/5/15.
 */
public class WelcomeResponse extends BackendResponse {
    public WelcomeModel member;
    public AccessTokenModel accessToken;

    public WelcomeModel getMmeber() {
        return member == null ? new WelcomeModel() : member;
    }

    public AccessTokenModel getAccessToken() {
        return accessToken == null ? new AccessTokenModel() : accessToken;
    }
}
