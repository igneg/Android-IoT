package ch.supsi.dti.ssiot2016.shimmer;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Main application class, used to initialize Stetho
 */
public class MyApplication extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
