package service.search.dto;

import com.google.gson.Gson;
import service.exception.ExceptionJson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс конвертируемый из входного json файла для поиска покупателей по критерияи
 */

public class CriteriaSearch {

    private final List<Object> criterias = new ArrayList<>();

    public List<Object> getCriteria() {
        return criterias;
    }

    public CriteriaSearch parse(File path) {

        Gson gson = new Gson();
        CriteriaSearch criteriaSearch = null;
        try (FileReader reader = new FileReader(path)) {
            criteriaSearch = gson.fromJson(reader, CriteriaSearch.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionJson("error", String.format("Файл не найден (%s)", e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionJson("error", String.format("Ошибка чтения данных (%s)", e.getMessage()));
        }
        return criteriaSearch;
    }


}
