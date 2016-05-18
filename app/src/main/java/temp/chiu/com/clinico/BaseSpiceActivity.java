package temp.chiu.com.clinico;


import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import temp.chiu.com.clinico.network.BackendService;

public abstract class BaseSpiceActivity extends BaseActivity {
    private SpiceManager backendSpiceManager = new SpiceManager(BackendService.class);

    @Override
    protected void onStart() {
        backendSpiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (backendSpiceManager.isStarted()) {
            backendSpiceManager.shouldStop();
        }
        super.onStop();
    }

    protected SpiceManager getBackendSpiceManager() {
        return backendSpiceManager;
    }

    public <T> void backendSpiceExecute(final SpiceRequest<T> request, final RequestListener<T> requestListener) {
        if (backendSpiceManager != null && backendSpiceManager.isStarted()) {
            backendSpiceManager.execute(request, requestListener);
        }
    }
}
