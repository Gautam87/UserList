package gautam.blazon.com.userlist.user_list;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gautam.blazon.com.userlist.R;
import gautam.blazon.com.userlist.data.model.GetUserListResponsePojo;
import gautam.blazon.com.userlist.data.model.UserItem;
import gautam.blazon.com.userlist.utils.NetConnectionDetector;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
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

    @Test
    public void handlePermissionsAllowedAndNetworkAvailable() {
        mPresenter.handlePermissionsAllowed();
        when(mPresenterSpy.checkNetwork()).thenReturn(true);
        verify(mView).callApi();
    }

    @Test
    public void handlePermissionsAllowedAndNetworkNotAvailable() {
        mPresenter.handlePermissionsAllowed();
        when(mPresenterSpy.checkNetwork()).thenReturn(false);
        verify(mView).showInfoView();
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_network_unavailable));
    }

    @Test
    public void handlePermissionsDenied() {
        mPresenter.handlePermissionsDenied();
        verify(mView).showSnackBar(mMockContext.getString(R.string.error_storage_permissions_denied));
        verify(mView).callApi();
    }

    @Test
    public void checkUserListInDbWithoutContents() {
        mPresenter.checkUserListInDb();
        List<UserItem> userItems = new ArrayList<>();
        if(userItems.size()==0){
            verify(mPresenterSpy).handleEmptyDb();
        }
    }

    @Test
    public void checkUserListInDbWithContents() {
        mPresenter.checkUserListInDb();
        List<UserItem> userItems = new ArrayList<>();
        userItems.add(new UserItem());
        if(userItems.size()>0){
            verify(mPresenterSpy).handleUserListFromDb(anyListOf(UserItem.class));
        }
    }

    @Test
    public void handleEmptyDb() {
        mPresenter.handleEmptyDb();
        verify(mView).showInfoView();
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_no_users_in_db));
    }

    @Test
    public void handleUserListFromDb() {
        mPresenter.checkUserListInDb();
        List<UserItem> userItems = new ArrayList<>();
        userItems.add(new UserItem());
        mPresenter.handleUserListFromDb(userItems);
        verify(mView).showUserList(anyListOf(UserItem.class));
    }

    @Test
    public void handleUserListResponseWithCorrectStatusAndEmptyList() {
        GetUserListResponsePojo getUserListResponsePojo = new GetUserListResponsePojo();
        getUserListResponsePojo.setCode(1200);
        mPresenter.handleUserListResponse(getUserListResponsePojo);
        verify(mView).setInfoViewMessage(mMockContext.getString(R.string.error_no_users));
        verify(mView).showInfoView();
    }

    @Test
    public void handleUserListResponseWithCorrectStatusAndNormalList() {
        GetUserListResponsePojo getUserListResponsePojo = new GetUserListResponsePojo();
        getUserListResponsePojo.setCode(1200);
        mPresenter.handleUserListResponse(getUserListResponsePojo);
        verify(mView).showUserList(Mockito.anyListOf(UserItem.class));
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
        mPresenter.handleTryAgainClicked();
        verify(mPresenterSpy).fetchUserListFromApi();
    }
}