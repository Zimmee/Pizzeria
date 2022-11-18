package core.services;

import com.google.inject.Inject;
import core.models.Pizza;
import core.models.Visitor;
import core.strategy.visitorsStrategy.AverageDemandStrategy;
import core.strategy.visitorsStrategy.Strategy;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VisitorsService implements AutoCloseable {
    private final ScheduledExecutorService scheduledExecutorService;
    private Future<?> scheduledTask;

    private Strategy selectedStrategy = new AverageDemandStrategy();

    public ObservableList<Visitor> visitors = FXCollections.observableArrayList();
    public ObservableList<Pizza> pizzas = FXCollections.observableArrayList();

    private final Runnable visitorsManagerRunnable = new Runnable() {
        @Override
        public void run() {
            manageCreatingVisitor();
            scheduledTask = scheduledExecutorService.schedule(()->{Platform.runLater(visitorsManagerRunnable);},
                    selectedStrategy.calculateDelay(),
                    TimeUnit.SECONDS);

        }
    };

    @Inject
    public VisitorsService()
    {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledTask = scheduledExecutorService.schedule(()->{Platform.runLater(visitorsManagerRunnable);}, selectedStrategy.calculateDelay(), TimeUnit.MILLISECONDS);
    }

    public void manageCreatingVisitor()
    {
//        if(visitors.size()>10){
//            return;
//        }
        var visitor = selectedStrategy.createVisitor();
        pizzas.addAll(visitor.getPizzas());
        visitors.add(visitor);
    }

    public Visitor createVisitor() {

        return new Visitor(new ArrayList<>(), 0);
    }

    @Override
    public void close() throws Exception {
        scheduledTask.cancel(true);
        scheduledExecutorService.shutdownNow();
    }

    public void setSelectedStrategy(Strategy selectedStrategy) {
        this.selectedStrategy = selectedStrategy;
    }
}
