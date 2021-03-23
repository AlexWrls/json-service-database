package service.stat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Элемент статистики содежит:
 * 1. name - фамилия и имя покупателя
 * 2. purchases - список статистик всех уникальных товаров, купленных покупателем за этот период, упорядоченных по суммарной стоимости по убыванию
 * 2. totalExpenses - общая стоимость покупок этого покупателя за период
 */
@AllArgsConstructor
@NoArgsConstructor
public class ElementStat {
    @Getter
    @Setter
    private String name;

    @Getter
    private final List<Stat> purchases = new ArrayList<>();

    @Getter
    @Setter
    private long totalExpenses;

    public void addSum(long sum){
        this.totalExpenses = totalExpenses+sum;
    }
    public void addStat(Stat stat) {
        purchases.add(stat);
    }

}
