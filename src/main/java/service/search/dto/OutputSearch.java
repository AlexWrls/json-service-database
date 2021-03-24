package service.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Выходной класс для конвертации в json для писка покупателей по критериям содержит:
 * 1. type - тип результата
 * 2. result - список элементов поиска
 */

@AllArgsConstructor
@NoArgsConstructor
public class OutputSearch {
    @Getter
    @Setter
    private String type;
    @Getter
    private final List<ElementSearch> result = new ArrayList<>();

    public void addElement(ElementSearch elementSearch) {
        result.add(elementSearch);
    }
}
