package temp.chiu.com.clinico.dynamicserverretrofit;

import android.app.Application;

import com.google.gson.Gson;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.retrofit.GsonRetrofitObjectPersisterFactory;

import java.io.File;

import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

/**
 * Created by Kate on 2015/3/13
 *
 * @see com.octo.android.robospice.retrofit.RetrofitGsonSpiceService
 */
public abstract class DynamicServerRetrofitGsonSpiceService extends DynamicServerRetrofitSpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new GsonRetrofitObjectPersisterFactory(application, getConverter(), getCacheFolder()));
        return cacheManager;
    }

    @Override
    protected Converter createConverter() {
        return new GsonConverter(new Gson());
    }

    public File getCacheFolder() {
        return null;
    }
}
