package gautam.blazon.com.userlist.user_list;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.marcoscg.infoview.InfoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gautam.blazon.com.userlist.R;
import gautam.blazon.com.userlist.adapters.UserListAdapter;
import gautam.blazon.com.userlist.data.model.UserItem;
import gautam.blazon.com.userlist.utils.ClickListener;
import gautam.blazon.com.userlist.utils.RecyclerTouchListener;

public class UserListFragment extends Fragment implements UserListContract.View, UserListAdapter.OnItemInteractionListener {

    @BindView(R.id.info_view)
    InfoView infoView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private static final String TAG = UserListFragment.class.getName();
    private UserListPresenter mUserListPresenter;
    private UserListAdapter mUserListAdapter;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUserListPresenter = new UserListPresenter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, view);
        mUserListPresenter.attachView(this);
        infoView.setIconDrawable(getResources().getDrawable(R.drawable.ic_exclamation));
        infoView.setOnTryAgainClickListener(new InfoView.OnTryAgainClickListener() {
            @Override
            public void onTryAgainClick() {
                tryAgainClicked();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) { }

            @Override
            public void onLongClick(View view, int position) { }
        }));

        mUserListPresenter.checkUserListInDb();
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
        infoView.setProgress(true);
    }

    @Override
    public void hideLoader() {
        infoView.setProgress(false);
    }

    @Override
    public void showUserList(List<UserItem> userItems) {
        mUserListAdapter = new UserListAdapter(getActivity(), userItems, this);
        recyclerView.setAdapter(mUserListAdapter);

    }

    @Override
    public void showInfoView() {
        infoView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setInfoViewMessage(String message) {
        infoView.setMessage(message);
    }

    @Override
    public void hideInfoView() {
        infoView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setTitle(String title) {
        if (getActivity() != null ) {
            getActivity().setTitle(title);
        }
    }

    @Override
    public void showSnackBar(String message) {
        if (getActivity() != null) {
            Snackbar.make(getActivity().findViewById(android.R.id.content),
                    message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void tryAgainClicked() {
        mUserListPresenter.fetchUserListFromApi();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                tryAgainClicked();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


