package service.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import service.Argument;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Конвертирование в json и запись в выходящий файл ошибок
 */

public class ExceptionJson extends RuntimeException {

    private final Logger log = Logger.getGlobal();

    private void printError(String type, String message) {

        Argument argument = Argument.getArguments();
        try (FileWriter writer = new FileWriter(argument.getOutFile());) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", type);
            jsonObject.addProperty("message", message);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.setPrettyPrinting().create();
            writer.write(gson.toJson(jsonObject));
        } catch (IOException e) {
            log.warning(String.format("Ошибка %s записи в файл %s", e.getMessage(), argument.getOutFile()));
            e.printStackTrace();
        }
    }

    public ExceptionJson(String type, String message) {
        printError(type, message);
        System.exit(1);
    }

}
