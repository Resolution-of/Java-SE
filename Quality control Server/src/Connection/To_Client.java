package Connection;

import Server.*;
import Usages.Account;
import Usages.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class To_Client {
    static private int kol;
    public static PreparedStatement pst = null;
    static ResultSet rs = null;
    public static int getKol() {
        return kol;
    }

    static public void Connect(ObjectInputStream sois, ObjectOutputStream soos) {
        //перенести все подключения в другую функцию
        try {
            Statement statement = To_Database.connection.createStatement();
            String selec = "Select MAX(userID) as id from accounts";
            ResultSet resultSet = statement.executeQuery(selec);

            while (resultSet.next()) {
                kol = resultSet.getInt("id");
            }
            resultSet.close();
            String choice = (String) sois.readObject();
            if(choice.equals("log"))
            {
                String UserAccount = (String) sois.readObject();
                String UserPassword = (String) sois.readObject();
                try {
                    String selec1 = "Select * from accounts";
                    Statement statement1 = To_Database.connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery(selec1);
                    int q = kol;
                    int prov = -1;
                    while (resultSet1.next()) {
                        int id = resultSet1.getInt("userID");
                        String account = resultSet1.getString("account");
                        String password = resultSet1.getString("password");
                        String role = resultSet1.getString("role");
                        if (UserAccount.equals(account) && UserPassword.equals(password)) {
                            prov = 1;
                            Account ob = new Account(id, account, password, role);
                            soos.writeObject(role);
                            switch (role)
                            {
                                case "admin" -> {
                                    Admin admin = new Admin(sois, soos, account);
                                    //дальше всё делать в админе и передать ему sois и soos
                                    break;
                                }
                                case "user" -> {
                                    User user = new User(sois, soos, account);
                                    break;
                                }
                                case "expert" -> {
                                    Expert expert = new Expert(sois, soos, account);
                                    break;
                                }
                                case "manager" -> {
                                    Manager manager = new Manager(sois, soos);
                                    break;
                                }
                            }
                        }
                    }
                    if (prov == -1) {
                        soos.writeObject("mistake");
                    }
                    //resultSet1.close();
                    Connect(sois, soos);

                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
            else if(choice.equals("reg"))
            {
                String UserAccount = (String) sois.readObject();
                if(UserAccount.equals("end"))
                {
                    /*Main.clientAccepted.close();
                    sois.close();
                    soos.close();
                    new Thread(() -> {
                        Main.connect();
                        Connect(sois, soos);
                    }).start();*/
                }
                else if(UserAccount.equals("back")) {
                    Connect(sois, soos);
                }
                else if(UserAccount.equals("exit"))
                {
                    Main.socket.close();
                    ServerSocket server = new ServerSocket(Main.PORT);
                    try {
                        while (true) {
                            Socket socket = server.accept();
                            try {
                                Main.serverList.add(new Server(socket));
                            } catch (IOException e) {
                                socket.close();
                            }
                        }
                    } finally {
                        server.close();
                    }
                }
                else {
                    String UserPassword = (String) sois.readObject();
                    String sel = "INSERT INTO accounts (userID, account, password, role) VALUES (?,?,?,?)";

                    pst = To_Database.connection.prepareStatement(sel);
                    pst.setInt(1, getKol() + 1);
                    pst.setString(2, UserAccount);
                    pst.setString(3, UserPassword);
                    pst.setString(4, "user");

                    pst.execute();
                    pst.close();
                    Connect(sois, soos);
                }
            }
            else if(choice.equals("exit"))
            {
                Main.numOfConnection++;
                Thread.currentThread().join();
            }
        }
            catch (Exception e) {
            try {
                Main.socket.close();
                Main.serverList.add(new Server(new ServerSocket(Main.PORT).accept()));
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
                e.printStackTrace();
        }
    }
}
