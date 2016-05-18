package temp.chiu.com.clinico;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import temp.chiu.com.clinico.global.Preferences;
import temp.chiu.com.clinico.global.Utility;
import temp.chiu.com.clinico.model.ConstructionListResponse;
import temp.chiu.com.clinico.model.ConstructionlModel;
import temp.chiu.com.clinico.network.Backend_API;
import temp.chiu.com.clinico.widget.SimpleDividerItemDecoration;

public class ConstructionListActivity extends BaseSpiceActivity {
    private static final String TAG = "ConstructionListActivity";

    @Bind(R.id.list)
    public UltimateRecyclerView list;

    public ConstructionListAdapter adapter;
    List<ConstructionlModel> constructionlModelList = new ArrayList<ConstructionlModel>();

    ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_construction_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = Utility.newCommonProgressDialog(this);
        setTitle(R.string.animalList);

        performDataListRequest();
    }

    private void performDataListRequest() {
        progressDialog.show();
        Preferences.getToken(this);

        Backend_API.ConstructionListRequest request = new Backend_API.ConstructionListRequest();
        getBackendSpiceManager().execute(request, new DataListRequestListener(this));
    }

    private class DataListRequestListener extends Backend_API.BaseAPIListener<ConstructionListResponse> {
        protected DataListRequestListener(Context context) {
            super(context, ConstructionListResponse.class, false);
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            progressDialog.dismiss();
            super.onRequestFailure(e);

            message = e.getCause().toString().replace("retrofit.RetrofitError: ", "");
            Log.d(TAG, "onRequestFailure " + message);
            Utility.showCommonDialog(ConstructionListActivity.this, message);
        }

        @Override
        public void onRequestSuccess(ConstructionListResponse response) {
            super.onRequestSuccess(response);
            Log.d(TAG, "onResultRequestSuccess. " + response.getResult().getCount());
            progressDialog.dismiss();
            constructionlModelList = response.getResult().getResults();

            initView();
        }
    }

    private void initView() {
        list.setLayoutManager(new LinearLayoutManager(this));
        SimpleDividerItemDecoration divider = new SimpleDividerItemDecoration(this);
        list.addItemDecoration(divider);
        adapter = new ConstructionListAdapter(this, constructionlModelList);
        list.setAdapter(adapter);
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ConstructionListActivity.class);
        activity.startActivity(intent);
    }
}
