package gautam.blazon.com.userlist.user_list;

import android.content.Context;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

    @Spy
    private List<UserItem> userItems = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new UserListPresenter(mMockContext);
        mPresenterSpy = spy(mPresenter);
        mPresenter.attachView(mView);
        mPresenterSpy.attachView(mView);
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
    public void handleUserListFromDb() {
        mPresenterSpy.handleUserListFromDb(anyListOf(UserItem.class));
        verify(mView).hideLoader();
        verify(mView).hideInfoView();
        verify(mView).showUserList(anyListOf(UserItem.class));
        verify(mView).setTitle(mMockContext.getString(R.string.user_list_db));
    }

    @Test
    public void handleNetworkAvailable() {
        doReturn(true).when(mPresenterSpy).checkNetwork();
        mPresenterSpy.handleNetworkAvailable();
        verify(mPresenterSpy).fetchUserListFromApi();
        verify(mView, never()).setInfoViewMessage(anyString());
    }

    @Test
    public void handleNetworkNotAvailable() {
        when(mNetConnectionDetector.isConntectingToInternet()).thenReturn(false);
        mPresenterSpy.handleNetworkNotAvailable();
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_network_unavailable));
        verify(mView).showInfoView();
        verify(mPresenterSpy, never()).fetchUserListFromApi();

    }

    @Test
    public void handleEmptyDb() {
        mPresenter.handleEmptyDb();
        verify(mView).showInfoView();
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_no_users_in_db));
    }


    @Test
    public void handleUserListResponseWithCorrectStatusAndNormalList() {
        GetUserListResponsePojo getUserListResponsePojo = mock(GetUserListResponsePojo.class);
        getUserListResponsePojo.setCode(1200);
        UserItem item = mock(UserItem.class);
        item.setId(1);
        item.setName("XYZ");
        item.setSkills("TEST SKILLS");
        item.setName("Test Name");
        userItems.add(item);
        getUserListResponsePojo.setData(userItems);
        doReturn(Collections.singletonList(item)).when(getUserListResponsePojo).getData();
        doReturn(true).when(mPresenterSpy).isResponseValid(getUserListResponsePojo);
        mPresenterSpy.handleUserListResponse(getUserListResponsePojo);
        verify(mPresenterSpy).isResponseValid(getUserListResponsePojo);
        verify(mView).showUserList(Mockito.anyListOf(UserItem.class));
        verify(mView).hideLoader();
        verify(mView).hideInfoView();
        verify(mView).setTitle(mMockContext.getString(R.string.user_list_api));
    }

    @Test
    public void handleUserListResponseWithCorrectStatusAndEmptyList() {
        GetUserListResponsePojo getUserListResponsePojo = mock(GetUserListResponsePojo.class);
        getUserListResponsePojo.setCode(1200);
        userItems = new ArrayList<>();
        getUserListResponsePojo.setData(userItems);
        doReturn(userItems).when(getUserListResponsePojo).getData();
        doReturn(true).when(mPresenterSpy).isResponseValid(getUserListResponsePojo);
        mPresenterSpy.handleUserListResponse(getUserListResponsePojo);
        verify(mPresenterSpy).isResponseValid(getUserListResponsePojo);
        verify(mView, never()).showUserList(Mockito.anyListOf(UserItem.class));
        verify(mView).hideLoader();
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_no_users));
        verify(mView, never()).setTitle(mMockContext.getString(R.string.user_list_api));
    }


    @Test
    public void handleUserListResponseWithIncorrectStatus() {
        GetUserListResponsePojo getUserListResponsePojo = new GetUserListResponsePojo();
        getUserListResponsePojo.setCode(400);
        mPresenter.handleUserListResponse(getUserListResponsePojo);
        verify(mView).hideLoader();
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_incorrect_response_code));
        verify(mView).showInfoView();
    }

    @Test
    public void handleTryAgainClicked() {
        mPresenterSpy.handleTryAgainClicked();
        verify(mPresenterSpy).fetchUserListFromApi();
    }

    @Test
    public void handleError() {
        mPresenterSpy.handleError();
        verify(mView).hideLoader();
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_something_went_wrong));
        verify(mView).showInfoView();
    }


    @Test
    public void isResponseValid_Correct() {
        GetUserListResponsePojo getUserListResponsePojo = mock(GetUserListResponsePojo.class);
        doReturn(1200).when(getUserListResponsePojo).getCode();
        Assert.assertTrue(mPresenter.isResponseValid(getUserListResponsePojo));
    }

    @Test
    public void isResponseValid_Incorrect() {
        GetUserListResponsePojo getUserListResponsePojo = mock(GetUserListResponsePojo.class);
        doReturn(400).when(getUserListResponsePojo).getCode();
        Assert.assertFalse(mPresenter.isResponseValid(getUserListResponsePojo));
    }

    @Test
    public void validateID_Null() {
        UserItem item = mock(UserItem.class);
        doReturn(null).when(item).getId();
        Assert.assertFalse(mPresenter.validateID(item));
    }

    @Test
    public void validateID_Correct() {
        UserItem item = mock(UserItem.class);
        doReturn(1).when(item).getId();
        Assert.assertTrue(mPresenter.validateID(item));
    }

    @Test
    public void checkNetwork() {
        Assert.assertEquals(mPresenterSpy.checkNetwork(), NetConnectionDetector.getInstance(mMockContext).isConntectingToInternet());
    }

}