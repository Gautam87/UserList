package gautam.blazon.com.userlist.user_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcoscg.infoview.InfoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gautam.blazon.com.userlist.R;
import gautam.blazon.com.userlist.data.model.UserItem;

public class UserListFragment extends Fragment implements UserListContract.View {

    @BindView(R.id.info_view)
    InfoView infoView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private static final String TAG = UserListFragment.class.getName();
    private UserListPresenter mUserListPresenter;
    private boolean isPermissionGranted;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserListPresenter = new UserListPresenter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, view);
        mUserListPresenter.attachView(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //avoid fragment to be destroyed on screen rotation
        setRetainInstance(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserListPresenter.detachView();
    }

    @Override
    public void showComplete() {
    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showUserList(List<UserItem> userItems) {

    }

    @Override
    public void showInfoView() {

    }

    @Override
    public void setInfoViewMessage(String message) {

    }

    @Override
    public void hideInfoView() {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void callApi() {
    }

    @Override
    public void showSnackBar(String message) {

    }

    @Override
    public void tryAgainClicked() {

    }

}


