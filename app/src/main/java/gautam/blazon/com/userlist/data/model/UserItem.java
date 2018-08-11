package gautam.blazon.com.userlist.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import gautam.blazon.com.userlist.UserListDatabase;

@Table(database = UserListDatabase.class)
public class UserItem extends BaseModel{

    @Column(getterName = "getIdLocal")
    @PrimaryKey(autoincrement = true)
    private transient int idLocal;

    @Column
    @SerializedName("id")
    @Expose
    private Integer id;

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

    public UserItem(Integer id, String name, String skills, String image) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.image = image;
    }

    public UserItem() {
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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