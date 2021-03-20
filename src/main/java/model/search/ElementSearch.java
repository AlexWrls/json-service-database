package model.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class ElementSearch {
    @Getter @Setter
    private Object criteria;

    @Getter
   private List<Search> result = new ArrayList<>();

    public void addResult(Search search){
        result.add(search);
    }
}
