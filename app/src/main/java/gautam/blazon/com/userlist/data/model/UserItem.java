package gautam.blazon.com.userlist.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import gautam.blazon.com.userlist.UserListDatabase;

@Table(database = UserListDatabase.class)
public class UserItem {

    @Column
    @PrimaryKey(autoincrement = true)
    @SerializedName("id")
    @Expose
    private int id;

    @Column
    @SerializedName("name")
    @Expose
    private String name;

    @Column
    @SerializedName("skills")
    @Expose
    private String skills;

    @Column
    @SerializedName("image")
    @Expose
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}