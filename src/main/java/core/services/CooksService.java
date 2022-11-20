package core.services;

import com.example.pizzeria2.Main;
import core.models.*;
import core.strategy.cooksStrategy.SingleTaskStrategy;
import core.strategy.cooksStrategy.Strategy;
import core.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class CooksService {
    public Strategy cooksStrategy;
    public ObservableList<Cook> cooks = FXCollections.observableArrayList();

    public void onTick(){
        var pizzas = Main.getInjector().getInstance(VisitorsService.class).pizzas;
          var activePizzas = pizzas.stream().filter(pizza -> cooksStrategy.getClass() == SingleTaskStrategy.class?(pizza.getState()!= PizzaState.ready && pizza.getState()!=PizzaState.dough):(pizza.getState()==PizzaState.created)).collect(Collectors.toList());
          var freeCooks = cooks.stream().filter(c->c.getState()==CookState.free).collect(Collectors.toList());
          if(activePizzas.size()>0 && freeCooks.size()>0){
              for (Pizza pizza:activePizzas) {
                 var validCooks = freeCooks.stream().filter(c->c.getState()==CookState.free && c.getType()==Utils.pizzaStateToCookType(pizza.getState())).collect(Collectors.toList());
//                  System.out.println(pizza.getState()+" "+validCooks.size()+" "+Utils.pizzaStateToCookType(pizza.getState()));
//                  System.out.println(cooksStrategy.getClass() == SingleTaskStrategy.class);
                 if(validCooks.size()>0 && cooksStrategy.getClass() == SingleTaskStrategy.class){
                     new Thread(() -> {
                         try {
                             if(validCooks.get(0).getState()==CookState.free){
                                 validCooks.get(0).cook(pizza);
                             }
                         } catch (InterruptedException e) {
                             throw new RuntimeException(e);
                         }
                     }).start();
                 }else{
                     new Thread(() -> {
                         try {
                             if(freeCooks.get(0).getState()==CookState.free){
                                 freeCooks.get(0).cook(pizza);
                             }
                         } catch (InterruptedException e) {
                             throw new RuntimeException(e);
                         }
                     }).start();
                 }
              }


          }

    }

    public void initCooks(){
        cooks.addAll(cooksStrategy.createCooks(Main.getInjector().getInstance(PizzeriaService.class).cooksNumber));
    }

    public Strategy getCooksStrategy() {
        return cooksStrategy;
    }

    public void setCooksStrategy(Strategy cooksStrategy) {
        this.cooksStrategy = cooksStrategy;
    }
}
