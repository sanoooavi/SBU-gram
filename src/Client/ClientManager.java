package Client;

import Model.Post;
import Whatever.Comment;
import Whatever.ThatUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientManager {
    public static Boolean signUp(Profile profile) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.SignUp);
        toSend.put("profile", profile);
        Map<String, Object> received = Network.serve(toSend);
        //if ( received.get("answer") == null ) return null;
        return (Boolean) received.get("answer");
    }

    public static Profile login(String username, String password) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.Login);
        toSend.put("username", username);
        toSend.put("password", password);
        Map<String, Object> received = Network.serve(toSend);
        if (received.get("answer") == null) return null;
        return (Profile) received.get("answer");
    }

    public static boolean isUserNameExists(String usernamecheck) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.Username_unique);
        toSend.put("username", usernamecheck);
        Map<String, Object> recieved = Network.serve(toSend);
        return (boolean) recieved.get("answer");
    }

    public static void PublishPost(Post post) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.Publish_Post);
        toSend.put("username", post.getPublisher());
        toSend.put("post", post);
        Network.serve(toSend);
    }

    public static List<Post> LoadingInfo() {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.LoadTimeLine);
        toSend.put("username", thisClient.getUserName());
        Map<String, Object> received = Network.serve(toSend);
        if (received.get("answer") == null) return null;
        return (List<Post>) received.get("answer");
    }

    public static List<Profile> LoadingTable() {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.LoadSearchTable);
        Map<String, Object> received = Network.serve(toSend);
        if (received.get("answer") == null) return null;
        return (List<Profile>) received.get("answer");
    }

    public static boolean LikePost(Profile profile, Post post) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.LikePost);
        toSend.put("Profile", profile);
        toSend.put("Post", post);
        Map<String, Object> received = Network.serve(toSend);
        return (boolean) received.get("answer");
    }

    public static void AddComment(Comment comment, Post post) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.AddComment);
        toSend.put("Comment", comment);
        toSend.put("Post", post);
        Network.serve(toSend);
    }



    public static Profile getInfo(String userTarget, String userName) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.GetInfo);
        toSend.put("user", userName);
        toSend.put("userTarget", userTarget);
        Map<String, Object> received =Network.serve(toSend);
        if (received.get("answer") == null) return null;
        return (Profile) received.get("answer");

    }
    public static Boolean follow(String followingUserName, String followerUserName) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.Follow);
        //the one who you want to follow
        toSend.put("following",followingUserName);
        //your profile
        toSend.put("follower", followerUserName);
        Map<String, Object> received =Network.serve(toSend);
        if (received.get("answer") == null) return null;
        return (Boolean) received.get("answer");
    }

    public static Boolean Unfollow(String profile,String profile1) {
        Map<String ,Object>toSend=new HashMap<>();
        toSend.put("command",Command.UnFollow);
        toSend.put("ToUnfollow",profile);
        toSend.put("User",profile1);
        Map<String, Object> received =Network.serve(toSend);
        if (received.get("answer") == null) return null;
        return (Boolean) received.get("answer");
    }

    public static Profile UpdateProfile(String userName, String email, String newName, String newLastName, String phoneNumber, String location, Gender gender, byte[] bytes) {
        Map<String,Object>toSend=new HashMap<>();
        toSend.put("command",Command.UpdateProfile);
        toSend.put("username",userName);
        toSend.put("email",email);
        toSend.put("newName",newName);
        toSend.put("newLastName",newLastName);
        toSend.put("phoneNumber",phoneNumber);
        toSend.put("location",location);
        toSend.put("gender",gender);
        toSend.put("profilePhoto",bytes);
        Map<String, Object> received =Network.serve(toSend);
        return (Profile) received.get("answer");
    }

    public static void rePost(Post post, String userName) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.rePost);
        toSend.put("username", userName);
        toSend.put("post", post);
        Network.serve(toSend);
    }

    public static List<Post> LoadingPersonalInfo() {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.LoadPersonalTimeLine);
        toSend.put("username", ThatUser.getUserName());
        Map<String, Object> received = Network.serve(toSend);
        if (received.get("answer") == null) return null;
        return (List<Post>) received.get("answer");
    }

    public static boolean SaveTheSecondPassword(String userName, String text) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.UrgentPassword);
        toSend.put("username", userName);
        toSend.put("text", text);
        Map<String, Object> received = Network.serve(toSend);
        return (boolean) received.get("answer");
    }

    public static String GetThePassword(String password, String userName) {
        Map<String, Object> toSend = new HashMap<>();
        toSend.put("command", Command.ForgotPassword);
        toSend.put("username", userName);
        toSend.put("text",password);
        Map<String, Object> received = Network.serve(toSend);
        if (received.get("answer") == null) return null;
        return (String) received.get("answer");
    }
}
