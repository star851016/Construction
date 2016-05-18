package temp.chiu.com.clinico.dynamicserverretrofit;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by Kate on 2014/11/17
 */
public abstract class DynamicServerRetrofitSpiceRequest<T, R> extends RetrofitSpiceRequest<T, R> {
    public DynamicServerRetrofitSpiceRequest(Class<T> clazz, Class<R> retrofitedInterfaceClass) {
        super(clazz, retrofitedInterfaceClass);
    }

    public String getServerUrl() {
        return null;
    }
}