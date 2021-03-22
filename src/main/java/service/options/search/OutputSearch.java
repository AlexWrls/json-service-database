package service.options.search;

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
    private final List<ElementSearch> result = new ArrayList<>();

    public void addElement(ElementSearch elementSearch){
        result.add(elementSearch);
    }
}
