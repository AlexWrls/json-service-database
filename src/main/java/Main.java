import java.util.List;

public class Main {

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        DBJsonConverter dbJsonConverter = new DBJsonConverter();
        Arg arg = Arg.getArguments(args);

        // TODO Auto-generated method stub
        dbJsonConverter.driver = "org.postgresql.Driver";
        dbJsonConverter.url = "jdbc:postgresql://localhost:5432/serviceDB";
        dbJsonConverter.username = "postgres";
        dbJsonConverter.password = "root";
        dbJsonConverter.path = arg.getOutFile().getAbsolutePath();

        ParseCriterias parseCriterias = new ParseCriterias();
        List<Object> criterias = parseCriterias.parse().getCriterias();

        StringBuilder select = new StringBuilder().append("select ");
        StringBuilder from = new StringBuilder().append("from ");
        StringBuilder where = new StringBuilder().append("where");

        for (Object c:criterias){
            String[] value;
            String string = String.valueOf(c).substring(1,String.valueOf(c).length()-1);
            if (string.contains(",")){
                value = string.split("[,\\=]");
            } else {
                value = string.split("=");
            }
            for (int i = 0; i < value.length; i++) {
              if (value[i].equals("lastName")){
                  select.append("b.first_name, b.last_name ");
                  from.append("buyer as b ");
                  where.append(" b.last_name = ").append("'").append(value[i + 1]).append("'");
              }
            }
        }

        dbJsonConverter.query = String.valueOf(select.append(from).append(where));


        DatabaseConnector dc = new DatabaseConnector();
        dc.getDbConnection(dbJsonConverter.driver,dbJsonConverter.url,dbJsonConverter.username,dbJsonConverter.password);
        DBJsonConverter formatter = new DBJsonConverter();

        formatter.dataLoad(dbJsonConverter.path);

    }
}
