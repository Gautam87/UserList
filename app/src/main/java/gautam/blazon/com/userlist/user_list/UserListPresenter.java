package gautam.blazon.com.userlist.user_list;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gautam.blazon.com.userlist.R;
import gautam.blazon.com.userlist.base.BasePresenter;
import gautam.blazon.com.userlist.data.model.GetUserListResponsePojo;
import gautam.blazon.com.userlist.data.model.UserItem;
import gautam.blazon.com.userlist.data.remote.ApiManager;
import gautam.blazon.com.userlist.utils.NetConnectionDetector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
    public void checkUserListInDb() {
        getMvpView().showLoader();
        List<UserItem> userItems = SQLite.select().
                from(UserItem.class).queryList();
        if (userItems.size() > 0) {
            getMvpView().hideLoader();
            handleUserListFromDb(userItems);
        } else {
//            handleEmptyDb();
            fetchUserListFromApi();
        }
    }

    @Override
    public void handleEmptyDb() {
        getMvpView().setInfoViewMessage(context.getString(R.string.error_no_users_in_db));
        getMvpView().showInfoView();
    }

    @Override
    public void handleUserListFromDb(List<UserItem> userItems) {
        getMvpView().hideLoader();
        getMvpView().hideInfoView();
        getMvpView().showUserList(userItems);
    }

    @Override
    public void fetchUserListFromApi() {
        if(checkNetwork()) {
            compositeDisposable.add(apiManager.getUserList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<GetUserListResponsePojo>() {
                        @Override
                        public void onNext(GetUserListResponsePojo doctorReportResponse) {
                            handleUserListResponse(doctorReportResponse);
                        }

                        @Override
                        public void onError(Throwable e) {
                            handleError();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }));
        }else {
            getMvpView().hideLoader();
            getMvpView().setInfoViewMessage(context.getString(R.string.error_network_unavailable));
        }
    }

    @Override
    public void handleUserListResponse(GetUserListResponsePojo getUserListResponsePojo) {
        if (isResponseValid(getUserListResponsePojo)) {
            HashMap<Integer, UserItem> userItemHashMap = new HashMap<>();
            if (getUserListResponsePojo.getData() != null) {
                for (UserItem item : getUserListResponsePojo.getData()) {
                    if (validateID(item) && userItemHashMap.get(item.getId()) == null) {
                        userItemHashMap.put(item.getId(), item);
                    }
                }
                if (userItemHashMap.size() > 0) {
                    List<UserItem> userItems = new ArrayList<>(userItemHashMap.values());
                    getMvpView().showUserList(userItems);
                    //clear previously stored data
                    Delete.table(UserItem.class);
                    //save in bulk
                    FlowManager.getModelAdapter(UserItem.class).saveAll(userItems);

                } else {
                    getMvpView().setInfoViewMessage(context.getString(R.string.error_no_users));
                    getMvpView().showInfoView();
                }
            }
        } else {
            getMvpView().setInfoViewMessage(context.getString(R.string.error_incorrect_response_code));
            getMvpView().showInfoView();
        }
    }

    @Override
    public boolean validateID(UserItem userItem) {
        if (userItem != null && userItem.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkNetwork() {
        return NetConnectionDetector.getInstance(context).isConntectingToInternet();
    }


    @Override
    public void handleNetworkAvailable() {
        this.fetchUserListFromApi();
    }

    @Override
    public void handleNetworkNotAvailable() {
        getMvpView().setInfoViewMessage(context.getString(R.string.error_network_unavailable));
        getMvpView().showInfoView();
    }

    @Override
    public boolean isResponseValid(GetUserListResponsePojo getUserListResponsePojo) {
        if (getUserListResponsePojo != null && getUserListResponsePojo.getCode() == 1200) {
            return true;
        }
        return false;
    }

    @Override
    public void handleTryAgainClicked() {
        fetchUserListFromApi();
    }

    @Override
    public void handleError() {
        getMvpView().hideLoader();
        getMvpView().setInfoViewMessage(context.getString(R.string.error_something_went_wrong));
        getMvpView().showInfoView();
    }
}
