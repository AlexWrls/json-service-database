package search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OutputObject {
    @Getter @Setter
     private Object type;
    @Getter
    private List<ElementResult> result = new ArrayList<>();

    public void addElement(ElementResult elementResult){
        result.add(elementResult);
    }
}