
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // parse argument
        Argument argument = Argument.getArguments(args);

        // get object criterias
        ParseCriterias parseCriterias = new ParseCriterias();
        List<Object> criterias = parseCriterias.parse(argument.getInputFile()).getCriterias();

        // create query for database
        CreateQuery createQuery = new CreateQuery();
        String query = createQuery.createQuery(criterias);

        // converter to json
        DBJsonConverter dbJsonConverter = new DBJsonConverter();
        dbJsonConverter.writeJson(argument.getOutFile(),query);

    }
}

