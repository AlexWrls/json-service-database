package service.stat.repository;

import service.exception.ExceptionJson;
import service.search.dto.CriteriaSearch;
import service.stat.dto.CriteriaStat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * Формирование запросов для поиска статистики по ктирериям
 */

public class CreateQueryStat {

    private final CriteriaStat criteriaStat;
    public CreateQueryStat(CriteriaStat criteriaStat) {
        this.criteriaStat = criteriaStat;
    }

    public Map<Object, String> createQuery() {
        Map<Object, String> queryMap = new HashMap<>();
        StringBuilder query = new StringBuilder();

        query.append("SELECT b.last_name, b.first_name, p.name, sum(p.price) FROM  buyer as b ")
                .append("INNER JOIN purchase as pu ON pu.buyer_id = b.id ")
                .append("INNER JOIN product as p ON pu.product_id = p.id ")
                .append("WHERE pu.date >= ")
                .append("'").append(criteriaStat.getStartDate()).append("'").append("::date ")
                .append("AND pu.date < ").append("'").append(criteriaStat.getEndDate()).append("'").append("::date+1 ");

        List<LocalDate> weekends = getWeekendDay(criteriaStat);
        for (LocalDate weekend : weekends) {
            query.append("AND pu.date != ").append("'").append(weekend).append("'").append("::date ");
        }

        query.append("GROUP BY b.last_name, b.first_name , p.name ")
                .append("ORDER BY b.last_name, b.first_name ,sum(p.price) DESC");
        queryMap.put(getWorkDay(weekends, criteriaStat), String.valueOf(query));
        return queryMap;
    }

    private List<LocalDate> getWeekendDay(CriteriaStat criteriaStat) {
        List<LocalDate> weekends = new ArrayList<>();
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            startDate = LocalDate.parse(criteriaStat.getStartDate());
            endDate = LocalDate.parse(criteriaStat.getEndDate());

        } catch (Exception e) {
            throw new ExceptionJson("error", "Неправильный формат даты");
        }
        if (startDate.isAfter(endDate)) {
            throw new ExceptionJson("error", "Дата окончания поиска должна идти после даты начала");
        }

        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        LocalDate day = startDate;
        while (day.isBefore(endDate)) {
            if (weekend.contains(day.getDayOfWeek())) {
                weekends.add(day);
            }
            day = day.plusDays(1);
        }
        if (weekend.contains(endDate.getDayOfWeek())) {
            weekends.add(endDate);
        }
        return weekends;
    }

    private long getWorkDay(List<LocalDate> weekend, CriteriaStat criteriaStat) {
        LocalDate startDate = LocalDate.parse(criteriaStat.getStartDate());
        LocalDate endDate = LocalDate.parse(criteriaStat.getEndDate());
        Period between = Period.between(startDate, endDate);
        return (long) ((between.getDays() + 1) - weekend.size());
    }
}
