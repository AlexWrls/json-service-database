package service.factory;


import java.io.File;

/**
 *  Фабрика конвертирования содержит:
 *  1. Criteria - критерий по которому дубет производиться формирование запросов к БД по входному файлу
 *  2. CreateQuery - запросы к БД для извлечения нужных данных
 *  3. JsonConverter - извлекает данные по запросу и записывает их в выходной файл в формате Json
 */
public interface ConverterFactory {
    Criteria getCriteria(File path);

    CreateQuery getCreateQuery(Criteria criteria);

    JsonConverter getJsonConverter();
}
