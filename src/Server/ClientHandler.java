package Server;

import Client.Command;

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
        while (true) {
            Map<String, Object> income = null;
            try {
                income = (Map<String, Object>) dis.readObject();
                Map<String, Object> answer = null;
                Command command = (Command) income.get("command");
                switch (command) {
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
                    case rePost:
                        answer = ServerManager.rePost(income);
                        break;
                    case Follow:
                        answer = ServerManager.Follow(income);
                        break;
                    case UnFollow:
                        answer = ServerManager.UnFollow(income);
                        break;
                    case Block:
                        answer = ServerManager.Block(income);
                        break;
                    case UnBlock:
                        answer = ServerManager.UnBlock(income);
                        break;
                    case Mute:
                        answer = ServerManager.Mute(income);
                        break;
                    case UnMute:
                        answer = ServerManager.UnMute(income);
                        break;
                    case UpdateProfile:
                        answer = ServerManager.UpdateProfile(income);
                        break;
                    case GetInfo:
                        answer = ServerManager.GetInfo(income);
                        break;
                    case LoadPersonalTimeLine:
                        answer = ServerManager.LoadingPersonalInfo(income);
                        break;
                    case ForgotPassword:
                        answer = ServerManager.GetPassword(income);
                        break;
                    case SaveSecondPassword:
                        answer = ServerManager.SaveThePassword(income);
                        break;
                    case ChangePassword:
                        answer = ServerManager.ChangePassword(income);
                        break;
                    case LoadUserDirect:
                        answer = ServerManager.LoadingDirectInfo(income);
                        break;
                    case LoadChatPage:
                        answer = ServerManager.LoadChatPage(income);
                        break;
                    case SendMessage:
                        answer = ServerManager.SendMessage(income);
                        break;
                    case TrashText:
                        answer = ServerManager.TrashText(income);
                        break;
                    case GetProfile:
                        answer = ServerManager.GetProfile(income);
                        break;
                    case StartChat:
                        answer = ServerManager.StartChat(income);
                        break;
                    case LogOut:
                        ServerManager.LogOut(income);
                        break;
                    case EditText:
                        ServerManager.EditTextMessage(income);
                        break;
                    case DeleteAccount:
                       answer= ServerManager.DeleteAccount(income);
                        break;
                }
                dos.reset();
                dos.writeObject(answer);
                dos.flush();
            } catch (ClassCastException | ClassNotFoundException e) {
            } catch (IOException e) {
                break;
            }

        }
        // after loggin out!
        try {
            dis.close();
            dos.close();
            userSocket.close();
        } catch (IOException e) {
        }
    }
}
