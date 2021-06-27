package Server;

import Client.Command;
import Client.Gender;
import Client.Profile;
import Model.Post;
import Whatever.Comment;
import Whatever.Message;
import Whatever.TextMessage;
import Whatever.Time;

import java.util.*;

/**
 * server manager class is the mossssssssst important class in this project
 * it makes the commands to happen and save them all in our data in has the access to our data and
 * there is no other class which can change our data directly
 */

public class ServerManager {
    public static Comparator<Message> timeCompare = (a, b) -> -1 * Long.compare(a.getTimeMilli(), b.getTimeMilli());

    /**
     * when a new profile creates the server should put this user and it's profile in to the map
     * and also the data manager should save the new profile
     * @param income the new profile that just signed-up
     * @return
     */
    public static Map<String, Object> signUp(Map<String, Object> income) {
        Profile newProfile = (Profile) income.get("profile");
        String username = newProfile.getUsername();
        Server.users.put(username, newProfile);
        DataManager.getInstance().updateDataBase(); // save to local file
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.SignUp);
        ans.put("answer", true);
        System.out.println(newProfile.getUsername() + " connected to server");
        System.out.println("time : " + Time.getTime());
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
            System.out.println(profile.getUsername() + " connected to server");
            System.out.println(profile.getUsername() + " logged in");
            System.out.println("time : " + Time.getTime());
        }
        return ans;
    }

    /**
     * it is of the most useful methods which searches in the users list to see  a new member of this app
     * chooses a right username and doesn't let him/her choose repeated username
     * @param income the username
     * @return if you can continue or should change the username
     */

    public static Map<String, Object> UserNameExists(Map<String, Object> income) {
        String usernameCheck = (String) income.get("username");
        Profile profile = Server.users.get(usernameCheck);
        Boolean exists = (profile != null);
        Map<String, Object> ans = new HashMap<>();
        ans.put("answer", exists);
        ans.put("command", Command.Username_unique);
        return ans;
    }

    /**also each profile has a list of posts that when you publish a post you should first
     * add this post to you array and tell the data manager to save all the info
     * @param income the new post and the publisher
     * @return nothing really!:/
     */

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

    /**
     * in order to see what happened to your followings when you open your time line you can see
     * a list view of all the posts of yours and your following that are sorted in time
     * @param income the username who logged in
     * @return list of posts that is waiting to see
     */

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
        System.out.println(username + " get post list");
        System.out.println("time : " + Time.getTime());
        returnValue.addAll(Server.users.get(username).getPosts());
        ans.put("answer", returnValue);
        return ans;
    }

    /**
     * this method is used when you are looking for some one this app shows all the users
     * that has an account and helps you find some one by name or age
     * @param income not used
     * @return all the users
     */
    public static Map<String, Object> LoadingTable(Map<String, Object> income) {
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LoadTimeLine);
        ans.put("answer", new ArrayList<>(Server.users.values()));
        return ans;
    }

    /**
     * if you liked a post before this appears as dislike and decreases the amount of likes
     * but if it's not so there is an increasment and yor profile is added to this post's liked list
     * @param income your username and the post you want to like or dislike
     * @return the bool which show what to do
     */

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
            } else {
                Server.users.get(username).getPosts().get(index).getLikes().remove(profile);
                DataManager.getInstance().updateDataBase();
            }
        }
        //if the boolean is true it means this user has liked this post before or has deleted account
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LikePost);
        ans.put("answer", isGonnaLike);
        System.out.println(usernameToAdd + " like");
        System.out.println("message: " + post.getWriter() + " " + post.getTitle());
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    /**
     * when you want to add comment to a post there is no limit this is a simple method
     * with no condition
     * @param income the comment and the post we are gonna add the comment to
     * @return nothing useful like always
     */

    public static Map<String, Object> AddComment(Map<String, Object> income) {
        Comment comment = (Comment) income.get("Comment");
        Post post = (Post) income.get("Post");
        int index = Server.users.get(post.getPublisher()).getPosts().indexOf(post);
        Server.users.get(post.getPublisher()).getPosts().get(index).getComments().add(comment);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.AddComment);
        ans.put("answer", Boolean.TRUE);
        System.out.println(comment.toString() + " commented");
        System.out.println("message: " + post.getTitle());
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    /**
     * by following a user his/her post will be shown in your time line and also there is an increase
     * in your following and in that user's followers
     * @param income both sides username
     * @return
     */

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

    /**
     * it just works the opposite of follow and removes the user from your following list
     * and also removes you from that user's follower list
     * and saves the changes in data so there again you can follow the user
     * @param income both sides username
     * @return
     */

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

    /**
     * when you block a user that user can not send you message or can not even visit your profile so
     * until you have the user in your list that user can not communicate with u and
     * @param income both sides username
     * @return just that the command was accepted boolean.true
     */

    public static Map<String, Object> Block(Map<String, Object> income) {
        String ToBlock = (String) income.get("ToBlock");
        String from = (String) income.get("from");
        Profile toBlock = Server.users.get(ToBlock);
        Server.users.get(from).getBlocked().add(toBlock);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.Block);
        ans.put("answer", Boolean.TRUE);
        System.out.println(from + " blocked ");
        System.out.println("message : " + ToBlock);
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    /**
     * works the opposite of the block so you remove some one from your blocked list
     * then you can chat or see each other's post
     * but if that user has followed you in the past and then you blocked her it looks like
     * that she unfollowed you too so you need to follow her if you want
     * @param income both sides username
     * @return nothing useful
     */

    public static Map<String, Object> UnBlock(Map<String, Object> income) {
        String ToUnBlock = (String) income.get("ToUnBlock");
        String from = (String) income.get("from");
        Profile toUnBlock = Server.users.get(ToUnBlock);
        Server.users.get(from).getBlocked().remove(toUnBlock);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.UnBlock);
        ans.put("answer", Boolean.TRUE);
        System.out.println(from + " Unblocked ");
        System.out.println("message : " + ToUnBlock);
        System.out.println("time : " + Time.getTime());
        return ans;
    }

    /**
     * mute method doesn't give the permission the other person's post to be shown
     * in your time line but this doesn't mean that there is change in follower and following
     * and it is different from block
     * @param income your username and also the person who u want to mute
     * @return the command was accepted
     */

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

    /**
     * it just removes the person from your muted list and so you can see her/his posts in your timeline
     * and then saves the changes in data manager
     * @param income your username and the person you want to un mute
     * @return
     */

    public static Map<String, Object> UnMute(Map<String, Object> income) {
        String username = (String) income.get("userName");
        String usernameToUnMute = (String) income.get("usernameToUnMute");
        Profile ToUnMute = Server.users.get(usernameToUnMute);
        Server.users.get(username).getMute().remove(ToUnMute);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.UnMute);
        ans.put("answer", Boolean.TRUE);
        System.out.println(username + " muted ");
        System.out.println("message : " + usernameToUnMute);
        System.out.println("time: " + Time.getTime());
        return ans;
    }

    /**
     * when you search some one and go to see it's profile this method loads and find all the info
     * that user has and gives and shows it to you but if the user doesn't exist this give you a null and
     * by that you understand that it has deleted account
     * @param income your username and the who you want to see the personal info
     * @return the target user profile
     */

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

    /**
     * update profile gets all the field that can be changes and if there was any change it appears in you profile
     * you can change your profile,name,lastname,phone number,email,gender and your location
     * but u should know that you can not ever change your username
     * @param income all the field that can be changed
     * @return the new profile to set
     */

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
            List<Post> posts = new ArrayList<>();
            for (Profile profile : Server.users.values()) {
                posts.addAll(profile.getPosts());
            }
            for (Post post : posts) {
                if (post.getWriter().equals(username))
                    post.setProfilePhoto(profilePhoto);
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

    /**
     * when you go to some ones profile and want to see her/his posts this method helps you
     * @param income the username who you want to see posts
     * @return all the post this user has
     */

    public static Map<String, Object> LoadingPersonalInfo(Map<String, Object> income) {
        String username = (String) income.get("username");
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LoadPersonalTimeLine);
        ArrayList<Post> returnValue = new ArrayList<>(Server.users.get(username).getPosts());
        ans.put("answer", returnValue);
        return ans;
    }

    /**
     * when you forget your password you write the second password and send it to server
     * so it searches and gives you the result that if you were correct you can get your password and login again
     * but if not there is no use to get back your account
     * @param income the username and the second password
     * @return the real password
     */

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

    /**
     * this is the method to save the second password which is used
     * when you forget your password
     * @param income the username and the second password using in emergencies
     * @return
     */

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

    /**
     * by the name it is obvious that repost method reposts the post :/i did not get what was written in the project
     * so my repost is kinda different i mean it has the same writer,desc,title but likes and comments are individual
     * @param income the post and also the user who wants to repost
     * @return
     */

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

    /**
     * this method really took time it's kinda messy but at least it works when you open your
     * direct page you can see how many unread messages you have from different people
     * and also the last message is shown in the view and of course it is sorted by the time of last message
     * between each user
     * @param income the user that we are gonna open the chat page for
     * @return array list of people who were communicating with u
     */
    public static Map<String, Object> LoadingDirectInfo(Map<String, Object> income) {
        String username = (String) income.get("username");
        for (Profile profile : Server.users.values()) {
            int size = 0;
            if (profile.getMessages().containsKey(username)) {
                for (Message message : profile.getMessages().get(username)) {
                    if (!message.isWasSeen()) {
                        size++;
                    }
                }
                profile.getNotSeen().put(username, size);
            } else {
                profile.getNotSeen().put(username, 0);
            }
        }
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LoadUserDirect);
        ArrayList<Profile> returnValue = new ArrayList<>();
        for (String user : Server.users.get(username).getMessages().keySet()) {
            returnValue.add(Server.users.get(user));
        }
        for (Profile profile : Server.users.values()) {
            if (profile.getMessages().containsKey(username) && (!Server.users.get(username).getMessages().containsKey(profile.getUsername()))) {
                returnValue.add(profile);
            }
        }
        for (Profile profile : Server.users.values()) {
            List<Message> messages = new ArrayList<>();
            if (Server.users.get(username).getMessages() != null) {
                if (Server.users.get(username).getMessages().containsKey(profile.getUsername())) {
                    messages.addAll(Server.users.get(username).getMessages().get(profile.getUsername()));
                }
            }
            if (Server.users.get(profile.getUsername()).getMessages() != null) {
                if (Server.users.get(profile.getUsername()).getMessages().containsKey(username)) {
                    messages.addAll(Server.users.get(profile.getUsername()).getMessages().get(username));
                }
            }
            messages.sort(timeCompare);
            if (messages .size()>=1) {
                Server.users.get(profile.getUsername()).getLastMessage().put(username, messages.get(0));
                Server.users.get(username).getLastMessage().put(profile.getUsername(), messages.get(0));
            }
            DataManager.getInstance().updateDataBase();
        }
        if(returnValue.size()>=2) {
            for (int i = 0; i < returnValue.size()-1; i++) {
                if (returnValue.get(i).getLastMessage().get(username).getTimeMilli() <returnValue.get(i + 1).getLastMessage().get(username).getTimeMilli()) {
                    Profile temp = returnValue.get(i);
                    returnValue.set(i, returnValue.get(i + 1));
                    returnValue.set(i + 1, temp);
                }
            }
        }
        ans.put("answer", returnValue);
        return ans;
    }

    /**
     *by each profile there is a map each contains of usernames you have chatted with and the messages
     * so when you send a message this message should be put in this map and this method does that
     * @param income just the message
     * @return that the command was accepted
     */

    public static Map<String, Object> SendMessage(Map<String, Object> income) {
        Message message = (Message) income.get("message");
        String sender = message.getSender();
        String Receiver = message.getReceiver();
        ArrayList<Message> messages = new ArrayList<>();
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

    /**
     * this method shows if the message was seen by the receiver or not and if it was
     * so the bool changes to true
     * @param income the chats between 2 people
     */

    public static void UnseenDms(Map<String, Object> income) {
        String username = (String) income.get("username");
        String chatWith = (String) income.get("chatWith");
        if (Server.users.get(chatWith).getMessages() != null) {
            if ((Server.users.get(chatWith).getMessages().containsKey(username))) {
                for (Message message : Server.users.get(chatWith).getMessages().get(username)) {
                    message.setWasSeen(true);
                }
            }
        }
        DataManager.getInstance().updateDataBase();
    }

    /**
     * when you go to some one's chat page you hope to see all the last messages between you
     * so this method helps you take a look
     * @param income both usernames that chatted with each other
     * @return all the messages between these people
     */

    public static Map<String, Object> LoadChatPage(Map<String, Object> income) {
        String username = (String) income.get("username");
        String chatWith = (String) income.get("chatWith");
        List<Message> returnValue = new ArrayList<>();
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
        returnValue.sort(timeCompare);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.LoadChatPage);
        ans.put("answer", returnValue);
        return ans;
    }

    /**
     *this method is used in chat and just for photos and texts(did not have time to use for voices)
     * @param income contains the message you are gonna delete for both sides
     * @return that the command was accepted
     */


    public static Map<String, Object> TrashText(Map<String, Object> income) {
        Message message = (Message) income.get("message");
        String sender = message.getSender();
        String Receiver = message.getReceiver();
        Server.users.get(sender).getMessages().get(Receiver).remove(message);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        System.out.println("message deleted");
        ans.put("command", Command.TrashText);
        ans.put("answer", Boolean.TRUE);
        return ans;
    }

    /**
     * by it's name well its obvious that this method change your current password if
     * you select the new one and remember the last
     * @param income contains the old and new password also the username
     * @return if the command were accepted or not
     */

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

    /**
     * this method gives back the user info we want
     * @param income just have the username to find the profile
     * @return the profile which has changed and we need to continue the program
     */
    public static Map<String, Object> GetProfile(Map<String, Object> income) {
        String username = (String) income.get("username");
        Profile prof = Server.users.get(username);
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.GetProfile);
        ans.put("answer", prof);
        return ans;
    }

    /**
     * this method is useful when you want to  find some one to start your chat
     * so you just give the username you want and wait to go to her/his chat page
     * @param income
     * @return if the command were accepted or this user doesn't exist
     */

    public static Map<String, Object> StartChat(Map<String, Object> income) {
        String username = (String) income.get("username");
        Boolean isNullProfile = (Server.users.get(username) == null);
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.StartChat);
        ans.put("answer", Boolean.TRUE);
        if (isNullProfile) {
            ans.put("answer", null);
        }
        return ans;
    }

    /**
     * this method is not useful and it just sout that some one logged out
     * @param income the user who want to logout
     */

    public static void LogOut(Map<String, Object> income) {
        String username = (String) income.get("username");
        System.out.println(username + " LogOut");
        System.out.println("time: " + Time.getTime());
    }

    /**
     * this method edit the text message to anything we want but it just works for text messages and that's it
     * @param income contains the message we want to edit
     */

    public static void EditTextMessage(Map<String, Object> income) {
        Message message = (TextMessage) income.get("message");
        String text = (String) income.get("text");
        String Sender = message.getSender();
        String receiver = message.getReceiver();
        int indexOfMessage = Server.users.get(Sender).getMessages().get(receiver).indexOf(message);
        Message newMessage = new TextMessage(text, message.getTimeMilli(), message.getTime());
        newMessage.setSender(Sender);
        newMessage.setReceiver(receiver);
        Server.users.get(Sender).getMessages().get(receiver).set(indexOfMessage, newMessage);
        DataManager.getInstance().updateDataBase();
    }

    /**
     * this method deletes account of a user so it will delete all the posts or like and comments
     * and dleete this user from the whole app and also data manager
     * @param income contains the username of the person who wants to delete account
     * @return return that the command were accepted
     */

    public static Map<String, Object> DeleteAccount(Map<String, Object> income) {
        String username = (String) income.get("username");
        Profile profile = Server.users.get(username);
        for (Profile a : Server.users.values()) {
            if (a.getFollowings().contains(profile)) {
                a.getFollowings().remove(profile);
                profile.getFollowers().remove(a);
            }
        }
        for (Profile a : Server.users.values()) {
            if (a.getFollowers().contains(profile)) {
                a.getFollowers().remove(profile);
                profile.getFollowings().remove(a);
            }
        }

        for (Profile prof : Server.users.values()) {
            prof.getPosts().removeIf(post -> post.getWriter().equals(username));
        }
        for (Profile prof : Server.users.values()) {
            for (Post post : prof.getPosts()) {
                post.getLikes().remove(profile);
                post.getComments().remove(profile);
            }
        }
        for (Profile prof : Server.users.values()) {
            for (Post post : prof.getPosts()) {
                post.getRepost().remove(profile);
            }
        }
        for (Profile prof : Server.users.values()) {
            profile.getMessages().remove(prof);
            prof.getMessages().remove(username);
        }
        Server.users.remove(username);
        DataManager.getInstance().updateDataBase();
        Map<String, Object> ans = new HashMap<>();
        ans.put("command", Command.DeleteAccount);
        ans.put("answer", Boolean.TRUE);
        return ans;
    }
}
