package model.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ElementStat {
   @Getter @Setter
    private String name;
    @Getter
   private List<Stat> purchases = new ArrayList();
    public void addStat(Stat stat){
        purchases.add(stat);
    }
}
