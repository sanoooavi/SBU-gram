package Tests;

import Server.DataManager;

import java.io.File;
import java.io.IOException;
public class MakeFile {
    public static void main(String[] args) throws IOException {
        File file=new File(DataManager.PROFILES_FILE);
        if(!file.exists()){
            file.createNewFile();
        }
    }
}
