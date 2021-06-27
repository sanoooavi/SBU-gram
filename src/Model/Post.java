package Model;

import Client.Profile;
import Whatever.Comment;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * post class is a serializable so that can be written in file it also consists of 3 lists:likes/comments/repost
 * each list contains of different users but the comment is different each post has a publisher and writer and also
 * has a photo that we save it in an array of bytes and also has the profile photo of the person who wrote the post
 * In addition to the title and desc it save the time of the post releases so that can be sorted in the future
 * this class has just setter and getter and equal so there is nothing to talk about
 * it is obvious and you can get it by just taking a look
 * @author Me
 * @since idk
 */

public class Post implements Serializable {
    @Serial
    private static final long serialVersionUID = -2622370219466881429L;
    byte[] Photo;
    byte[] ProfilePhoto;
    private String writer;
    private String publisher;
    private String title;
    private String description;
    List<Profile> likes = new CopyOnWriteArrayList<>();
    List<Comment> comments = new CopyOnWriteArrayList<>();
    List<Profile>repost=new CopyOnWriteArrayList<>();
    private String timeReleased;
    private Long timerMil;

    public List<Profile> getRepost() {
        return repost;
    }

    public void setTimerMil(Long timerMil) {
        this.timerMil = timerMil;
    }

    public Long getTimerMil() {
        return timerMil;
    }

    public String getTimeReleased() {
        return timeReleased;
    }

    public void setTimeReleased(String timeReleased) {
        this.timeReleased = timeReleased;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public byte[] getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public List<Profile> getLikes() {
        return likes;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getCommentsOnField() {
        String str = "";
        for (int i = 0; i < getComments().size(); i++) {
            str += getComments().get(i);
        }
        return str;
    }

    //@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(publisher, post.publisher) && Objects.equals(title, post.title) && Objects.equals(description, post.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publisher, title, description);
    }
}
