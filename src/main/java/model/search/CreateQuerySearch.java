package model.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateQuerySearch {

    public  Map<Object,String> createQuery(List<Object> criterias) {

        Map<Object,String> queryMap = new HashMap<>();

        StringBuilder query = new StringBuilder();
        for (Object c : criterias) {
            String[] value;
            String string = String.valueOf(c).substring(1, String.valueOf(c).length() - 1);
            if (string.contains(",") && string.contains("=")) {
                value = string.split("[,=]");
            } else {
                value = string.split("=");
            }
            for (int i = 0; i < value.length; i++) {
                if (value[i].equalsIgnoreCase("lastName")) {
                    query.append("SELECT b.last_name, b.first_name FROM  buyer as b ");
                    query.append("where b.last_name = ").append("'").append(value[i + 1]).append("'");
                    queryMap.put(c, String.valueOf(query));
                    query.setLength(0);

                }
                if (value[i].replaceAll(" ", "").equalsIgnoreCase("productName")) {
                    query.append("SELECT b.last_name, b.first_name FROM  buyer as b ");
                    query.append("INNER JOIN purchase as pu ON pu.buyer_id = b.id ");
                    query.append("INNER JOIN product as p ON pu.product_id = p.id AND p.name = ").append("'").append(value[i + 1]).append("'");
                    query.append(" GROUP BY b.last_name, b.first_name HAVING count(b.id) >= ").append(Math.round(Float.parseFloat(value[i + 3])));
                    queryMap.put(c, String.valueOf(query));
                    query.setLength(0);

                }
                if (value[i].replaceAll(" ", "").equalsIgnoreCase("minExpenses")) {
                    query.append("SELECT DISTINCT b.last_name, b.first_name FROM  buyer as b ");
                    query.append("INNER JOIN purchase as pu ON pu.buyer_id = b.id ");
                    query.append("INNER JOIN product as p ON pu.product_id = p.id AND p.price >= ").append(Math.round(Float.parseFloat(value[i + 1])));
                    query.append(" AND p.price <= ").append(Math.round(Float.parseFloat(value[i + 3])));
                    queryMap.put(c, String.valueOf(query));
                    query.setLength(0);
                }
                if (value[i].replaceAll(" ", "").equalsIgnoreCase("badCustomers")) {
                    query.append("SELECT b.last_name, b.first_name FROM  buyer as b ");
                    query.append("INNER JOIN purchase as pu ON pu.buyer_id = b.id ");
                    query.append("INNER JOIN product as p ON pu.product_id = p.id ");
                    query.append("GROUP BY b.last_name, b.first_name HAVING count(pu.buyer_id) >= 0");
                    query.append("ORDER BY count(pu.buyer_id) LIMIT ").append(Math.round(Float.parseFloat(value[i + 1])));
                    queryMap.put(c, String.valueOf(query));
                    query.setLength(0);
                }
            }

        }
        return queryMap;
    }
}

