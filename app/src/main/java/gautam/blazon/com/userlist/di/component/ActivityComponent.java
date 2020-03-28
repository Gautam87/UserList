package gautam.blazon.com.userlist.di.component;
import dagger.Component;
import gautam.blazon.com.userlist.di.module.UserListModule;
import gautam.blazon.com.userlist.di.scope.ActivityScope;
import gautam.blazon.com.userlist.user_list.UserListActivity;
import gautam.blazon.com.userlist.user_list.UserListContract;
import gautam.blazon.com.userlist.user_list.UserListFragment;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = UserListModule.class)
public interface ActivityComponent {

    void inject(UserListActivity userListActivity);

    void inject(UserListFragment userListFragment);

    UserListContract.Presenter getUserListPresenter();
}