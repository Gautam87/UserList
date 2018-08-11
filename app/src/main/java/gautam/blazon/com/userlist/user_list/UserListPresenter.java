package gautam.blazon.com.userlist.user_list;

import android.content.Context;

import java.util.List;

import gautam.blazon.com.userlist.base.BasePresenter;
import gautam.blazon.com.userlist.data.model.GetUserListResponsePojo;
import gautam.blazon.com.userlist.data.model.UserItem;
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

    @Override
    public void handlePermissionsAllowed() {

    }

    @Override
    public void handlePermissionsDenied() {

    }

    @Override
    public void checkUserListInDb() {

    }

    @Override
    public void handleEmptyDb() {

    }

    @Override
    public void handleUserListFromDb(List<UserItem> userItems) {

    }

    @Override
    public void fetchUserListFromApi() {

    }

    @Override
    public void handleUserListResponse(GetUserListResponsePojo getUserListResponsePojo) {

    }

    @Override
    public boolean validateID(UserItem userItem) {
        return false;
    }

    @Override
    public boolean checkNetwork() {
        return false;
    }


    @Override
    public void HandleNetworkAvailable() {

    }

    @Override
    public void HandleNetworkNotAvailable() {

    }

    @Override
    public boolean isResponseValid(GetUserListResponsePojo getUserListResponsePojo) {
        return false;
    }

    @Override
    public void handleTryAgainClicked() {

    }
}
