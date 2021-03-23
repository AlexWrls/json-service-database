package service.options.search;

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


}
