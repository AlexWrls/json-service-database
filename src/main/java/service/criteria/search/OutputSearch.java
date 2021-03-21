package service.criteria.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OutputSearch {
    @Getter @Setter
     private Object type;
    @Getter
    private List<ElementSearch> result = new ArrayList<>();

    public void addElement(ElementSearch elementSearch){
        result.add(elementSearch);
    }
}
