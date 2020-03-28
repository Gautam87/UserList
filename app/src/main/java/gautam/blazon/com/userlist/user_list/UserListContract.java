package gautam.blazon.com.userlist.user_list;

import java.util.List;

import gautam.blazon.com.userlist.base.MvpView;
import gautam.blazon.com.userlist.data.model.GetUserListResponsePojo;
import gautam.blazon.com.userlist.data.model.UserItem;

public class UserListContract {

    public interface View extends MvpView {

        void showLoader();

        void hideLoader();

        void showUserList(List<UserItem> userItems);

        void showInfoView();

        void setInfoViewMessage(String message);

        void hideInfoView();

        void setTitle(String title);

        void showSnackBar(String message);

        void tryAgainClicked();
    }

    public interface Presenter {

        void checkUserListInDb();

        void handleEmptyDb();

        void handleUserListFromDb(List<UserItem> userItems);

        void fetchUserListFromApi();

        void handleUserListResponse(GetUserListResponsePojo getUserListResponsePojo);

        boolean validateID(UserItem userItem);

        boolean checkNetwork();

        void handleNetworkAvailable();

        void handleNetworkNotAvailable();

        boolean isResponseValid(GetUserListResponsePojo getUserListResponsePojo);

        void handleTryAgainClicked();

        void handleError();

    }
}
