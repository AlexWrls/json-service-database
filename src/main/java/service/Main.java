package service;

import service.exception.ExceptionJson;
import service.factory.*;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Argument argument = Argument.getArguments(args);

        try {
            if (argument.isSearch()) {

                ConverterFactory converterFactory = new ConverterFactorySearch();
                Criteria criteria = converterFactory.getCriteria(argument.getInputFile());
                Map<Object, String> queryMap = converterFactory.getCreateQuery(criteria).createQueryMap();

                converterFactory.getJsonConverter().writeJson(argument.getOutFile(),queryMap);

            } else {
                ConverterFactory converterFactory = new ConverterFactoryStat();
                Criteria criteria = converterFactory.getCriteria(argument.getInputFile());
                Map<Object, String> queryMap = converterFactory.getCreateQuery(criteria).createQueryMap();

                converterFactory.getJsonConverter().writeJson(argument.getOutFile(),queryMap);
            }
        } catch (Exception e) {
            throw new ExceptionJson("error", String.format("Ошибка работы программы (%s)", e.getMessage()));
        }

    }
}

