package gautam.blazon.com.userlist.di.component;

import android.app.Application;
import android.content.Context;


import javax.inject.Singleton;

import dagger.Component;
import gautam.blazon.com.userlist.UserListApplication;
import gautam.blazon.com.userlist.di.module.AppModule;
import gautam.blazon.com.userlist.di.module.ContextModule;

@Singleton
@Component(modules = {AppModule.class, ContextModule.class})
public interface AppComponent {
    void inject(UserListApplication userListApplication);

//    Context getContext();

    Application getUserListApplication();
}
