package Model;

import Client.Profile;
import Whatever.Comment;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Post implements Serializable {
   @Serial
    private static final long serialVersionUID = -2622370219466881429L;
    byte[] Photo;
    private String writer;
    private  String title;
    private  String description;
    List<Profile> likes= new CopyOnWriteArrayList<>();
    List<Comment>comments=new CopyOnWriteArrayList<>();
    public void setPhoto(byte[] photo) {
        Photo = photo;
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
    public String getCommentsOnField(){
        String str="";
        for (int i=0;i<getComments().size();i++){
            str+=getComments().get(i);
        }
        return str;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(writer, post.writer) && Objects.equals(title, post.title) && Objects.equals(description, post.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer, title, description);
    }
}
