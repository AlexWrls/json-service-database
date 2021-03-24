package service.search.dto;

import lombok.AllArgsConstructor;

/**
 * Класс для формирования поиска
 * 1. firstName - имя покупателя
 * 2. lastName - фамилия покупателя
 */

@AllArgsConstructor
public class Search {
    private final String firstName;
    private final String lastName;
}
