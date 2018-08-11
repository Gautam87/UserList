package gautam.blazon.com.userlist.user_list;

import android.content.Context;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import gautam.blazon.com.userlist.R;
import gautam.blazon.com.userlist.data.model.GetUserListResponsePojo;
import gautam.blazon.com.userlist.data.model.UserItem;
import gautam.blazon.com.userlist.utils.NetConnectionDetector;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Local unit test for the User List Presenter
 */
public class UserListPresenterTest {

    @Mock
    private UserListContract.View mView;

    private UserListPresenter mPresenter;

    private UserListPresenter mPresenterSpy;

    @Mock
    private Context mMockContext;

    @Mock
    private NetConnectionDetector mNetConnectionDetector;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new UserListPresenter(mMockContext);
        mPresenterSpy = spy(mPresenter);
        mPresenter.attachView(mView);
    }

    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Scheduler.Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Test
    public void HandleNetworkAvailable() {
        when(mNetConnectionDetector.isConntectingToInternet()).thenReturn(true);
        mPresenter.handleNetworkAvailable();
    }

    @Test
    public void HandleNetworkNotAvailable() {
        when(mNetConnectionDetector.isConntectingToInternet()).thenReturn(false);
        mPresenter.handleNetworkNotAvailable();
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_network_unavailable));
        verify(mView).showInfoView();
    }

//    @Test
//    public void checkUserListInDbWithoutContents() {
//        mPresenterSpy.checkUserListInDb();
//        verify(mPresenterSpy).handleEmptyDb();
//    }

//    @Test
//    public void checkUserListInDbWithContents() {
//        mPresenter.checkUserListInDb();
//            verify(mPresenterSpy).handleUserListFromDb(anyListOf(UserItem.class));
//    }

    @Test
    public void handleEmptyDb() {
        mPresenter.handleEmptyDb();
        verify(mView).showInfoView();
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_no_users_in_db));
    }

//    @Test
//    public void handleUserListFromDb() {
//        mPresenter.checkUserListInDb();
//        List<UserItem> userItems = new ArrayList<>();
//        userItems.add(new UserItem());
//        mPresenter.handleUserListFromDb(userItems);
//        verify(mView).showUserList(anyListOf(UserItem.class));
//    }

    @Test
    public void handleUserListResponseWithCorrectStatusAndNormalList() {
        GetUserListResponsePojo getUserListResponsePojo = new GetUserListResponsePojo();
        getUserListResponsePojo.setCode(1200);
        mPresenterSpy.handleUserListResponse(getUserListResponsePojo);
        verify(mPresenterSpy).isResponseValid(getUserListResponsePojo);
    }


    @Test
    public void handleUserListResponseWithIncorrectStatus() {
        GetUserListResponsePojo getUserListResponsePojo = new GetUserListResponsePojo();
        getUserListResponsePojo.setCode(400);
        mPresenter.handleUserListResponse(getUserListResponsePojo);
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_incorrect_response_code));
        verify(mView).showInfoView();
    }

    @Test
    public void handleTryAgainClicked() {
        mPresenterSpy.handleTryAgainClicked();
        verify(mPresenterSpy).fetchUserListFromApi();
    }
}