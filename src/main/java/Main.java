
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // parse argument
        Argument argument = Argument.getArguments(args);

        // get object criterias
        ParseCriterias parseCriterias = new ParseCriterias();
        List<Object> criterias = parseCriterias.parse(argument.getInputFile()).getCriterias();

        // create query for database
        CreateQuery createQuery = new CreateQuery();
        Map<Object,String> queryMap = createQuery.createQuery(criterias);

        // converter to json
        JsonConverter jsonConverter = new JsonConverter();
        jsonConverter.writeJson(argument.getOutFile(),queryMap);


    }
}

