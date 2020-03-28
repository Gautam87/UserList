package gautam.blazon.com.userlist.di.module;

import dagger.Module;
import dagger.Provides;
import gautam.blazon.com.userlist.data.remote.ApiManager;

@Module
public class ApiModule {

    @Provides
    ApiManager providesApiManager(){
        return new ApiManager();
    }
}
