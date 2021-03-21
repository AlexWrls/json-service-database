import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.search.ElementSearch;
import model.search.OutputSearch;
import model.search.Search;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class JsonSearchConverter {
    private static final String driver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/serviceDB";
    private static final String username = "postgres";
    private static final String password = "root";


    public void  writeJson(File path, Map<Object, String> queryMap){

        try (Connection connection = DatabaseConnector.getDbConnection(driver, url, username, password);
             Statement statement = connection.createStatement();
             FileWriter writer = new FileWriter(path); ) {

            OutputSearch outputSearch = new OutputSearch();
            outputSearch.setType("search");

            for (Map.Entry<Object,String> map : queryMap.entrySet()){
                ResultSet resultSet = statement.executeQuery(map.getValue());
                ElementSearch elementSearch = new ElementSearch();
                elementSearch.setCriteria(map.getKey());

                while (resultSet.next()){
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    elementSearch.addResult(new Search(firstName,lastName));
                }
                outputSearch.addElement(elementSearch);
                resultSet.close();
            }

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            writer.write(gson.toJson(outputSearch));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
