package Client;

import Model.Post;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Profile implements Serializable {
    @Serial
    private static final long serialVersionUID = 19841212603990502L;
    private final String username;
    private String name;
    private String lastname;
    private String password;
    private LocalDate birthDate;
    private Integer age;
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

    public Integer getAge(){
        this.age= LocalDateTime.now().getYear()-birthDate.getYear();
        return this.age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile)) return false;
        Profile profile = (Profile) o;
        return Objects.equals(username, profile.username) && Objects.equals(name, profile.name) && Objects.equals(lastname, profile.lastname) && Objects.equals(password, profile.password) && Objects.equals(birthDate, profile.birthDate) && Objects.equals(age, profile.age) && gender == profile.gender && Arrays.equals(ProfilePhoto, profile.ProfilePhoto) && Objects.equals(posts, profile.posts);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username, name, lastname, password, birthDate, age, gender, posts);
        result = 31 * result + Arrays.hashCode(ProfilePhoto);
        return result;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
