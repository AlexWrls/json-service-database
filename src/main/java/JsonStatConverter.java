import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.stat.ElementStat;
import model.stat.OutputStat;
import model.stat.Stat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class JsonStatConverter {
    private static final String driver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/serviceDB";
    private static final String username = "postgres";
    private static final String password = "root";


    public void writeJson(File path, Map<Object, String> queryMap) {

        try (Connection connection = DatabaseConnector.getDbConnection(driver, url, username, password);
             Statement statement = connection.createStatement();
             FileWriter writer = new FileWriter(path);) {

            OutputStat outputStat = new OutputStat();
            outputStat.setType("stat");

            for (Map.Entry<Object, String> map : queryMap.entrySet()) {
                ResultSet resultSet = statement.executeQuery(map.getValue());
                outputStat.setTotalDays((Long) map.getKey());

                Stack<ElementStat> stackElement = new Stack<>();

                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String productName = resultSet.getString("name");
                    long sum = resultSet.getLong("sum");
                    String fullName = firstName + " " + lastName;

                    if (stackElement.isEmpty()) {
                        stackElement.push(new ElementStat());
                        ElementStat elementStat = stackElement.peek();
                        elementStat.setName(fullName);
                    }

                    if (!fullName.equals(stackElement.peek().getName())) {

                        outputStat.addElementStat(stackElement.pop());
                        stackElement.push(new ElementStat());
                        ElementStat elementStat = stackElement.peek();
                        elementStat.setName(fullName);
                    }
                    stackElement.peek().addStat(new Stat(productName, sum));
                    stackElement.peek().addSum(sum);
                }

                if (!stackElement.isEmpty()){
                    outputStat.addElementStat(stackElement.pop());
                }
                resultSet.close();
            }

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            writer.write(gson.toJson(outputStat));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
