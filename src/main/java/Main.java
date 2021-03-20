
import model.search.CreateQuerySearch;
import model.search.ParseCriteriaSearch;
import model.stat.CreateQueryStat;
import model.stat.CriteriaStat;
import model.stat.ParseCriteriaStat;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // parse argument
        Argument argument = Argument.getArguments(args);

        if (argument.isSearch()){
            // get object criterias
            ParseCriteriaSearch parseCriteriaSearch = new ParseCriteriaSearch();
            List<Object> criteria = parseCriteriaSearch.parse(argument.getInputFile()).getCriteria();

            // create query for database
            CreateQuerySearch createQuerySearch = new CreateQuerySearch();
            Map<Object,String> queryMap = createQuerySearch.createQuery(criteria);

            // converter to json
            JsonSearchConverter jsonSearchConverter = new JsonSearchConverter();
            jsonSearchConverter.writeJson(argument.getOutFile(),queryMap);

        } else {
            ParseCriteriaStat parseCriteriaStat = new ParseCriteriaStat();
            CriteriaStat criteriaStat = parseCriteriaStat.parse(argument.getInputFile());

            CreateQueryStat createQueryStat = new CreateQueryStat();
            Map<Object, String> queryMap = createQueryStat.createQuery(criteriaStat);

            JsonStatConverter jsonStatConverter = new JsonStatConverter();
            jsonStatConverter.writeJson(argument.getOutFile(),queryMap);

        }

    }
}

