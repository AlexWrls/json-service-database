package service;

import service.converter.JsonConverter;
import service.converter.JsonConverterImpl;
import service.exception.ExceptionJson;
import service.search.dto.CriteriaSearch;
import service.search.repository.CreateQuerySearch;
import service.stat.dto.CriteriaStat;
import service.stat.repository.CreateQueryStat;

import java.util.Map;

public class Main {


    public static void main(String[] args) {
        // parse argument
        Argument argument = Argument.getArguments(args);

        Map<Object, String> queryMap;

        try {
            if (argument.isSearch()) {
                // get object criterias
                CriteriaSearch criteriaSearch = new CriteriaSearch().parse(argument.getInputFile());

                // create query for database
                CreateQuerySearch createQuerySearch = new CreateQuerySearch(criteriaSearch);
                queryMap = createQuerySearch.createQuery();

            } else {

                CriteriaStat criteriaStat = new CriteriaStat().parse(argument.getInputFile());

                CreateQueryStat createQueryStat = new CreateQueryStat(criteriaStat);
                queryMap = createQueryStat.createQuery();
            }
            // converter to json
            JsonConverter jsonConverter = new JsonConverterImpl();
            jsonConverter.writeJson(argument.getOutFile(), queryMap);

        } catch (Exception e) {
            throw new ExceptionJson("error", String.format("Ошибка работы программы (%s)", e.getMessage()));
        }

    }
}

