package core.utils;

import core.models.CookType;
import core.models.PizzaState;

import java.util.Random;

public class Utils {
    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static CookType pizzaStateToCookType(PizzaState state){
        if(state ==PizzaState.created){
            return CookType.makingDough;
        } else if (state ==PizzaState.filling) {
            return CookType.filling;
        }else if (state ==PizzaState.baking) {
            return CookType.backing;
        }
        return CookType.multi;
    }
}
