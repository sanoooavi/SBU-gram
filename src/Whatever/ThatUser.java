package Whatever;

import Client.Profile;
import Model.Post;
import java.util.List;

/**
 * that user is a simple class which save the other user you are following during this program
 */
public class ThatUser {

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
