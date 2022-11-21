package core.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class Pizza implements Cloneable{
    private UUID id;
    private String name;
    private int preparationTime;

    private PizzaState state;

    public Pizza(String name, int preparationTime) {
        this.id =UUID.randomUUID();
        this.name = name;
        this.preparationTime = preparationTime;
        this.state = PizzaState.created;
    }

    public Pizza(String name, int preparationTime, PizzaState state) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.preparationTime = preparationTime;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Pizza{id" +id.toString() +
                " name='" + name + '\'' +
                ", preparationTime=" + preparationTime +
                ", state=" + state +
                '}';
    }

    @Override
    public Pizza clone() {
        return new Pizza(name,preparationTime, state);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pizza pizza = (Pizza) o;

        if (preparationTime != pizza.preparationTime) return false;
        if (!Objects.equals(id, pizza.id)) return false;
        if (!Objects.equals(name, pizza.name)) return false;
        return state == pizza.state;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + preparationTime;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
