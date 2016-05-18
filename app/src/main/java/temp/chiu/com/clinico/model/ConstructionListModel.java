package temp.chiu.com.clinico.model;

import java.util.ArrayList;
import java.util.List;

/**
 *Created by xin-ling on 2016/5/15.
 */
public class ConstructionListModel {
    public String count;
    public List<ConstructionlModel> results;

    public List<ConstructionlModel> getResults() {
        return results == null ? new ArrayList<ConstructionlModel>() : results;
    }

    public String getCount() {
        return count == null ? "" : count;
    }
}
