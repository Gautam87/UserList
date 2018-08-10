package gautam.blazon.com.userlist.base;

public interface BaseView<T> {
    void setPresenter(T presenter);

    /**
     * Show progress bar dialog
     */
    void showComplete();
}
