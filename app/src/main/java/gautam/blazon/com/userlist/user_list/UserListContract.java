package gautam.blazon.com.userlist.user_list;

import java.util.List;

import gautam.blazon.com.userlist.base.MvpView;
import gautam.blazon.com.userlist.data.model.GetUserListResponsePojo;
import gautam.blazon.com.userlist.data.model.UserItem;

public class UserListContract {

    public interface View extends MvpView {

        void checkAndAskPermissions();

        void showLoader();

        void hideLoader();

        void showUserList(List<UserItem> userItems);

        void showInfoView();

        void hideInfoView();

        void setTitle(String title);
    }

    interface Presenter {

        void handlePermissionsAllowed();

        void handlePermissionsDenied();

        void checkUserListInDb();

        void handleEmptyDb();

        void handleUserListFromDb(List<UserItem> userItems);

        void fetchUserListFromApi();

        void handleUserListResponse(GetUserListResponsePojo getUserListResponsePojo);

        void validateID(UserItem userItem);

    }
}
