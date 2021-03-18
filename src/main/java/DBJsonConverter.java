import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;



public class DBJsonConverter {

    static ArrayList<String> data = new ArrayList<String>();
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;
    static String path = "";
    static String driver="";
    static String url="";
    static String username="";
    static String password="";
    static String query="";

    @SuppressWarnings({ "unchecked" })
    public static void dataLoad(String path) {
        JSONObject obj1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        conn = DatabaseConnector.getDbConnection(driver, url, username, password);

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<String> columnNames = new ArrayList<String>();
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    columnNames.add(columns.getColumnName(i));
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < columnNames.size(); i++) {
                        data.add(rs.getString(columnNames.get(i)));
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
                ps.close();
            } else {
                JSONObject obj2 = new JSONObject();
                obj2.put(null, (Collection<?>) null);
                jsonArray.add(obj2);
                obj1.put("results", jsonArray);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}