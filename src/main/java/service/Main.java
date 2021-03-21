package service;

import service.converter.JsonConverter;
import service.converter.JsonConverterImpl;
import service.criteria.search.CreateQuerySearch;
import service.criteria.search.ParseCriteriaSearch;
import service.criteria.stat.CreateQueryStat;
import service.criteria.stat.CriteriaStat;
import service.criteria.stat.ParseCriteriaStat;
import service.exception.ExceptionJson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // parse argument
        Argument argument = Argument.getArguments(args);

        Map<Object,String> queryMap = new HashMap<>();
//        try {
            if (argument.isSearch()){
                // get object criterias
                ParseCriteriaSearch parseCriteriaSearch = new ParseCriteriaSearch();
                List<Object> criteria = parseCriteriaSearch.parse(argument.getInputFile()).getCriteria();

                // create query for database
                CreateQuerySearch createQuerySearch = new CreateQuerySearch();
                queryMap = createQuerySearch.createQuery(criteria);

            } else {
                ParseCriteriaStat parseCriteriaStat = new ParseCriteriaStat();
                CriteriaStat criteriaStat = parseCriteriaStat.parse(argument.getInputFile());

                CreateQueryStat createQueryStat = new CreateQueryStat();
                queryMap = createQueryStat.createQuery(criteriaStat);
            }
            // converter to json
            JsonConverter jsonConverter = new JsonConverterImpl();
            jsonConverter.writeJson(argument.getOutFile(),queryMap);

//        }catch (Exception e){
//            throw new ExceptionJson("error",e.getMessage());
//        }

    }
}

