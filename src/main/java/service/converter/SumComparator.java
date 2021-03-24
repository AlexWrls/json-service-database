package service.converter;

import service.factory.stat.dto.ElementStat;

import java.util.Comparator;

/**
 * Сортировка стасистики по сумме покупок товаров
 */

class SumComparator implements Comparator<ElementStat> {
    @Override
    public int compare(ElementStat o1, ElementStat o2) {
        if (o1.getTotalExpenses() < o2.getTotalExpenses()) {
            return 1;
        } else {
            return -1;
        }
    }
}
