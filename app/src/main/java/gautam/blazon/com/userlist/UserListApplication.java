package gautam.blazon.com.userlist;

import android.content.Context;

import androidx.multidex.MultiDexApplication;
import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import gautam.blazon.com.userlist.di.component.AppComponent;
import gautam.blazon.com.userlist.di.component.DaggerAppComponent;
import gautam.blazon.com.userlist.di.module.AppModule;
import gautam.blazon.com.userlist.di.module.ContextModule;

public class UserListApplication extends MultiDexApplication{

    AppComponent component;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static UserListApplication get(Context context) {
        return (UserListApplication) context.getApplicationContext();
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

        //DI
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .contextModule(new ContextModule(this))
                .build();

    }

    public AppComponent component() {
        return component;
    }
}
