package temp.chiu.com.clinico.dynamicserverretrofit;

import android.app.Application;

import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.retrofit.GsonRetrofitObjectPersisterFactory;

import java.io.File;

import retrofit.converter.Converter;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by Kate on 2015/3/13
 */
public abstract class DynamicServerRetrofitXmlSpiceService extends DynamicServerRetrofitSpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new GsonRetrofitObjectPersisterFactory(application, getConverter(), getCacheFolder()));
        return cacheManager;
    }

    @Override
    protected Converter createConverter() {
        return new SimpleXMLConverter();
    }

    public File getCacheFolder() {
        return null;
    }
}
