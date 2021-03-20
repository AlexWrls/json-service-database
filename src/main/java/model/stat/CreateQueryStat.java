package model.stat;

import model.search.Search;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CreateQueryStat {

    public Map<Object,String> createQuery(CriteriaStat criteriaStat){
        Map<Object,String> queryMap = new HashMap<>();
        StringBuilder query = new StringBuilder();

         query.append("SELECT b.last_name, b.first_name, p.name, sum(p.price) FROM  buyer as b ");
         query.append("INNER JOIN purchase as pu ON pu.buyer_id = b.id ");
         query.append("INNER JOIN product as p ON pu.product_id = p.id ");
         query.append("GROUP BY b.last_name, b.first_name , p.name ");
         query.append("ORDER BY sum(p.price) DESC");
         queryMap.put(getWorkDays(criteriaStat), String.valueOf(query));
        return queryMap;
    }



    public long getWorkDays(CriteriaStat criteriaStat) {

        LocalDate startDate = LocalDate.parse(criteriaStat.getStartDate());
        LocalDate endDate = LocalDate.parse(criteriaStat.getEndDate());

        long count = 0;
        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        if (!weekend.contains(endDate.getDayOfWeek())){
            count++;
        }
        LocalDate day = startDate;
        while (day.isBefore(endDate)) {
            if (!weekend.contains(day.getDayOfWeek())){
                count++;
            }
            day = day.plusDays(1);
        }
        return count;
    }

}
