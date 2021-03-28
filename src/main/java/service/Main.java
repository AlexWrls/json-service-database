package service;

import service.exception.ExceptionJson;
import service.factory.ObjectFactory;

public class Main {

    static String[] searchTest = new String[]{"search", "inputSearch.json", "outputSearch.json"};
    static String[] statTest = new String[]{"stat", "inputStat.json", "outputStat.json"};

    public static void main(String[] args) {
        try {

            ObjectFactory objectFactory = new ObjectFactory();

            Argument argument = Argument.getArguments(searchTest);
            objectFactory.convertToJson(argument);

            argument.restArgument();

            argument = Argument.getArguments(statTest);
            objectFactory.convertToJson(argument);

        } catch (Exception e) {
            throw new ExceptionJson("error", String.format("Ошибка работы программы (%s)", e.getMessage()));
        }

    }
}

