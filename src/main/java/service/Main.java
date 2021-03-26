package service;

import service.exception.ExceptionJson;
import service.factory.ObjectFactory;

public class Main {

    public static void main(String[] args) {
        try {
            Argument argument = Argument.getArguments(args);
            ObjectFactory objectFactory = new ObjectFactory();
            objectFactory.convertToJson(argument);
        } catch (Exception e) {
            throw new ExceptionJson("error",String.format("Ошибка работы программы (%s)",e.getMessage()));
        }

    }
}

