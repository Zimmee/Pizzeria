package core.strategy.cooksStrategy;

import core.models.Cook;
import core.models.CookState;
import core.models.CookType;

import java.util.ArrayList;

public class SingleTaskStrategy extends Strategy{


    @Override
    public ArrayList<Cook> createCooks(int cooksNumber) {
        var cooks = new ArrayList<Cook>();
        for (int i = 0; i < cooksNumber; i++) {
            var type = CookType.values()[i%3];
            cooks.add(new Cook(type, CookState.free));
        }

        return cooks;
    }
}
