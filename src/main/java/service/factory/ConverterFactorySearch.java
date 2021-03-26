package service.factory;

import service.converter.JsonConverterImpl;
import service.factory.search.dto.CriteriaSearch;
import service.factory.search.repository.CreateQuerySearch;

import java.io.File;

public class ConverterFactorySearch implements ConverterFactory {


    @Override
    public Criteria getCriteria(File path) {
        return new CriteriaSearch().parse(path);
    }

    @Override
    public CreateQuery getCreateQuery(Criteria criteria) {
        return new CreateQuerySearch((CriteriaSearch) criteria);
    }

    @Override
    public JsonConverter getJsonConverter() {
        return new JsonConverterImpl();
    }
}
