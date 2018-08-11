package gautam.blazon.com.userlist;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class UserListApplication extends MultiDexApplication{

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // For Database
        FlowManager.init(new FlowConfig.Builder(this).build());
        // For Debugging
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

    }
}
