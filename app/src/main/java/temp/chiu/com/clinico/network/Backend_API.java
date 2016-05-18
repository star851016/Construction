package temp.chiu.com.clinico.network;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import temp.chiu.com.clinico.R;
import temp.chiu.com.clinico.dynamicserverretrofit.DynamicServerRetrofitSpiceRequest;
import temp.chiu.com.clinico.global.Utility;
import temp.chiu.com.clinico.model.ConstructionListResponse;
import temp.chiu.com.clinico.model.BackendResponse;
import temp.chiu.com.clinico.model.Welcome;
import temp.chiu.com.clinico.model.WelcomeResponse;

public class Backend_API {

    public static class BaseAPIListener<T extends BackendResponse> implements RequestListener<T> {
        private static final String TAG = "BaseAPIListener";

        protected final Context context;
        private final Class<T> typeParameterClass;
        private boolean showDialog;
        protected String message;
        private String title;
        private DialogInterface.OnClickListener onClickListener;
        protected float responseCode = 1;
        private Handler mHandler = new Handler();

        protected BaseAPIListener(Context context, Class<T> typeParameterClass) {
            this(context, typeParameterClass, true);
        }

        protected BaseAPIListener(Context context, Class<T> typeParameterClass, boolean showDialog) {
            this.context = context;
            this.typeParameterClass = typeParameterClass;
            this.showDialog = showDialog;
            title = context.getString(R.string.app_name);
            onClickListener = null;
            message = context.getString(R.string.g_e_network);
        }

        protected void setTitle(String title) {
            this.title = title;
        }

        protected void setDialogOnClickListener(DialogInterface.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        protected void setDefaultDialogShow(boolean showDialog) {
            this.showDialog = showDialog;
        }

        protected void showRequestDialog() {
            if (onClickListener != null) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(R.string.g_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onClickListener.onClick(dialogInterface, i);
                            }
                        }).create();
                dialog.show();

            } else {
                Utility.showCommonDialog(context, title, message);
            }
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException.getCause() != null) {
                Log.d(TAG, spiceException.getCause().toString());
            }

            if (spiceException instanceof NoNetworkException) {
                message = context.getString(R.string.g_e_connect_message);
            }
            /**
             * maybe no internet or server problem
             */
            if (showDialog) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                showRequestDialog();
            }
        }

        protected boolean isSuccessRC() {
            return responseCode < 400;
        }

        @Override
        public void onRequestSuccess(T result) {

            if (result == null) {
                if (showDialog) {
                    showRequestDialog();
                }
            } else {
                responseCode = result.getCode();
                message = result.getRM();
            }
        }
    }

    public static abstract class BaseRequest<T extends BackendResponse> extends DynamicServerRetrofitSpiceRequest<T, BackendQuery> {
            public BaseRequest(Class<T> typeParameterClass) {
                super(typeParameterClass, BackendQuery.class);

                setRetryPolicy(null);
            }
    }

    public static class MemberLoginRequest extends BaseRequest<WelcomeResponse> {

        private Welcome welcome;

        public MemberLoginRequest(Welcome welcome) {
            super(WelcomeResponse.class);
            this.welcome = welcome;
        }

        @Override
        public WelcomeResponse loadDataFromNetwork() throws Exception {
            return getService().login(welcome);
        }
    }

    public static class ConstructionListRequest extends BaseRequest<ConstructionListResponse> {

        public ConstructionListRequest() {
            super(ConstructionListResponse.class);
        }

        @Override
        public ConstructionListResponse loadDataFromNetwork() throws Exception {
            return getService().getConstructionList();
        }

        @Override
        public String getServerUrl() {
            return "http://data.taipei/opendata/datalist";
        }
    }

}
