package service.options.stat;

import com.google.gson.Gson;
import service.exception.ExceptionJson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ParseCriteriaStat  {

    public CriteriaStat parse(File path) {

        Gson gson = new Gson();
        CriteriaStat criteriaStat = null;
        try (
            FileReader reader = new FileReader(path);) {
            criteriaStat = gson.fromJson(reader, CriteriaStat.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionJson("error",String.format("Файл не найден (%s)",e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionJson("error",String.format("Ошибка чтения данных (%s)",e.getMessage()));
        }
        return criteriaStat;
    }

}
