package service;

import lombok.Getter;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class Argument {
    private static final String PATH = System.getProperty("user.dir")+"/src/main/resources/";
    private final Logger log = Logger.getGlobal();

    @Getter
    private boolean isSearch = false;
    @Getter                                  // false - Статистика за период  | true - Поиск покупателей по критериям
    private File outFile;                    // имя выходного файла
    @Getter
    private File inputFile;                  // имя входных файлов

    private Argument(){};
    private static Argument arguments;
    public static Argument getArguments(String... args){
        if (arguments == null){
            arguments = new Argument();
            arguments.parse(args);
        }
        return arguments;
    }

    private static void showParamOptions(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar program.jar ","Параметры программы задаются при запуске через аргументы командной строки, по порядку:",options ,
                "input.json • имя входного файла, обязательное;\noutput.json • имя выходного файла;");

    }
    private static void printOption(Options options, int status){
        showParamOptions(options);
        System.exit(status);
    }

    private void parse(String[] args) {
        Options options = new Options();
        options.addOption("search", false, "• поиск покупателей по критериям (search);");
        options.addOption("stat", false, "• статистика за период (stat);");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (UnrecognizedOptionException e) {
            log.warning(String.format("Неизвестный параметр: %s",e.getOption()));
            printOption(options, 1);
        } catch (ParseException e) {
            log.warning(String.format("Сбой разбора параметра аргументов: %s", e.getMessage()));
            printOption(options, 2);
        }

        if (cmd == null) {
            log.warning("Ошибка вполнения программы");
            printOption(options, 3);
        } else {
            if (cmd.hasOption("search") && cmd.hasOption("stat")) {
                log.warning("Может быть только один параметр  (пример: -search или -stat)");
                printOption(options, 4);
            }
            if (!(cmd.hasOption("search") || cmd.hasOption("stat"))) {
                log.warning("Отсутствует обязательная опция тип опереации (пример: -search или -stat)");
                printOption(options, 5);
            }

            List<String> files = cmd.getArgList();

            if (files.size() < 1) {
                log.warning("Отсутствуют остальные параметры: имя входного файла; (пример: input.json)");
                printOption(options, 7);
            }
            if (files.size() < 2) {
                log.warning("Отсутствуют остальные параметры: имя выходного файла; (пример: output.json)");
                printOption(options, 8);
            }

            isSearch = cmd.hasOption("search");

            inputFile = new File(PATH + "source/" + files.get(0));
            outFile = new File(PATH + "result/" + files.get(1));

            log.info("Входной файл:\n"+ inputFile);
            log.info("Выходной файл:\n"+ outFile);
            log.info(isSearch?"Задан поиск покупателей по критериям":"Задан поиск статистики за период");

        }
    }
}
