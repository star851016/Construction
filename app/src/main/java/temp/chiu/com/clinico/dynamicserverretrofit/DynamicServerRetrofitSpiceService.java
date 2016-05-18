package temp.chiu.com.clinico.dynamicserverretrofit;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;

/**
 * Created by Kate on 2014/11/17
 *
 * from a model of RetrofitSpiceService
 * @see com.octo.android.robospice.retrofit.RetrofitSpiceService
 * {@see <a href="http://stephanenicolas.github.io/robospice/site/latest/xref/com/octo/android/robospice/retrofit/RetrofitSpiceService.html">RetrofitSpiceService</a>}
 */
public abstract class DynamicServerRetrofitSpiceService extends SpiceService {
    private static final int WEB_SERVICES_TIMEOUT = 30 * 1000;

    private Map<String, Object> retrofitInterfaceToServiceMap = new HashMap<String, Object>();
    private RestAdapter.Builder builder;
    private Map<String, RestAdapter> restAdapterMap = new HashMap<String, RestAdapter>();
    protected List<Class<?>> retrofitInterfaceList = new ArrayList<Class<?>>();
    private Converter mConverter;

    @Override
    public void onCreate() {
        super.onCreate();
        builder = createRestAdapterBuilder();
        restAdapterMap.put(getServerUrl(), builder.build());
    }

    protected abstract String getServerUrl();

    protected RestAdapter.Builder createRestAdapterBuilder() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(getTimeoutMills(), TimeUnit.MILLISECONDS);
        okHttpClient.setCookieHandler(new CookieManager(null, getCookiePolicy()));
        if (isHTTPS()) {
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
                okHttpClient.setSslSocketFactory(sc.getSocketFactory());
                okHttpClient.setHostnameVerifier(new MyHostnameVerifier());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
        OkClient client = new OkClient(okHttpClient);

        return new RestAdapter.Builder().setEndpoint(getServerUrl()).setConverter(getConverter()).setClient(client);
    }

    protected CookiePolicy getCookiePolicy() {
        return CookiePolicy.ACCEPT_ORIGINAL_SERVER;
    }

    protected abstract Converter createConverter();

    protected final Converter getConverter() {
        if (mConverter == null) {
            mConverter = createConverter();
        }

        return mConverter;
    }

    protected RestAdapter getRestAdapter(String url) {
        RestAdapter restAdapter = restAdapterMap.get(url);
        if (restAdapter == null) {
            restAdapter = builder.setEndpoint(url).build();
            restAdapterMap.put(url, restAdapter);
        }
        return restAdapter;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getRetrofitService(Class<T> serviceClass, String url) {
        String serverUrl = url == null ? getServerUrl() : url;
        String serviceKey = getServiceKey(serviceClass, serverUrl);
        T service = (T) retrofitInterfaceToServiceMap.get(serviceKey);
        if (service == null) {
            service = getRestAdapter(serverUrl).create(serviceClass);
            retrofitInterfaceToServiceMap.put(serviceKey, service);
        }
        return service;
    }

    private <T> String getServiceKey(Class<T> serviceClass, String serverUrl){
        return serverUrl + "#" + serviceClass.getCanonicalName();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addRequest(CachedSpiceRequest<?> request, Set<RequestListener<?>> listRequestListener) {
        if (request.getSpiceRequest() instanceof DynamicServerRetrofitSpiceRequest) {
            DynamicServerRetrofitSpiceRequest retrofitSpiceRequest = (DynamicServerRetrofitSpiceRequest) request.getSpiceRequest();
            retrofitSpiceRequest.setService(getRetrofitService(retrofitSpiceRequest.getRetrofitedInterfaceClass(), retrofitSpiceRequest.getServerUrl()));
        }
        else if (request.getSpiceRequest() instanceof RetrofitSpiceRequest) {
            RetrofitSpiceRequest retrofitSpiceRequest = (RetrofitSpiceRequest) request.getSpiceRequest();
            retrofitSpiceRequest.setService(getRetrofitService(retrofitSpiceRequest.getRetrofitedInterfaceClass(), null));
        }

        super.addRequest(request, listRequestListener);
    }

    public final List<Class<?>> getRetrofitInterfaceList() {
        return retrofitInterfaceList;
    }

    protected void addRetrofitInterface(Class<?> serviceClass) {
        retrofitInterfaceList.add(serviceClass);
    }

    protected long getTimeoutMills() {
        return WEB_SERVICES_TIMEOUT;
    }

    protected boolean isHTTPS() {
        return false;
    }

    // here, trust managers is a single trust-all manager
    private class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}