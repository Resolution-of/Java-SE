package Server;
import  Connection.*;
import Usages.Product;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.*;
public class Admin {
    ObjectInputStream sois;
    ObjectOutputStream soos;
    static int kol;
    public Admin(ObjectInputStream sois, ObjectOutputStream soos, String account)
    {
        boolean Menu = true;
        this.sois = sois;
        this.soos = soos;
        while(Menu) {
            try {
                String selec1 = "Select COUNT(idadmin) as id from admin where account = '" + account + "'";
                Statement statement2 = To_Database.connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(selec1);

                while (resultSet2.next()) {
                    kol = resultSet2.getInt("id");
                }
                resultSet2.close();
                soos.writeObject(String.valueOf(kol));
                Product[] ob = new Product[kol];
                String select = "Select * from admin where account = '" + account + "'";
                Statement statement3 = To_Database.connection.createStatement();
                ResultSet resultSet3 = statement3.executeQuery(select);
                int q = 0;
                while (resultSet3.next()) {
                    int id = resultSet3.getInt("idadmin");
                    String productName = resultSet3.getString("productName");
                    double price = resultSet3.getDouble("price");
                    double weight = resultSet3.getDouble("weight");
                    double protein = resultSet3.getDouble("protein");
                    double fats = resultSet3.getDouble("fats");
                    double carbohydrates = resultSet3.getDouble("carbohydrates");
                    double nutritionalValue = resultSet3.getDouble("nutritionalValue");
                    String composition = resultSet3.getString("composition");
                    String acc = resultSet3.getString("account");
                    ob[q] = new Product(productName, price, weight, protein, fats, carbohydrates, nutritionalValue, composition);
                    soos.writeObject(productName);
                    soos.writeObject(String.valueOf(price));
                    soos.writeObject(String.valueOf(weight));
                    soos.writeObject(String.valueOf(protein));
                    soos.writeObject(String.valueOf(fats));
                    soos.writeObject(String.valueOf(carbohydrates));
                    soos.writeObject(String.valueOf(nutritionalValue));
                    soos.writeObject(composition);
                    q++;
                }
                resultSet3.close();
                String num = (String) sois.readObject();
                if (num.equals("1")) {
                    add(account);
                }
                else if (num.equals("2"))
                {
                    red(account);
                }
                else if(num.equals("3"))
                {
                    delete();
                }
                else if (num.equals("back")) {
                    To_Client.Connect(sois, soos);
                    Menu = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void add(String account)
    {
        try {
            String selec1 = "Select MAX(idadmin) as id from admin";
            Statement statement2 = To_Database.connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(selec1);

            while (resultSet2.next()) {
                kol = resultSet2.getInt("id");
                //System.out.print(kol);
            }
            resultSet2.close();


            String productName = (String) sois.readObject();
            String price = (String) sois.readObject();
            String weight = (String) sois.readObject();
            String protein = (String) sois.readObject();
            String fats = (String) sois.readObject();
            String carbohydrates = (String) sois.readObject();
            String nutritionalValue = (String) sois.readObject();
            String composition = (String) sois.readObject();

            String sel = "INSERT INTO admin (idadmin, productName, price, weight, protein, fats, carbohydrates, nutritionalValue, composition, valid, account) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = To_Database.connection.prepareStatement(sel);
            pst.setInt(1, ++kol);
            pst.setString(2, productName);
            pst.setString(3, price);
            pst.setString(4, weight);
            pst.setString(5, protein);
            pst.setString(6, fats);
            pst.setString(7, carbohydrates);
            pst.setString(8, nutritionalValue);
            pst.setString(9, composition);
            pst.setString(10, "-");
            pst.setString(11, account);

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
            String productName = (String) sois.readObject();

            String select = "Select idadmin from admin where productName = '" + productName + "'";
            Statement statement4 = To_Database.connection.createStatement();
            ResultSet resultSet4 = statement4.executeQuery(select);
            int num = 0;
            while(resultSet4.next())
            {
                num = resultSet4.getInt("idadmin");
            }
            resultSet4.close();

            String sel = "DELETE FROM admin where productName = '" + productName + "'";
            PreparedStatement pst = To_Database.connection.prepareStatement(sel);
            pst.execute();
            pst.close();
            for (int i = num; i < kol; i++)
            {
                String sel2 = "UPDATE `admin` SET `idadmin` = '" + i + "' WHERE (`idadmin` = '" + (i + 1) + "')";
                PreparedStatement pst2 = To_Database.connection.prepareStatement(sel2);
                pst2.execute();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void red(String account)
    {
        try {
            String productName = (String) sois.readObject();

            String productName1 = (String) sois.readObject();
            String price = (String) sois.readObject();
            String weight = (String) sois.readObject();
            String protein = (String) sois.readObject();
            String fats = (String) sois.readObject();
            String carbohydrates = (String) sois.readObject();
            String nutritionalValue = (String) sois.readObject();
            String composition = (String) sois.readObject();

            String update = "UPDATE admin SET productName = '" + productName1 + "', price = '" + price + "'," +
                    " weight = '" + weight + "', protein = '" + protein + "', fats = '" + fats + "'," +
                    " carbohydrates = '" + carbohydrates + "', nutritionalValue = '" + nutritionalValue +"'," +
                    " composition = '" + composition + "' where (`productName` = '" + productName + "')";
            PreparedStatement pst = To_Database.connection.prepareStatement(update);
            pst.execute();
            pst.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
