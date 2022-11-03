package core.services;

import com.google.inject.Inject;
import core.models.Visitor;
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
    private final int visitorCreationDelay;
    private Future<?> scheduledTask;

    public ObservableList visitors = FXCollections.observableArrayList();

    private final Runnable visitorsManagerRunnable = new Runnable() {
        @Override
        public void run() {
            manageCreatingPassenger();
            scheduledTask = scheduledExecutorService.schedule(()->{Platform.runLater(visitorsManagerRunnable);},
                    (int)(Math.random() * visitorCreationDelay),
                    TimeUnit.MILLISECONDS);
            System.out.println("Created user");
        }
    };

    @Inject
    public VisitorsService()
    {
        this.visitorCreationDelay = 10000; //TODO: Add delay settings


        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledTask = scheduledExecutorService.schedule(()->{Platform.runLater(visitorsManagerRunnable);}, visitorCreationDelay, TimeUnit.MILLISECONDS);
        System.out.println("Created");
    }

    public void manageCreatingPassenger()
    {
        var visitor = createVisitor();
        visitors.add(visitor);
    }

    public Visitor createVisitor() {

        return new Visitor(new ArrayList<>(), 1);
    }

    @Override
    public void close() throws Exception {
        scheduledTask.cancel(true);
        scheduledExecutorService.shutdownNow();
    }
}
