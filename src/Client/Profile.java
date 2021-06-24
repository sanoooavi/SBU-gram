package Client;

import Model.Post;
import Whatever.Message;
import Whatever.Time;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Profile implements Serializable {
    @Serial
    private static final long serialVersionUID = 19841212603990502L;
    private final String username;
    private String name;
    private String lastname;
    private String password;
    private String ForgettablePassword = null;
    private LocalDate birthDate;
    private Gender gender;
    byte[] ProfilePhoto;
    String email = null;
    String phoneNumber = null;
    String location = null;
    public List<Post> posts = new ArrayList<>();
    public List<Profile> followings = new CopyOnWriteArrayList<>();
    public List<Profile> followers = new CopyOnWriteArrayList<>();
    public List<Profile> Blocked = new CopyOnWriteArrayList<>();
    public List<Profile> Mute = new CopyOnWriteArrayList<>();
    public Map<String, List<Message>> Messages = new ConcurrentHashMap<>();
    public Map<String, Message> lastMessage = new ConcurrentHashMap<>();
    public Map<String, Integer> NotSeen = new ConcurrentHashMap<>();

    public Profile(String username) {
        this.username = username;
    }

    public Map<String, Message> getLastMessage() {
        return lastMessage;
    }

    public Map<String, Integer> getNotSeen() {
        return NotSeen;
    }

    public Profile authenticate(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) return this;
        return null;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public void setForgettablePassword(String forgettablePassword) {
        ForgettablePassword = forgettablePassword;
    }


    public List<Profile> getFollowers() {
        return followers;
    }

    public List<Profile> getFollowings() {
        return followings;
    }


    public Map<String, List<Message>> getMessages() {
        return Messages;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public byte[] getProfilePhoto() {
        return ProfilePhoto;
    }

    public Gender getGender() {
        return gender;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
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

    public Integer getAge() {
        return LocalDateTime.now().getYear() - birthDate.getYear();
    }

    public List<Profile> getBlocked() {
        return Blocked;
    }

    public String getForgettablePassword() {
        return ForgettablePassword;
    }

    public List<Profile> getMute() {
        return Mute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile)) return false;
        Profile profile = (Profile) o;
        return Objects.equals(username, profile.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public List<Post> getPosts() {
        return posts;
    }
}
