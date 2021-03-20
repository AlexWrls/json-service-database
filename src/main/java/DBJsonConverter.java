import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBJsonConverter {

    private static final String driver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/serviceDB";
    private static final String username = "postgres";
    private static final String password = "root";

    public void  writeJson (File path, String query){
        List<String> data = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getDbConnection(driver, url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query); ) {

            JSONObject obj1 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
           List<String> columnNames = new ArrayList<>();

            if (resultSet != null) {
                ResultSetMetaData columns = resultSet.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    columnNames.add(columns.getColumnName(i));
                }
                while (resultSet.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < columnNames.size(); i++) {
                        data.add(resultSet.getString(columnNames.get(i)));
                        {
                            for (int j = 0; j < data.size(); j++) {
                                if (data.get(j) != null) {
                                    obj.put(columnNames.get(i), data.get(j));
                                }else {
                                    obj.put(columnNames.get(i), "");
                                }
                            }
                        }
                    }

                    jsonArray.add(obj);
                    obj1.put("results", jsonArray);
                    FileWriter file = new FileWriter(path);
                    file.write(obj1.toString());
                    file.flush();
                    file.close();
                }

            } else {
                JSONObject obj2 = new JSONObject();
                obj2.put(null, (Collection<?>) null);
                jsonArray.add(obj2);
                obj1.put("results", jsonArray);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
