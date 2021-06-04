package Server;

import Client.Command;
import Client.Profile;
import Model.Post;
import Whatever.Time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class ServerManager {
    public static Map<String,Object> signUp(Map<String,Object> income){
        Profile newProfile = (Profile) income.get("profile");
        String username = newProfile.getUsername();
        Server.users.put(username,newProfile);
        DataManager.getInstance().updateDataBase(); // save to local file
        Map<String,Object> ans = new HashMap<>();
        ans.put("command",Command.SignUp);
        ans.put("answer",true);
        System.out.println(newProfile.getUsername() + " register" /* + TODO */); //add image address
        System.out.println("time : "+Time.getTime());
        System.out.println(newProfile.getUsername() + " sign_in");
        System.out.println("time : "+Time.getTime());
        return ans;
    }
    /**
     login  in server side
     it is given username and password from income map and check if the username Exists
     if the username doesn't exist it return null profile with the exists boolean : false
     if the username exists, the exists boolean is true
     then it use profile.authenticate to make sure that the username and password match ( authenticate could be changed later for improve security)
     if the username and Password math, we return profile (which is used as token)
     otherwise we return null as profile
     **/
    public static Map<String,Object> login(Map<String,Object> income){

        String username = (String) income.get("username");
        String password = (String) income.get("password");

        Boolean isNullProfile = (Server.users.get(username) == null);
        Map<String,Object> ans = new HashMap<>();
        ans.put("command",Command.Login);
        ans.put("exists",!isNullProfile);
        if(isNullProfile){
            return ans;
        }
        Profile profile = Server.users.get(username).authenticate(username, password);
        ans.put("answer",profile);

        if(profile != null){
            System.out.println(profile.getUsername() + " logged in");
            System.out.println("time : "+ Time.getTime());
        }
        return ans;
    }

    public static Map<String, Object> UserNameExists(Map<String, Object> income) {
        String usernameCheck = (String) income.get("username");
        Profile profile = Server.users.get(usernameCheck);
        Boolean exists = (profile != null);
        Map<String,Object> ans = new HashMap<>();
        ans.put("answer",exists);
        ans.put("command",Command.Username_unique);
        return ans;
    }

    public static Map<String, Object> PublishPost(Map<String, Object> income) {
        Post post = (Post) income.get("post");
        String username= (String) income.get("username");
        Server.users.get(username).getPosts().add(post);
        DataManager.getInstance().updateDataBase(); // save to local file
        Map<String,Object> ans = new HashMap<>();
        ans.put("command",Command.Publish_Post);
        ans.put("answer",new Boolean(true));
        System.out.println(post.getWriter() + " publish");
        System.out.println("message: "+ post.getTitle() + " "+post.getWriter());
        System.out.println("time : "+Time.getTime());
        return ans;
    }

    public static Map<String, Object> LoadTimeLine(Map<String, Object> income) {
        String username= (String) income.get("username");
        Map<String,Object> ans = new HashMap<>();
        ans.put("command",Command.LoadTimeLine);
        ans.put("answer",new ArrayList<>(Server.users.get(username).getPosts()));
        return ans;
    }

    public static Map<String, Object> LoadingTable(Map<String, Object> income) {
        Map<String,Object> ans = new HashMap<>();
        ans.put("command",Command.LoadTimeLine);
        ans.put("answer",new ArrayList<>(Server.users.values()));
        return ans;
    }
}
