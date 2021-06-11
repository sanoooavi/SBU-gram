package Server;

import Client.Command;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket userSocket;
    private ObjectOutputStream dos;
    private ObjectInputStream dis;

    public ClientHandler(Socket socket) {
        try {
            userSocket = socket;
            this.dos = new ObjectOutputStream(userSocket.getOutputStream());
            this.dis = new ObjectInputStream(userSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            Map<String,Object> income = null;
            try{
                income = (Map<String,Object>) dis.readObject();
                Map<String,Object> answer = null;
                Command command = (Command) income.get("command");
                switch(command){
                    case Login:
                        answer = ServerManager.login(income);
                        break;
                    case SignUp:
                        answer = ServerManager.signUp(income);
                        break;
                    case Username_unique:
                        answer = ServerManager.UserNameExists(income);
                        break;
                    case Publish_Post:
                        answer = ServerManager.PublishPost(income);
                        break;
                    case LoadTimeLine:
                        answer = ServerManager.LoadTimeLine(income);
                        break;
                    case LoadSearchTable:
                        answer = ServerManager.LoadingTable(income);
                        break;
                    case LikePost:
                        answer = ServerManager.LikePost(income);
                        break;
                    case AddComment:
                        answer = ServerManager.AddComment(income);
                        break;
                    case Follow:
                        answer = ServerManager.Follow(income);
                        break;
                    case UnFollow:
                        answer = ServerManager.UnFollow(income);
                        break;
                    case GetInfo:
                        answer = ServerManager.GetInfo(income);
                        break;
                    case UpdateProfile:
                        answer = ServerManager.UpdateProfile(income);
                        break;
                    case ForgotPassword:
                        break;
                }
                dos.writeObject(answer);
                dos.flush();
            }
            catch(ClassCastException | ClassNotFoundException e){
            }
            catch(EOFException e){
                break;
            }
            catch(IOException e){
                break;
            }

        }
        // after loggin out!
        try{
            dis.close();
            dos.close();
            userSocket.close();
        }catch(IOException e){}
    }
    }
