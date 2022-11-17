package core.strategy;

import com.example.pizzeria2.Main;
import core.common.CallbackAble;
import core.models.Pizza;
import core.models.Visitor;
import core.services.PizzeriaService;
import core.utils.Utils;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HighDemandStrategy extends Strategy {
    PizzeriaService pizzeriaService =   Main.getInjector().getInstance(PizzeriaService.class);

    @Override
    public Visitor createVisitor() {
        var pizzasAmount = Utils.getRandomNumberUsingNextInt(2,5);

        var visitorsPizzas = new ArrayList<Pizza>();

        for (int i = 0; i < pizzasAmount; i++) {
            var randomPizza = pizzeriaService.availablePizzas.get(Utils.getRandomNumberUsingNextInt(0, pizzeriaService.availablePizzas.size()));
            visitorsPizzas.add(randomPizza);
        }

        var visitorsQueue = Utils.getRandomNumberUsingNextInt(0, pizzeriaService.queueNumber);

        var newVisitor = new Visitor(visitorsPizzas, visitorsQueue);

        return  newVisitor;
    }

    @Override
    public int calculateDelay() {
        return 2/pizzeriaService.queueNumber;
    }
}
