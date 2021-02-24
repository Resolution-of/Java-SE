package Server;

import Connection.To_Client;
import Connection.To_Database;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class Main {
    /*static public Socket clientAccepted = null;
    static public  ObjectInputStream sois = null;
    static public ObjectOutputStream soos = null;
    static public ServerSocket serverSocket;*/

    public static final int PORT = 2525;
    public static LinkedList<Server> serverList = new LinkedList<>();
    public static Socket socket;
    public static int numOfConnection = 1;
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                socket = server.accept();
                try {
                    System.out.print("Создание сокета на порту: " + PORT + " №" + numOfConnection + "\n");
                    serverList.add(new Server(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }

    /*public static void main(String[] args)
    {
        try {
            serverSocket = new ServerSocket(2525);
            new Thread(() -> {
                connect();
                To_Client.Connect(sois, soos);
            }).start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void connect()
    {
        try {
            To_Database.Database();
            System.out.println("server starting....");
            clientAccepted = serverSocket.accept();
            System.out.println("connection established....");
            sois = new ObjectInputStream(clientAccepted.getInputStream());
            soos = new ObjectOutputStream(clientAccepted.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
