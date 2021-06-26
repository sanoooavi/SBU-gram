package Client;

import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import javafx.application.Application;

import static javafx.application.Application.launch;

public class Network {
    public static String serverAddress="127.0.0.1";
    public static final int port = 2222;
    private static boolean isConnected = false;
    public static Socket socket;
    public static ObjectInputStream dis;
    public static ObjectOutputStream dos;
    public static boolean isConnected(){
        return isConnected;
    }
    public static Boolean connectToServer(String[]args){
        if(socket != null) return false;
        try{
            System.out.println("server ip : " + serverAddress);
            socket = new Socket( serverAddress, port);
            dos = new ObjectOutputStream( socket.getOutputStream() );
            dis = new ObjectInputStream( socket.getInputStream() );
            isConnected = true;
            return true;

     //   }catch (ConnectException e){
        }
        catch (IOException e) {
        }
        return false;
    }


    /**
     a method that disconnect from server and make socket null
     it also make is connected boolean as false
     **/
    public static Boolean disconnectFromServer(){
        try{
            dis.close();
            dos.close();
            socket.close();
            isConnected = false;
            socket = null;
            dis = null;
            dos = null;
            return true;
        }
        catch( Exception e){
            e.printStackTrace();
        }
        socket = null;
        dis = null;
        dos = null;
        return false;
    }
    /**
     main method of sending and receiving to server
     if named serve because it give command and send it to server and return the response
     as i told, it send a map<String,Object> and receive same map too
     it use dos to send and dis top receive
     it flush the dos to make sure that buffering don't make delay for us
     **/
    public static Map<String,Object> serve(Map<String,Object> toSend){
        Map<String,Object> recieved = null;
        try{
            dos.writeObject(toSend);
            dos.flush();
            dos.reset();
            recieved = (Map<String,Object>) dis.readObject();
            return recieved;
        } catch (ClassNotFoundException e){
        } catch( IOException e){
            e.printStackTrace();
        }
        return recieved;
    }
}
