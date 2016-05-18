package temp.chiu.com.clinico.model;

/**
 * Created by xin-ling on 2016/5/15.
 */
public class ConstructionListResponse extends BackendResponse {

    public ConstructionListModel result;

    public ConstructionListModel getResult() {
        return result == null ? new ConstructionListModel() : result;
    }
}
