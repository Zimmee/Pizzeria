package core.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "Id")
public class Visitor {
   private String Id;
   private ArrayList<Pizza> pizzas;
   private int selectedQueue;


    public Visitor( ArrayList<Pizza> pizzas, int selectedQueue) {
        Id = UUID.randomUUID().toString();
        this.pizzas = pizzas;
        this.selectedQueue = selectedQueue;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "Id='" + Id + '\'' +
                ", pizzas=" + pizzas +
                ", selectedQueue=" + selectedQueue +
                '}';
    }
}
