package gautam.blazon.com.userlist.di.module;
import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gautam.blazon.com.userlist.UserListApplication;

@Module(includes = {ContextModule.class, ApiModule.class})
public class AppModule {

    private UserListApplication mUserListApplication;

    public AppModule(UserListApplication userListApplication) {
        this.mUserListApplication = userListApplication;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mUserListApplication;
    }
}