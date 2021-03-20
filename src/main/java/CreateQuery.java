import java.util.List;

public class CreateQuery {
    public String createQuery(List<Object> criterias){
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
//               if (value[i].equalsIgnoreCase("lastName")) {
//                query.append("SELECT b.last_name, b.first_name FROM  buyer as b ");
//                    query.append("where b.last_name = ").append("'").append(value[i + 1]).append("'");
//                    DBJsonConverter dbJsonConverter = new DBJsonConverter();
//                    dbJsonConverter.writeJson(argument.getOutFile(), String.valueOf(query));
//                    query.setLength(0);
//              }
//                if (value[i].replaceAll(" ","").equalsIgnoreCase("productName")){
//                    query.append("SELECT b.last_name, b.first_name FROM  buyer as b ");
//                    query.append("INNER JOIN purchase as pu ON pu.buyer_id = b.id ");
//                    query.append("INNER JOIN product as p ON pu.product_id = p.id AND p.name = ").append("'").append(value[i + 1]).append("'");
//                    query.append(" GROUP BY b.last_name, b.first_name HAVING count(b.id) >= ").append("'").append(Math.round(Float.parseFloat(value[i + 3]))).append("'");
//                }
                if (value[i].replaceAll(" ","").equalsIgnoreCase("minExpenses")){
                    query.append("SELECT b.last_name, b.first_name FROM  buyer as b ");
                    query.append("INNER JOIN purchase as pu ON pu.buyer_id = b.id ");
                    query.append("INNER JOIN product as p ON pu.product_id = p.id AND p.price < ").append("'").append(Math.round(Float.parseFloat(value[i + 1]))).append("'");
                }
            }
        }
        return String.valueOf(query);
    }
}
