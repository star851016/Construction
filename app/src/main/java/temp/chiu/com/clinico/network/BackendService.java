package temp.chiu.com.clinico.network;

import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import temp.chiu.com.clinico.dynamicserverretrofit.DynamicServerRetrofitGsonSpiceService;
import temp.chiu.com.clinico.global.GlobalConstant;
import temp.chiu.com.clinico.global.Preferences;

public class BackendService extends DynamicServerRetrofitGsonSpiceService {

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", Preferences.getToken(getApplicationContext()));
        }
    };

    @Override
    protected boolean isHTTPS() {
        return true;
    }

    @Override
    protected String getServerUrl() {
        return GlobalConstant.BACKEND_API_URL;
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        return super.createRestAdapterBuilder().setRequestInterceptor(requestInterceptor);
    }

    @Override
    protected long getTimeoutMills() {
        return GlobalConstant.WEB_SERVICES_TIMEOUT;
    }

    @Override
    protected Converter createConverter() {
        return new GsonConverter(new GsonBuilder().serializeNulls().create());
    }
}
