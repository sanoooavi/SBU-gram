package Server;

import Client.Profile;
import Model.Post;

import java.io.*;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class DataManager {
    private static DataManager ourInstance = new DataManager();
    public static final String FILE_PREFIX = "C:/Users/Asus/Desktop/";
    public static final String PROFILES_FILE = FILE_PREFIX + "Profiles.txt";
    public static final String Post_File = FILE_PREFIX + "Posts.txt";

    /**
     * only way to se use this class is using this method and get inly instance of this class
     **/
    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {/* do nothing! */ }

    public synchronized void initializeServer() {
        try {
            FileInputStream fin = new FileInputStream(PROFILES_FILE);
            ObjectInputStream inFromFile = new ObjectInputStream(fin);
            Server.users = new ConcurrentHashMap<>((ConcurrentHashMap<String, Profile>) inFromFile.readObject());
            inFromFile.close();
            fin.close();
        } catch (EOFException | StreamCorruptedException e) {
            Server.users = new ConcurrentHashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * it is synchronized to prevent multithread problems
     * it save latests change of profiles and mails to file
     **/
    public synchronized void updateDataBase() {
        try {
            FileOutputStream fout = new FileOutputStream(PROFILES_FILE);
            ObjectOutputStream objToFile = new ObjectOutputStream(fout);
            objToFile.writeObject(Server.users); //writing profiles
            objToFile.close();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}