package gautam.blazon.com.userlist.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import gautam.blazon.com.userlist.data.remote.ApiManager;
import gautam.blazon.com.userlist.user_list.UserListContract;
import gautam.blazon.com.userlist.user_list.UserListPresenter;

@Module(includes = {ContextModule.class, ApiModule.class})
public class UserListModule {

    private UserListContract.View view;

    public UserListModule(UserListContract.View viewCallBack) {
        this.view = viewCallBack;
    }

    @Provides
    public UserListContract.View provideView() {
        return view;
    }

    @Provides
    public UserListContract.Presenter providePresenter(Context context, UserListContract.View view, ApiManager apiManager) {
        return new UserListPresenter(context, view, apiManager);
    }
}