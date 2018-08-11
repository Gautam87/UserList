package gautam.blazon.com.userlist.data.remote;

import gautam.blazon.com.userlist.data.model.GetUserListResponsePojo;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserListWebService {

    @GET("/")
    Observable<GetUserListResponsePojo> getUserList();
}
