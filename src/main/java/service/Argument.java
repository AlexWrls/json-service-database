package service;

import lombok.Getter;
import service.exception.ExceptionJson;

import java.io.File;
import java.util.logging.Logger;

/**
 * Парсер аргументов командной строки
 */

public class Argument {
    private static final String PATH = System.getProperty("user.dir") + "/src/main/resources/";
    private final Logger log = Logger.getGlobal();

    @Getter
    private String criteriaType;              // тип операции
    @Getter
    private File outFile;                    // имя выходного файла
    @Getter
    private File inputFile;                  // имя входных файлов

    private Argument() {
    }

    private static Argument arguments;

    public static Argument getArguments(String... args) {
        if (arguments == null) {
            arguments = new Argument();
            arguments.parse(args);
        }
        return arguments;
    }

    private void parse(String[] args) {
        if (args.length < 3) {
            throw new ExceptionJson("error", "Не заплнены агргументы командной строки!");
        }
        if (args.length > 3) {
            throw new ExceptionJson("error", "В аргументах командной строки указывается тип операции, имя входного и имя выходного файла");
        }
        try {
            criteriaType = args[0];
            inputFile = new File(PATH + "source/" + args[1]);
            outFile = new File(PATH + "result/" + args[2]);
        } catch (Exception e) {
            throw new ExceptionJson("error", "Ошибка парсинга аргументов конадной строки");
        }

        if (!inputFile.isFile()) {
            throw new ExceptionJson("error", String.format("Входной файл не является файлом (%s)", inputFile.getAbsolutePath()));
        }
        if (!outFile.isFile()) {
            throw new ExceptionJson("error", String.format("Выходной файл не является файлом (%s)", outFile.getAbsolutePath()));
        }

        log.info("Тип операции: " + criteriaType);
        log.info("Входной файл: " + inputFile);
        log.info("Выходной файл: " + outFile);


    }

}
