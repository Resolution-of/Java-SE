package Server;
import Connection.To_Client;
import Connection.To_Database;
import Usages.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expert {
    ObjectInputStream sois;
    ObjectOutputStream soos;
    static int kol;
    public Expert(ObjectInputStream sois, ObjectOutputStream soos, String account)
    {
        this.sois = sois;
        this.soos = soos;
        boolean Menu = true;
        while(Menu) {
            try {
                String selec1 = "Select COUNT(idadmin) as id from admin where valid = '-'";
                Statement statement2 = To_Database.connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(selec1);

                while (resultSet2.next()) {
                    kol = resultSet2.getInt("id");
                    //System.out.print(kol);
                }
                resultSet2.close();
                soos.writeObject(String.valueOf(kol));
                Product[] ob = new Product[kol];
                String select = "Select * from admin where valid = '-'";
                Statement statement3 = To_Database.connection.createStatement();
                ResultSet resultSet3 = statement3.executeQuery(select);
                int q = 0;
                while (resultSet3.next()) {
                    String productName = resultSet3.getString("productName");
                    double price = resultSet3.getDouble("price");
                    double weight = resultSet3.getDouble("weight");
                    double protein = resultSet3.getDouble("protein");
                    double fats = resultSet3.getDouble("fats");
                    double carbohydrates = resultSet3.getDouble("carbohydrates");
                    double nutritionalValue = resultSet3.getDouble("nutritionalValue");
                    String composition = resultSet3.getString("composition");

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
                if (num.equals("expertize")) {
                    expertize();
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
    private void expertize()
    {
        try {
            String productName = (String) sois.readObject();
            String Protein = (String) sois.readObject();
            String Fats = (String) sois.readObject();
            String Carbohydrates = (String) sois.readObject();
            String NutritionalValue = (String) sois.readObject();

            if(result_of_expertise(productName,Double.parseDouble(Protein),Double.parseDouble(Fats),
                    Double.parseDouble(Carbohydrates),Double.parseDouble(NutritionalValue)))
            {
                String sel2 = "UPDATE `admin` SET `valid` = '+' WHERE (`productName` = '" + productName + "')";
                PreparedStatement pst2 = To_Database.connection.prepareStatement(sel2);
                pst2.execute();
                pst2.close();
                soos.writeObject("Соответствует требованиям");
                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss E yyyy.MM.dd");

                FileWriter writer = new FileWriter("Report.txt", true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write( productName + ": Соответствует требованиям. " + formatForDateNow.format(dateNow) + "\n");
                bufferWriter.close();
            }
            else {
                soos.writeObject("Не соответствует требованиям");
                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss E yyyy.MM.dd");
                FileWriter writer = new FileWriter("Report.txt", true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write( productName + ": Не соответствует требованиям" + formatForDateNow.format(dateNow) + "\n");
                bufferWriter.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean result_of_expertise(String productName, double Protein, double Fats, double Carbohydrates, double NutritionalValue)
    {
        try{
            String select = "Select protein, fats, carbohydrates, nutritionalValue from admin where productName = '" + productName + "'";
            Statement statement = To_Database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            double protein = 0, fats = 0, carbohydrates = 0, nutritionalValue = 0;
            while(resultSet.next())
            {
                protein = resultSet.getDouble("protein");
                fats = resultSet.getDouble("fats");
                carbohydrates = resultSet.getDouble("carbohydrates");
                nutritionalValue = resultSet.getDouble("nutritionalValue");
            }
            if(protein < (Protein - 3.0) || protein > (Protein + 10.0))
            {
                return false;
            }
            else if(fats < (Fats - 30.0) || fats > (Fats + 100.0))
            {
                return false;
            }
            else if(carbohydrates < (Carbohydrates - 5.0) || carbohydrates > (Carbohydrates + 10.0))
            {
                return false;
            }
            else if(nutritionalValue < (NutritionalValue - 200.0) || nutritionalValue > (NutritionalValue + 200.0))
            {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
