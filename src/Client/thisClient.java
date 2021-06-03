package Client;

import Model.Post;
import java.util.List;

public class thisClient{
    public static Profile profile;
    public static void setProfile(Profile justCreatedProfile) {
        profile=justCreatedProfile;
    }
    public static Profile getProfile(){
        return profile;
    }
    public static String getUserName(){
        return profile.getUsername();
    }
    public static List<Post> getPosts(){
        return profile.posts;
    }


}
