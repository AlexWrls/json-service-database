package service.factory;

import service.converter.JsonConverterImpl;
import service.factory.stat.dto.CriteriaStat;
import service.factory.stat.repository.CreateQueryStat;

import java.io.File;

public class ConverterFactoryStat implements ConverterFactory {


    @Override
    public Criteria getCriteria(File path) {
        return new CriteriaStat().parse(path);
    }

    @Override
    public CreateQuery getCreateQuery(Criteria criteria) {
        return new CreateQueryStat((CriteriaStat) criteria);
    }

    @Override
    public JsonConverter getJsonConverter() {
        return new JsonConverterImpl();
    }
}
