package Client;

import Model.Post;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Profile implements Serializable {
    @Serial
    private static final long serialVersionUID = 19841212603990502L;
    private final String username;
    private String name;
    private String lastname;
    private String password;
    private LocalDate birthDate;
    private int followings = 0;
    private int followers = 0;
    private Gender gender;
    byte[] ProfilePhoto;
    public List<Post> posts=new ArrayList<>();
    public byte[] getProfilePhoto() {
        return ProfilePhoto;
    }
    public void setProfilePhoto(byte[] profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Profile(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Profile authenticate(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) return this;
        return null;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getFollowings() {
        return followings;
    }

    public int getFollowers() {
        return followers;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
