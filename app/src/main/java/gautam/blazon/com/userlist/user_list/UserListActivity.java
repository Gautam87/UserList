package gautam.blazon.com.userlist.user_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import javax.inject.Inject;

import gautam.blazon.com.userlist.R;

public class UserListActivity extends AppCompatActivity {

    private UserListFragment mFragment;

    @Inject
    public UserListActivity(Context context) {

    }

    public UserListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        if (savedInstanceState == null) {
            //activity is launched for the first time
            mFragment = new UserListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mFragment).commit();
        } else {
            // activity is launched second time
            mFragment = (UserListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        }
    }
}
