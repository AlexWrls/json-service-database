package service.stat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/**
 * Выходной класс для конвертации в json для писка статистики за период содержит:
 * 1. type - тип результата
 * 2. totalDays - тип результата
 * 3. customers - список элементов статистики
 * 4. totalExpenses - сумма покупок всех покупателей за период
 * 5. avgExpenses - средние затраты всех покупателей за период
 */

@AllArgsConstructor
@NoArgsConstructor
public class OutputStat {
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private long totalDays;
    @Getter
    private final List<ElementStat> customers = new ArrayList<>();

    public void addElementStat(ElementStat elementStat) {
        customers.add(elementStat);
    }
    @Getter @Setter
    private long totalExpenses;
    @Getter @Setter
    private double avgExpenses;
}
