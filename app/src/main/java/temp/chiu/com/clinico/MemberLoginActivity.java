package temp.chiu.com.clinico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.octo.android.robospice.persistence.exception.SpiceException;

import butterknife.Bind;
import butterknife.OnClick;
import temp.chiu.com.clinico.global.Preferences;
import temp.chiu.com.clinico.global.Utility;
import temp.chiu.com.clinico.model.Welcome;
import temp.chiu.com.clinico.model.WelcomeResponse;
import temp.chiu.com.clinico.network.Backend_API;

public class MemberLoginActivity extends BaseSpiceActivity {
    private static final String TAG = "MemberLoginActivity";

    @Bind(R.id.account)
    EditText emailText;
    @Bind(R.id.password)
    EditText passwordText;

    ProgressDialog progressDialog;

    private String email = "";
    private String password = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = Utility.newCommonProgressDialog(this);
        setTitle(R.string.login);

        //取消左上角back鍵
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }

        fake();
    }

    private void fake() {
        emailText.setText("root");
        passwordText.setText("root");
    }

    @OnClick(R.id.login)
    void login() {
        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        Log.d(TAG, "email:" + email + ", password:" + password);

        performMemberLoginRequest();
    }

    private void performMemberLoginRequest() {
        progressDialog.show();

        getBackendSpiceManager().execute(
                new Backend_API.MemberLoginRequest(new Welcome(email, password)),
                new MemberLoginRequestListener(this)
        );
    }

    private class MemberLoginRequestListener extends Backend_API.BaseAPIListener<WelcomeResponse> {

        protected MemberLoginRequestListener(Context context) {
            super(context, WelcomeResponse.class, false);
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            Log.d(TAG, "onRequestFailure ");
            progressDialog.dismiss();
            super.onRequestFailure(e);

            message = e.getCause().toString().replace("retrofit.RetrofitError: ", "");
            Utility.showCommonDialog(MemberLoginActivity.this, message);
        }

        @Override
        public void onRequestSuccess(WelcomeResponse response) {
            super.onRequestSuccess(response);
            Log.d(TAG, "onResultRequestSuccess. " + new Gson().toJson(response));
            progressDialog.dismiss();

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.success)
                    .setPositiveButton(R.string.g_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ConstructionListActivity.launch(MemberLoginActivity.this);
                        }
                    }).create();
            dialog.show();
            Preferences.saveToken(MemberLoginActivity.this, response.getAccessToken().getToken());
        }
    }

}
