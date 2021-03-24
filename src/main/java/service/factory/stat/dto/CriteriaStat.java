package service.factory.stat.dto;

import com.google.gson.Gson;
import lombok.Getter;
import service.factory.Criteria;
import service.exception.ExceptionJson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс критериев для поиска стастики за период содежит:
 * 1. startDate - дата начала поиска
 * 2. endDate - дата окончания поиска
 */
public class CriteriaStat implements Criteria {
    @Getter
    private String startDate;
    @Getter
    private String endDate;

    @Override
    public Criteria parse(File path) {

        Gson gson = new Gson();
        CriteriaStat criteriaStat = null;
        try (FileReader reader = new FileReader(path);) {
            criteriaStat = gson.fromJson(reader, CriteriaStat.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionJson("error", String.format("Файл не найден (%s)", e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionJson("error", String.format("Ошибка чтения данных (%s)", e.getMessage()));
        }
        return criteriaStat;
    }

}
