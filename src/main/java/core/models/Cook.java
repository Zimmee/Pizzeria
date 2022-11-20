package core.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cook {
    private int Id;
    private CookType type;
    private CookState state;

    private Pizza workingOn;

    public Cook(int id, CookType type, CookState state) {
        Id = id;
        this.type = type;
        this.state = state;
    }

    public void cook(Pizza pizza) throws InterruptedException {
        if(state!=CookState.free){
            return;
        }
        System.out.println(this.state);
        synchronized (this){
            this.state = CookState.busy;
            this.workingOn = pizza;
       synchronized (pizza){
           var cookTime = pizza.getPreparationTime()/3*1000;
//               System.out.println(pizza.getState()+" "+cookTime);

           if(pizza.getState() ==PizzaState.created && type==CookType.makingDough){
               pizza.setState(PizzaState.dough);
               wait(cookTime);
               pizza.setState(PizzaState.filling);
           } else if (pizza.getState() ==PizzaState.filling && type==CookType.filling) {
               wait(cookTime);
               pizza.setState(PizzaState.baking);
           }else if (pizza.getState() ==PizzaState.baking && type==CookType.backing) {
               wait(cookTime);
               pizza.setState(PizzaState.ready);
           }else if(type==CookType.multi){
               pizza.setState(PizzaState.dough);
               System.out.println("dough");
               wait(cookTime);
               pizza.setState(PizzaState.filling);
               System.out.println("filling");
               wait(cookTime);
               pizza.setState(PizzaState.baking);
//               System.out.println("baking");
               wait(cookTime);
//               System.out.println("ready");
               pizza.setState(PizzaState.ready);
           }
           workingOn = null;
           if(this.state != CookState.stopped){
               this.state = CookState.free;
           }
           notifyAll();

       }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cook cook = (Cook) o;

        if (Id != cook.Id) return false;
        if (type != cook.type) return false;
        return state == cook.state;
    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
