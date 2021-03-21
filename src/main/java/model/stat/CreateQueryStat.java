package model.stat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CreateQueryStat {

    public Map<Object, String> createQuery(CriteriaStat criteriaStat) {
        Map<Object, String> queryMap = new HashMap<>();
        StringBuilder query = new StringBuilder();

        query.append("SELECT b.last_name, b.first_name, p.name, sum(p.price) FROM  buyer as b ")
                .append("INNER JOIN purchase as pu ON pu.buyer_id = b.id ")
                .append("INNER JOIN product as p ON pu.product_id = p.id ")
                .append("WHERE pu.date >= ")
                .append("'").append(criteriaStat.getStartDate()).append("'").append("::date ")
                .append(" AND pu.date < ").append("'").append(criteriaStat.getEndDate()).append("'").append("::date+1 ")
                .append("GROUP BY b.last_name, b.first_name , p.name ")
                .append("ORDER BY sum(p.price) DESC");
        queryMap.put(getWorkDays(criteriaStat), String.valueOf(query));
        return queryMap;
    }

    public long getWorkDays(CriteriaStat criteriaStat) {

        LocalDate startDate = LocalDate.parse(criteriaStat.getStartDate());
        LocalDate endDate = LocalDate.parse(criteriaStat.getEndDate());

        long count = 0;
        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        if (!weekend.contains(endDate.getDayOfWeek())) {
            count++;
        }
        LocalDate day = startDate;
        while (day.isBefore(endDate)) {
            if (!weekend.contains(day.getDayOfWeek())) {
                count++;
            }
            day = day.plusDays(1);
        }
        return count;
    }

}
