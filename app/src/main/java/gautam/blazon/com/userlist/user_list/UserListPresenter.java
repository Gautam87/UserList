package gautam.blazon.com.userlist.user_list;

import android.content.Context;

import gautam.blazon.com.userlist.base.BasePresenter;
import gautam.blazon.com.userlist.data.remote.ApiManager;
import io.reactivex.disposables.CompositeDisposable;

public class UserListPresenter extends BasePresenter<UserListContract.View> implements UserListContract.Presenter {

    private static final String TAG = UserListPresenter.class.getName();
    Context context;
    private CompositeDisposable compositeDisposable;
    private ApiManager apiManager = new ApiManager();

    public UserListPresenter(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(UserListContract.View mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

}
