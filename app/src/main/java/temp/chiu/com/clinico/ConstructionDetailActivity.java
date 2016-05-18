package temp.chiu.com.clinico;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import temp.chiu.com.clinico.model.ConstructionlModel;

public class ConstructionDetailActivity extends BaseSpiceActivity {
    private static final String TAG = "ConstructionDetailActivity";

    @Bind(R.id.APP_NAME)
    TextView APP_NAME;
    @Bind(R.id.ADDR)
    TextView ADDR;
    @Bind(R.id.DurationStart)
    TextView DurationStart;
    @Bind(R.id.DurationEnd)
    TextView DurationEnd;
    @Bind(R.id.Tc_Ma)
    TextView Tc_Ma;
    @Bind(R.id.Tc_Ma3)
    TextView Tc_Ma3;
    @Bind(R.id.note)
    TextView note;
   


    ;

    ConstructionlModel constructionlModel = new ConstructionlModel();
    private static final String Construction_MODEL = "Construction_model";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected int getContentView() {
        return R.layout.activity_construction_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.animalDetail);

        getData();
        initView();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void getData() {
        if (getIntent() == null) {
            finish();
        }

        constructionlModel = new Gson().fromJson(getIntent().getStringExtra(Construction_MODEL), ConstructionlModel.class);
    }

    private void initView() {
        APP_NAME.setText("施工單位:" + constructionlModel.getAPP_NAME());
        DurationStart.setText("施工起日:" + constructionlModel.getCB_DA());
        DurationEnd.setText("施工迄日:" + constructionlModel.getCE_DA());
        Tc_Ma.setText("監工:" + constructionlModel.getTC_MA() + "/" + constructionlModel.getTC_TL());
        Tc_Ma3.setText("現場人員:" + constructionlModel.getTC_MA3() + "/" + constructionlModel.getTC_TL3());
        ADDR.setText("地點:" + constructionlModel.getC_NAME() + "區" + constructionlModel.getADDR());
        if (!constructionlModel.getCO_TI().isEmpty()) {
            note.setVisibility(View.VISIBLE);
            note.setText(constructionlModel.getCO_TI());
        }

    }


    public static void launch(Activity activity, ConstructionlModel data) {
        Intent intent = new Intent(activity, ConstructionDetailActivity.class);
       intent.putExtra(Construction_MODEL, new Gson().toJson(data));
        activity.startActivity(intent);
    }

   public void maps(View view){
       Intent intent = new Intent(ConstructionDetailActivity.this, MapsActivity.class);
       startActivity(intent);
   }




    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ConstructionDetail Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://temp.chiu.com.clinico/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ConstructionDetail Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://temp.chiu.com.clinico/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }



}
