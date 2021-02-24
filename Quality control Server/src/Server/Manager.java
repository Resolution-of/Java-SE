package Server;

import Connection.To_Client;
import Connection.To_Database;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Manager {
    ObjectInputStream sois;
    ObjectOutputStream soos;
    static int kol;
    public Manager(ObjectInputStream sois, ObjectOutputStream soos)
    {
        this.sois = sois;
        this.soos = soos;
        boolean Menu = true;
        while(Menu) {
            try {
                String selec1 = "Select COUNT(userID) as id from accounts where role = 'admin' GROUP BY role";
                Statement statement2 = To_Database.connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(selec1);

                while (resultSet2.next()) {
                    kol = resultSet2.getInt("id");
                    //System.out.print(kol);
                }
                resultSet2.close();
                soos.writeObject(String.valueOf(kol));
                String select = "Select * from accounts where role = 'admin'";
                Statement statement3 = To_Database.connection.createStatement();
                ResultSet resultSet3 = statement3.executeQuery(select);
                while(resultSet3.next())
                {
                    String account = resultSet3.getString("account");
                    String password = resultSet3.getString("password");
                    soos.writeObject(account);
                    soos.writeObject(password);
                }
                resultSet3.close();
                String num = (String) sois.readObject();
                if (num.equals("1"))
                {
                    add();
                }
                else if (num.equals("2"))
                {
                    delete();
                }
                else if(num.equals("back"))
                {
                    To_Client.Connect(sois, soos);
                    Menu = false;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void add()
    {
        try {
            String account = (String) sois.readObject();
            String password = (String) sois.readObject();
            String select = "Select MAX(userID) as id from accounts";
            Statement statement2 = To_Database.connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(select);

            while (resultSet2.next()) {
                kol = resultSet2.getInt("id");
                //System.out.print(kol);
            }
            resultSet2.close();

            String sel = "INSERT INTO accounts (userID, account, password, role) VALUES (?,?,?,?)";

            PreparedStatement pst = To_Database.connection.prepareStatement(sel);
            pst.setInt(1, ++kol);
            pst.setString(2, account);
            pst.setString(3, password);
            pst.setString(4, "admin");
            pst.execute();
            pst.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delete()
    {
        try {
            String account = (String) sois.readObject();

            String select = "Select userID from accounts where  account = '" + account + "'";
            Statement statement4 = To_Database.connection.createStatement();
            ResultSet resultSet4 = statement4.executeQuery(select);
            int num = 0;
            while(resultSet4.next())
            {
                num = resultSet4.getInt("userID");
            }
            resultSet4.close();

            String sel = "DELETE FROM accounts where account = '" + account + "'";
            PreparedStatement pst = To_Database.connection.prepareStatement(sel);
            pst.execute();
            pst.close();

            String select1 = "Select MAX(userID) as id from accounts";
            Statement statement2 = To_Database.connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(select1);

            while (resultSet2.next()) {
                kol = resultSet2.getInt("id");
                //System.out.print(kol);
            }
            resultSet2.close();

            for (int i = num; i < kol; i++)
            {
                String sel2 = "UPDATE `accounts` SET `userID` = '" + i + "' WHERE (`userID` = '" + (i + 1) + "')";
                PreparedStatement pst2 = To_Database.connection.prepareStatement(sel2);
                pst2.execute();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
