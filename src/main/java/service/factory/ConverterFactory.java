package service.factory;


import java.io.File;

public interface ConverterFactory {
    Criteria getCriteria(File path);

    CreateQuery getCreateQuery(Criteria criteria);

    JsonConverter getJsonConverter();
}
