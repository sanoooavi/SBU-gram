package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Post implements Serializable {
   @Serial
    private static final long serialVersionUID = -2622370219466881429L;
    byte[] Photo;
    private String writer;
    private  String title;
    private  String description;

    public void setPhoto(byte[] photo) {
        Photo = photo;
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

    @Override
    public String toString() {
        return "Post{" + "writer='" + writer + '\'' + ", title='" + title + '\'' + ", description='" + description + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return writer.equals(post.writer) && title.equals(post.title) && description.equals(post.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer, title, description);
    }
}
