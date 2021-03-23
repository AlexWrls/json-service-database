package service.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import service.Argument;
import service.DatabaseConnector;
import service.exception.ExceptionJson;
import service.search.dto.ElementSearch;
import service.search.dto.OutputSearch;
import service.search.dto.Search;
import service.stat.dto.ElementStat;
import service.stat.dto.OutputStat;
import service.stat.dto.Stat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Конвертирование в json и запись в выходящий файл
 */

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
                Gson gson = builder.setPrettyPrinting().create();
                writer.write(gson.toJson(outputSearch));
            }else {
                OutputStat outputStat = outputStat(queryMap,statement);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.setPrettyPrinting().create();
                writer.write(gson.toJson(outputStat));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ExceptionJson("error",String.format("Ошибка запроса к БД (%s)",throwables.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionJson("error",String.format("Ошибка записи данных (%s)",e.getMessage()));
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
            outputStat.setTotalDays((long)map.getKey());

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
        long sum  = outputStat.getCustomers().stream().mapToLong(ElementStat::getTotalExpenses).sum();
        double avg = (double) sum/ outputStat.getCustomers().size();

        outputStat.setTotalExpenses(sum);
        outputStat.setAvgExpenses(new BigDecimal(avg).setScale(2, RoundingMode.UP).doubleValue());
        outputStat.getCustomers().sort(new SumComparator());

        return outputStat;
    }
}
/**
 * Сортировка стасистики по сумме покупок товаров
 */

class SumComparator implements Comparator<ElementStat>{
    @Override
    public int compare(ElementStat o1, ElementStat o2) {
        if (o1.getTotalExpenses() < o2.getTotalExpenses()){
            return 1;
        }else {
            return -1;
        }
    }
}
