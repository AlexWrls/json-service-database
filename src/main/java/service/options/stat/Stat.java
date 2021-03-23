package service.options.stat;

import lombok.AllArgsConstructor;

/**
 * Статистика содежит:
 * 1. name - название товара
 * 2. expenses - суммарная стоимость всех покупок этого товара за период
 */
@AllArgsConstructor
public class Stat {

    private final String name;
    private final long expenses;
}
