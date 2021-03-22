package service.options.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OutputStat {
    @Getter
    @Setter
    private Object type;
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
