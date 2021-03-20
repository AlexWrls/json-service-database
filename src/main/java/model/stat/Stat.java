package model.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class Stat {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private long expenses;
}
