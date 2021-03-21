package service.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import service.Argument;
import service.DatabaseConnector;
import service.exception.ExceptionJson;
import service.criteria.search.ElementSearch;
import service.criteria.search.OutputSearch;
import service.criteria.search.Search;
import service.criteria.stat.ElementStat;
import service.criteria.stat.OutputStat;
import service.criteria.stat.Stat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Stack;

public class JsonConverterImpl implements JsonConverter {

    private static final String driver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/serviceDB";
    private static final String username = "postgres";
    private static final String password = "root";
    Argument argument = Argument.getArguments();

    @Override
    public void  writeJson(File path, Map<Object, String> queryMap){

        try (Connection connection = DatabaseConnector.getDbConnection(driver, url, username, password);
             Statement statement = connection.createStatement();
             FileWriter writer = new FileWriter(path); ) {
            if (argument.isSearch()){
                OutputSearch outputSearch = outputSearch(queryMap,statement);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                writer.write(gson.toJson(outputSearch));
            }else {
                OutputStat outputStat = outputStat(queryMap,statement);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                writer.write(gson.toJson(outputStat));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ExceptionJson("error",throwables.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionJson("error", e.getMessage());
        }
    }

    private OutputSearch outputSearch(Map<Object, String> queryMap,Statement statement) throws SQLException {
        OutputSearch outputSearch = new OutputSearch();
        outputSearch.setType("search");

        for (Map.Entry<Object,String> map : queryMap.entrySet()){
            ResultSet resultSet = statement.executeQuery(map.getValue());
            ElementSearch elementSearch = new ElementSearch();
            elementSearch.setCriteria(map.getKey());

            while (true){
                assert resultSet != null;
                if (!resultSet.next()) break;
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                elementSearch.addResult(new Search(firstName,lastName));
            }
            outputSearch.addElement(elementSearch);
            resultSet.close();
        }
        return outputSearch;
    }

    private OutputStat outputStat(Map<Object, String> queryMap,Statement statement) throws SQLException {
        OutputStat outputStat = new OutputStat();
        outputStat.setType("stat");

        for (Map.Entry<Object, String> map : queryMap.entrySet()) {
            ResultSet resultSet = statement.executeQuery(map.getValue());
            outputStat.setTotalDays((Long) map.getKey());

            Stack<ElementStat> stackElement = new Stack<>();

            while (true) {
                assert resultSet != null;
                if (!resultSet.next()) break;
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
        return outputStat;
    }

}
