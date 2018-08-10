package gautam.blazon.com.userlist;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = UserListDatabase.NAME, version = UserListDatabase.VERSION)
public class UserListDatabase {
    public static final String NAME = "UserListDatabase";

    public static final int VERSION = 1;
}