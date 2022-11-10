package core.services;

import core.models.Pizza;

import java.util.ArrayList;

public class PizzeriaService {
    public ArrayList<Pizza> availablePizzas;
    public int cooksNumber;
    public  int queueNumber;

    public void init(ArrayList<Pizza> pizzas, int cooks, int queue){
        this.availablePizzas =pizzas;
        this.cooksNumber = cooks;
        this.queueNumber = queue;
    }
}
