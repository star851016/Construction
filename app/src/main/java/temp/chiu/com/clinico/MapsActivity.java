package temp.chiu.com.clinico;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import temp.chiu.com.clinico.model.ConstructionlModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double longtitude;
    private double latitude;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            if (mMap != null) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            }
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(getlat(), getlong());
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    ConstructionlModel constructionlModel = new ConstructionlModel();
    private static final String Construction_MODEL = "Construction_model";

    String x;
    String y;
    double a;
    double b;


    public double getlong() {
        x = constructionlModel.getX();
        a = Double.parseDouble(x);
        return latlon[0];
    }

    public double getlat() {
        constructionlModel = new Gson().fromJson(getIntent().getStringExtra(Construction_MODEL), ConstructionlModel.class);
        y = constructionlModel.getY();
        b = Double.parseDouble(y);
        convert(a, b);
        return latlon[1];
    }



    public static double[] latlon;

    public double[] convert(double x, double y) {
        int dx = 250000;
        double dy = 0;
        double lon0 = 121 * Math.PI / 180;
        double k0 = 0.9999;
        double a = 6378137.0;
        double b = 6356752.314245;
        double e = Math.pow((1 - Math.pow(b, 2) / Math.pow(a, 2)), 0.5);

        x -= dx;
        y -= dy;

        // Calculate the Meridional Arc
        double M = y / k0;

        // Calculate Footprint Latitude

        double mu = M / (a * (1.0 - Math.pow(e, 2) / 4.0 - 3 * Math.pow(e, 4) / 64.0 - 5 * Math.pow(e, 6) / 256.0));
        double e1 = (1.0 - Math.pow((1.0 - Math.pow(e, 2)), 0.5)) / (1.0 + Math.pow((1.0 - Math.pow(e, 2)), 0.5));

        double J1 = (3 * e1 / 2 - 27 * Math.pow(e1, 3) / 32.0);
        double J2 = (21 * Math.pow(e1, 2) / 16 - 55 * Math.pow(e1, 4) / 32.0);
        double J3 = (151 * Math.pow(e1, 3) / 96.0);
        double J4 = (1097 * Math.pow(e1, 4) / 512.0);

        double fp = mu + J1 * Math.sin(2 * mu) + J2 * Math.sin(4 * mu) + J3 * Math.sin(6 * mu) + J4 * Math.sin(8 * mu);

        // Calculate Latitude and Longitude

        double e2 = Math.pow((e * a / b), 2);
        double C1 = Math.pow(e2 * Math.cos(fp), 2);
        double T1 = Math.pow(Math.tan(fp), 2);
        double R1 = a * (1 - Math.pow(e, 2)) / Math.pow((1 - Math.pow(e, 2) * Math.pow(Math.sin(fp), 2)), (3.0 / 2.0));
        double N1 = a / Math.pow((1 - Math.pow(e, 2) * Math.pow(Math.sin(fp), 2)), 0.5);

        double D = x / (N1 * k0);

        // lat
        double Q1 = N1 * Math.tan(fp) / R1;
        double Q2 = (Math.pow(D, 2) / 2.0);
        double Q3 = (5 + 3 * T1 + 10 * C1 - 4 * Math.pow(C1, 2) - 9 * e2) * Math.pow(D, 4) / 24.0;
        double Q4 = (61 + 90 * T1 + 298 * C1 + 45 * Math.pow(T1, 2) - 3 * Math.pow(C1, 2) - 252 * e2) * Math.pow(D, 6) / 720.0;
        double lat = fp - Q1 * (Q2 - Q3 + Q4);

        lat = (lat * 180) / Math.PI;
        Math.toDegrees(lat);
        // long
        double Q5 = D;
        double Q6 = (1 + 2 * T1 + C1) * Math.pow(D, 3) / 6;
        double Q7 = (5 - 2 * C1 + 28 * T1 - 3 * Math.pow(C1, 2) + 8 * e2 + 24 * Math.pow(T1, 2)) * Math.pow(D, 5) / 120.0;
        double lon = lon0 + (Q5 - Q6 + Q7) / Math.cos(fp);
        lon = (lon * 180) / Math.PI;
        Math.toDegrees(lon);

        latlon = new double[2];
        latlon[0] = lat;
        latlon[1] = lon;
        return latlon;

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
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
                "Maps Page", // TODO: Define a title for the content shown.
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

