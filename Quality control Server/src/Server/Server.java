package Server;

import Connection.To_Client;
import Connection.To_Database;

import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    private Socket clientAccepted;
    static public ObjectInputStream sois = null;
    static public ObjectOutputStream soos = null;

    public Server(Socket socket) throws IOException {
        this.clientAccepted = socket;

        sois = new ObjectInputStream(clientAccepted.getInputStream());
        soos = new ObjectOutputStream(clientAccepted.getOutputStream());
        start();
    }
    public void run() {
            while (true) {
                To_Database.Database();
                System.out.print("Соединение успешно установлено\n");
                To_Client.Connect(sois, soos);
            }
    }
}
