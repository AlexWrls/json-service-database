package service.factory;

import service.Argument;
import service.exception.ExceptionJson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectFactory {
    private Map<String, String> propertiesMap;

    public ObjectFactory() {
        String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
        try (Stream<String> lines = new BufferedReader(new FileReader(path)).lines();) {
            propertiesMap = lines.map(line -> line.split("=")).collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionJson("error",String.format("Ошибка чтения из application.properties (%s)",e.getMessage()));
        }

    }

    public void convertToJson(Argument argument) {
        ConverterFactory converterFactory;
        switch (argument.getCriteriaType()) {
            case "search": {
                converterFactory = new ConverterFactorySearch();
                Criteria criteriaSearch = converterFactory.getCriteria(argument.getInputFile());
                Map<Object, String> queryMap = converterFactory.getCreateQuery(criteriaSearch).createQueryMap();
                JsonConverter jsonConverter = converterFactory.getJsonConverter();
                configure(jsonConverter);
                jsonConverter.writeJson(argument.getOutFile(), queryMap);
                break;
            }
            case "stat": {
                converterFactory = new ConverterFactoryStat();
                Criteria criteriaStat = converterFactory.getCriteria(argument.getInputFile());
                Map<Object, String> queryMapStat = converterFactory.getCreateQuery(criteriaStat).createQueryMap();
                JsonConverter jsonConverterStat = converterFactory.getJsonConverter();
                configure(jsonConverterStat);
                jsonConverterStat.writeJson(argument.getOutFile(), queryMapStat);
                break;
            }
            default:
                throw new ExceptionJson("error", "Неизвестный тип операции(search или stat)");
        }

    }


    private void configure(Object t) {
        Class<?> aClass = t.getClass();
        for (Field field : aClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String value;
                if (annotation.value().isEmpty()) {
                    value = propertiesMap.get(field.getName());
                } else {
                    value = propertiesMap.get(annotation.value());
                }
                field.setAccessible(true);
                try {
                    field.set(t, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new ExceptionJson("error",String.format("Недопустимый аргумент, используемый для вызова метода (%s)",e.getMessage()));
                }
            }
        }

    }
}
