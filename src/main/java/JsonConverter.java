import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {
    private static final String driver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/serviceDB";
    private static final String username = "postgres";
    private static final String password = "root";

    public void  writeJson (File path, String query){
        List<String> data = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getDbConnection(driver, url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
             FileWriter writer = new FileWriter(path); ) {

            while (resultSet.next()){

            }


            Gson gson = new Gson();
            gson.newJsonWriter(writer);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
