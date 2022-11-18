package core.services;

import com.example.pizzeria2.Main;
import core.models.*;
import core.strategy.cooksStrategy.SingleTaskStrategy;
import core.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CooksService {
    public ObservableList<Cook> cooks = FXCollections.observableArrayList();

    public CooksService() {
        cooks.addAll(new SingleTaskStrategy().createCooks(Main.getInjector().getInstance(PizzeriaService.class).cooksNumber));
    }

    public void init(){
       
        var pizzas = Main.getInjector().getInstance(VisitorsService.class).pizzas;
          var activePizzas = pizzas.stream().filter(pizza -> pizza.getState()!= PizzaState.ready).collect(Collectors.toList());
          var freeCooks = cooks.stream().filter(c->c.getState()==CookState.free).collect(Collectors.toList());
//          System.out.println(activePizzas.size());
          if(activePizzas.size()>0 && freeCooks.size()>0){
              for (Pizza pizza:activePizzas) {
                 var validCooks = freeCooks.stream().filter(c->c.getState()==CookState.free && c.getType()==Utils.pizzaStateToCookType(pizza.getState())).collect(Collectors.toList());
                  System.out.println(pizza.getState()+" "+validCooks.size()+" "+Utils.pizzaStateToCookType(pizza.getState()));
                 if(validCooks.size()>0 || false/*replace with strategy check*/){
                     new Thread(() -> {
                         try {
                             validCooks.get(0).cook(pizza);
                         } catch (InterruptedException e) {
                             throw new RuntimeException(e);
                         }
                     }).start();
                 }
              }


          }

    }
}
