import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.search.ElementResult;
import model.search.OutputObject;
import model.search.Search;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class JsonConverter {
    private static final String driver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/serviceDB";
    private static final String username = "postgres";
    private static final String password = "root";

    Argument argument = Argument.getArguments();

    public void  writeJson (File path, Map<Object,String> queryMap){

        try (Connection connection = DatabaseConnector.getDbConnection(driver, url, username, password);
             Statement statement = connection.createStatement();
             FileWriter writer = new FileWriter(path); ) {

            OutputObject outputObject = new OutputObject();
            if (argument.isSearch()){
                outputObject.setType("search");
            } else {
                outputObject.setType("stat");
            }

            for (Map.Entry<Object,String> map : queryMap.entrySet()){
                ResultSet resultSet = statement.executeQuery(map.getValue());
                ElementResult elementResult = new ElementResult();
                elementResult.setCriteria(map.getKey());

                while (resultSet.next()){
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    elementResult.addResult(new Search(firstName,lastName));
                }
                outputObject.addElement(elementResult);
                resultSet.close();
            }

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            writer.write(gson.toJson(outputObject));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
