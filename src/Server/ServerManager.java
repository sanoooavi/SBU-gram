package Server;

import Client.Command;
import Client.Gender;
import Client.Profile;
import Model.Post;
import Whatever.Comment;
import Whatever.Message;
import Whatever.Time;

import java.util.*;

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
        ans.put("answer", Boolean.TRUE);
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
        for (Profile a : Server.users.get(username).getFollowings()) {
            if (!Server.users.get(username).getMute().contains(a)) {
                returnValue.addAll(a.getPosts());
            }
        }
        //Server.users.get(username).getFollowings().forEach(p -> returnValue.addAll(p.getPosts()));
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
        String usernameToAdd = (String) income.get("username");
        Profile profile = Server.users.get(usernameToAdd);
        Post post = (Post) income.get("Post");
        //user name of the publisher of this post
        String username = post.getPublisher();
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
        ans.put("answer", isGonnaLike);
        System.out.println(profile.getUsername() + " " + "like");
        System.out.println("message: " + post.getWriter() + " " + post.getTitle());
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> AddComment(Map<String, Object> income) {
        Comment comment = (Comment) income.get("Comment");
        Post post = (Post) income.get("Post");
        int index = Server.users.get(post.getPublisher()).getPosts().indexOf(post);
        Server.users.get(post.getPublisher()).getPosts().get(index).getComments().add(comment);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.AddComment);
        ans.put("answer", Boolean.TRUE);
        System.out.println(comment.toString());
        System.out.println("message: " + post.getTitle());
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> Follow(Map<String, Object> income) {
        String following = (String) income.get("following");
        String follower = (String) income.get("follower");
        Profile profTo = Server.users.get(following);
        Profile profFrom = Server.users.get(follower);
        Server.users.get(follower).getFollowings().add(profTo);
        DataManager.getInstance().updateDataBase();
        Server.users.get(following).getFollowers().add(profFrom);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.Follow);
        ans.put("answer", Boolean.TRUE);
        System.out.println(follower + " followed");
        System.out.println("message: " + following);
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> UnFollow(Map<String, Object> income) {
        String User = (String) income.get("User");
        String ToUnfollow = (String) income.get("ToUnfollow");
        Profile User_Profile = Server.users.get(User);
        Profile ToUnfollow_Profile = Server.users.get(ToUnfollow);
        boolean isNullProfile = (Server.users.get(User) == null || Server.users.get(ToUnfollow) == null);
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.UnFollow);
        ans.put("exists", !isNullProfile);
        if (isNullProfile) {
            return ans;
        }
        //when someone unfollow another person their likes will be removed or not?
        Server.users.get(ToUnfollow).getFollowers().remove(User_Profile);
        DataManager.getInstance().updateDataBase();
        Server.users.get(User).getFollowings().remove(ToUnfollow_Profile);
        DataManager.getInstance().updateDataBase();
        ans.put("answer", Boolean.TRUE);
        return ans;
    }

    public static Map<String, Object> Block(Map<String, Object> income) {
        String ToBlock = (String) income.get("ToBlock");
        String from = (String) income.get("from");
        Profile toBlock = Server.users.get(ToBlock);
        Server.users.get(from).getBlocked().add(toBlock);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.Block);
        ans.put("answer", Boolean.TRUE);
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> UnBlock(Map<String, Object> income) {
        String ToUnBlock = (String) income.get("ToUnBlock");
        String from = (String) income.get("from");
        Profile toUnBlock = Server.users.get(ToUnBlock);
        Server.users.get(from).getBlocked().remove(toUnBlock);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.UnBlock);
        ans.put("answer", Boolean.TRUE);
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> Mute(Map<String, Object> income) {
        String username = (String) income.get("userName");
        String usernameToMute = (String) income.get("usernameToMute");
        Profile ToMute = Server.users.get(usernameToMute);
        Server.users.get(username).getMute().add(ToMute);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.Mute);
        ans.put("answer", Boolean.TRUE);
        System.out.println(username + " muted ");
        System.out.println("message : " + usernameToMute);
        System.out.println("time: " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> UnMute(Map<String, Object> income) {
        String username = (String) income.get("userName");
        String usernameToUnMute = (String) income.get("usernameToUnMute");
        Profile ToUnMute = Server.users.get(usernameToUnMute);
        Server.users.get(username).getMute().remove(ToUnMute);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.UnMute);
        ans.put("answer", Boolean.TRUE);
        return ans;
    }

    public static Map<String, Object> GetInfo(Map<String, Object> income) {
        String userTarget = (String) income.get("userTarget");
        String username = (String) income.get("user");
        boolean isNullProfile = (Server.users.get(userTarget) == null);
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
        String username = (String) income.get("username");
        String email = (String) income.get("email");
        String newName = (String) income.get("newName");
        String newLastName = (String) income.get("newLastName");
        String phoneNumber = (String) income.get("phoneNumber");
        String location = (String) income.get("location");
        Gender gender = (Gender) income.get("gender");
        byte[] profilePhoto = (byte[]) income.get("profilePhoto");
        if (!newLastName.equals("null")) {
            Server.users.get(username).setLastname(newLastName);
        }
        if (!newName.equals("null")) {
            Server.users.get(username).setName(newName);
        }
        if (profilePhoto != null) {
            Server.users.get(username).setProfilePhoto(profilePhoto);
            for (Post a : Server.users.get(username).getPosts()) {
                a.setProfilePhoto(profilePhoto);
            }
        }
        Server.users.get(username).setEmail(email);
        Server.users.get(username).setPhoneNumber(phoneNumber);
        Server.users.get(username).setLocation(location);
        if (gender != null) {
            Server.users.get(username).setGender(gender);
        }
        DataManager.getInstance().updateDataBase(); // save to local file
        Map<String, Object> ans = new HashMap<>();
        Profile prof = Server.users.get(username);
        ans.put("command", Command.UpdateProfile);
        ans.put("answer", prof);
        System.out.println(username + " updated info");
        System.out.println("message: ");
        System.out.println("time : " + Time.getTime());
        return ans;
    }


    public static Map<String, Object> LoadingPersonalInfo(Map<String, Object> income) {
        String username = (String) income.get("username");
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LoadPersonalTimeLine);
        ArrayList<Post> returnValue = new ArrayList<>(Server.users.get(username).getPosts());
        ans.put("answer", returnValue);
        return ans;
    }

    public static Map<String, Object> GetPassword(Map<String, Object> income) {
        String username = (String) income.get("username");
        String writtenPassword = (String) income.get("text");
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.ForgotPassword);
        Boolean isNullProfile = (Server.users.get(username) == null);
        if (isNullProfile) {
            ans.put("answer", null);
            return ans;
        }
        String password = Server.users.get(username).getForgettablePassword();
        if (password == null || !password.equals(writtenPassword)) {
            ans.put("answer", null);
            return ans;
        }
        String realPassword = Server.users.get(username).getPassword();
        ans.put("answer", realPassword);
        return ans;
    }


    public static Map<String, Object> SaveThePassword(Map<String, Object> income) {
        String username = (String) income.get("username");
        String password = (String) income.get("password");
        Server.users.get(username).setForgettablePassword(password);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.SaveSecondPassword);
        ans.put("answer", Boolean.TRUE);
        return ans;

    }

    public static Map<String, Object> rePost(Map<String, Object> income) {
        Post MainPost = (Post) income.get("post");
        String username = (String) income.get("username");
        Profile profile = Server.users.get(username);
        Post newPost = new Post();
        newPost.setWriter(MainPost.getWriter());
        newPost.setDescription(MainPost.getDescription());
        newPost.setTitle(MainPost.getTitle());
        newPost.setPhoto(MainPost.getPhoto());
        newPost.setProfilePhoto(MainPost.getProfilePhoto());
        newPost.setTimeReleased(Time.getTime());
        newPost.setTimerMil(Time.getMilli());
        newPost.setPublisher(username);
        Server.users.get(username).getPosts().add(newPost);
        DataManager.getInstance().updateDataBase(); // save to local file
        int index = Server.users.get(MainPost.getWriter()).getPosts().indexOf(MainPost);
        Server.users.get(MainPost.getWriter()).getPosts().get(index).getRepost().add(profile);
        DataManager.getInstance().updateDataBase(); // save to local file
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.rePost);
        ans.put("answer", Boolean.TRUE);
        System.out.println(username + " rePost");
        System.out.println("message: " + newPost.getWriter() + " " + newPost.getTitle());
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> LoadingDirectInfo(Map<String, Object> income) {
        String username = (String) income.get("username");
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LoadUserDirect);
        ArrayList<Profile> returnValue = new ArrayList<>();
        returnValue.addAll(Server.users.values());
        ans.put("answer", returnValue);
        return ans;
    }

    public static Map<String, Object> SendMessage(Map<String, Object> income) {
        String sender = (String) income.get("usernameSender");
        String Receiver = (String) income.get("usernameReceiver");
        Message message = (Message) income.get("message");
        ArrayList<Object> messages = new ArrayList<>();
        if (Server.users.get(sender).getMessages() != null) {
            if (Server.users.get(sender).getMessages().containsKey(Receiver)) {
                messages.addAll(Server.users.get(sender).getMessages().get(Receiver));
            }
        }
        messages.add(message);
        Server.users.get(sender).getMessages().put(Receiver, messages);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.SendMessage);
        ans.put("answer", Boolean.TRUE);
        System.out.println(sender + " send");
        System.out.println("message: from " + sender + " to " + Receiver);
        System.out.println("time: " + Time.getTime());
        System.out.println(Receiver + " received");
        System.out.println("message: " + sender);
        System.out.println("time: " + Time.getTime());
        return ans;
    }

    public static Map<String, Object> LoadChatPage(Map<String, Object> income) {
        String username = (String) income.get("username");
        String chatWith = (String) income.get("chatWith");
        List<Object> returnValue = new ArrayList<>();
        if (Server.users.get(username).getMessages() != null) {
            if (Server.users.get(username).getMessages().containsKey(chatWith)) {
                returnValue.addAll(Server.users.get(username).getMessages().get(chatWith));
            }
        }
        if (Server.users.get(chatWith).getMessages() != null) {
            if (Server.users.get(chatWith).getMessages().containsKey(username)) {
                returnValue.addAll(Server.users.get(chatWith).getMessages().get(username));
            }
        }
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LoadChatPage);
        ans.put("answer", returnValue);
        return ans;
    }


    public static Map<String, Object> TrashText(Map<String, Object> income) {
        String username = (String) income.get("username");
        Message message = (Message) income.get("message");
        String Receiver = message.getReceiver();
        Server.users.get(username).getMessages().get(Receiver).remove(message);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        System.out.println("message deleted");
        ans.put("command", Command.TrashText);
        ans.put("answer", Boolean.TRUE);
        return ans;

    }

    public static Map<String, Object> ChangePassword(Map<String, Object> income) {
        String username = (String) income.get("username");
        String newPassword = (String) income.get("newPassword");
        String oldPassword = (String) income.get("oldPassword");
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.ChangePassword);
        if (!Server.users.get(username).getPassword().equals(oldPassword)) {
            ans.put("answer", Boolean.FALSE);
            return ans;
        }
        Server.users.get(username).setPassword(newPassword);
        DataManager.getInstance().updateDataBase();
        ans.put("answer", Boolean.TRUE);
        return ans;
    }


    public static Map<String, Object> GetProfile(Map<String, Object> income) {
        String username = (String) income.get("username");
        Profile prof = Server.users.get(username);
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.GetProfile);
        ans.put("answer", prof);
        return ans;
    }
}
