package core.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "name")
public class Pizza {
    private String name;
    private int preparationTime;

    public Pizza(String name, int preparationTime) {
        this.name = name;
        this.preparationTime = preparationTime;
    }
}
