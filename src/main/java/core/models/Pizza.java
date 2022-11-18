package core.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pizza implements Cloneable{
    private String name;
    private int preparationTime;

    private PizzaState state;

    public Pizza(String name, int preparationTime) {
        this.name = name;
        this.preparationTime = preparationTime;
        this.state = PizzaState.created;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", preparationTime=" + preparationTime +
                ", state=" + state +
                '}';
    }

    @Override
    public Pizza clone() {
        return new Pizza(name,preparationTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pizza pizza = (Pizza) o;

        if (preparationTime != pizza.preparationTime) return false;
        if (!name.equals(pizza.name)) return false;
        return state == pizza.state;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + preparationTime;
        result = 31 * result + state.hashCode();
        return result;
    }
}
