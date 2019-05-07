package in.themoneytree.ui.base;

import android.app.Application;

import in.themoneytree.BuildConfig;
import timber.log.Timber;

public class BaseApp extends Application {

    private static BaseApp INSTANCE;

    public static BaseApp getContext() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
