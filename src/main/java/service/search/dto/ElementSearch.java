package service.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Элемент поска содежит:
 * 1. criteria - критерий для поиска
 * 2. result - список результатов по критерию
 */

@AllArgsConstructor
@NoArgsConstructor
public class ElementSearch {
    @Getter
    @Setter
    private Object criteria;

    @Getter
    private final List<Search> result = new ArrayList<>();

    public void addResult(Search search) {
        result.add(search);
    }
}
