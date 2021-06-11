package Server;

import Client.Command;
import Client.Profile;
import Model.Post;
import Whatever.Comment;
import Whatever.Time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerManager {
    public static Map<String, Object> signUp(Map<String, Object> income) {
        Profile newProfile = (Profile) income.get("profile");
        String username = newProfile.getUsername();
        Server.users.put(username, newProfile);
        DataManager.getInstance().updateDataBase(); // save to local file
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.SignUp);
        ans.put("answer", true);
        System.out.println(newProfile.getUsername() + " register" /* + TODO */); //add image address
        System.out.println("time : " + Time.getTime());
        System.out.println(newProfile.getUsername() + " sign_in");
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    /**
     * login  in server side
     * it is given username and password from income map and check if the username Exists
     * if the username doesn't exist it return null profile with the exists boolean : false
     * if the username exists, the exists boolean is true
     * then it use profile.authenticate to make sure that the username and password match ( authenticate could be changed later for improve security)
     * if the username and Password math, we return profile (which is used as token)
     * otherwise we return null as profile
     **/
    public static Map<String, Object> login(Map<String, Object> income) {
        String username = (String) income.get("username");
        String password = (String) income.get("password");
        Boolean isNullProfile = (Server.users.get(username) == null);
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.Login);
        ans.put("exists", !isNullProfile);
        if (isNullProfile) {
            return ans;
        }
        Profile profile = Server.users.get(username).authenticate(username, password);
        ans.put("answer", profile);
        if (profile != null) {
            System.out.println(profile.getUsername() + " logged in");
            System.out.println("time : " + Time.getTime());
        }
        return ans;
    }

    public static Map<String, Object> UserNameExists(Map<String, Object> income) {
        String usernameCheck = (String) income.get("username");
        Profile profile = Server.users.get(usernameCheck);
        Boolean exists = (profile != null);
        Map<String, Object> ans = new HashMap<>();
        ans.put("answer", exists);
        ans.put("command", Command.Username_unique);
        return ans;
    }

    public static Map<String, Object> PublishPost(Map<String, Object> income) {
        Post post = (Post) income.get("post");
        String username = (String) income.get("username");
        Server.users.get(username).getPosts().add(post);
        DataManager.getInstance().updateDataBase(); // save to local file
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.Publish_Post);
        ans.put("answer", new Boolean(true));
        System.out.println(post.getWriter() + " publish");
        System.out.println("message: " + post.getTitle() + " " + post.getWriter());
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> LoadTimeLine(Map<String, Object> income) {
        String username = (String) income.get("username");
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LoadTimeLine);
        ArrayList<Post> returnValue = new ArrayList<>();
        Server.users.get(username)
                .getFollowings()
                .forEach(p -> returnValue.addAll(p.getPosts()));
        returnValue.addAll(Server.users.get(username).getPosts());
        ans.put("answer", returnValue);
        return ans;
    }

    public static Map<String, Object> LoadingTable(Map<String, Object> income) {
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LoadTimeLine);
        ans.put("answer", new ArrayList<>(Server.users.values()));
        return ans;
    }

    public static Map<String, Object> LikePost(Map<String, Object> income) {
        boolean isGonnaLike = true;
        //Profile of the person who wants to like the post
        Profile profile = (Profile) income.get("Profile");
        Post post = (Post) income.get("Post");
        //user name of the publisher of this post
        String username = post.getWriter();
        if (Server.users.containsKey(username) && Server.users.containsValue(profile)) {
            int index = Server.users.get(username).getPosts().indexOf(post);
            //if the boolean becomes false it means this user didn't like this post before
            isGonnaLike = ((Server.users.get(username)).getPosts()).get(index).getLikes().contains(profile);
            if (!isGonnaLike) {
                Server.users.get(username).getPosts().get(index).getLikes().add(profile);
                DataManager.getInstance().updateDataBase();
            }
        }
        //if the boolean is true it means this user has liked this post before or has deleted account
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LikePost);
        ans.put("answer", new Boolean(isGonnaLike));
        System.out.println(profile.getUsername() + " " + "like");
        System.out.println("message: " + post.getWriter() + " " + post.getTitle());
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> AddComment(Map<String, Object> income) {
        Comment comment = (Comment) income.get("Comment");
        Post post = (Post) income.get("Post");
        int index = Server.users.get(post.getWriter()).getPosts().indexOf(post);
        Server.users.get(post.getWriter()).getPosts().get(index).getComments().add(comment);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.AddComment);
        ans.put("answer", new Boolean(true));
        System.out.println(comment.toString());
        System.out.println("message: " + post.getTitle());
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> Follow(Map<String, Object> income) {
        Profile following = (Profile) income.get("following");
        Profile follower = (Profile) income.get("follower");
        Server.users.get(follower.getUsername()).getFollowings().add(following);
        DataManager.getInstance().updateDataBase();
        Server.users.get(following.getUsername()).getFollowers().add(follower);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.Follow);
        ans.put("answer", new Boolean(true));
        return ans;
    }

    public static Map<String, Object> UnFollow(Map<String, Object> income) {
        Profile User = (Profile) income.get("User");
        Profile ToUnfollow = (Profile) income.get("ToUnfollow");
        Boolean isNullProfile = (Server.users.get(User.getUsername()) == null||Server.users.get(ToUnfollow.getUsername()) == null);
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.UnFollow);
        ans.put("exists", !isNullProfile);
        if (isNullProfile) {
            return ans;
        }
        Server.users.get(ToUnfollow.getUsername()).getFollowers().remove(User);
        Server.users.get(User.getUsername()).getFollowings().remove(ToUnfollow);
        DataManager.getInstance().updateDataBase();
        ans.put("answer", new Boolean(true));
        return ans;
    }

    public static Map<String, Object> GetInfo(Map<String, Object> income) {
        String userTarget = (String) income.get("userTarget");
        String username = (String) income.get("user");
        Boolean isNullProfile = (Server.users.get(userTarget) == null);
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.GetInfo);
        ans.put("exists", !isNullProfile);
        if (isNullProfile) {
            return ans;
        }
        Profile profile = Server.users.get(userTarget);
        ans.put("answer", profile);
        System.out.println(username + " get info " + userTarget);
        System.out.println("message: " + userTarget);
        System.out.println("time : " + Time.getTime());
        return ans;
    }


    public static Map<String, Object> UpdateProfile(Map<String, Object> income) {
        String username=(String) income.get("username");
        String email=(String) income.get("email");
        String newName=(String)income.get("newName");
        String newLastName=(String)income.get("newLastName");
        String phoneNumber=(String)income.get("phoneNumber");
        String location=(String)income.get("location");
        return null;
    }
}
