package core.strategy.cooksStrategy;

import core.models.Cook;
import core.models.CookState;
import core.models.CookType;

import java.util.ArrayList;

public class MultiTaskStrategy extends Strategy{


    @Override
    public ArrayList<Cook> createCooks(int cooksNumber) {
        var cooks = new ArrayList<Cook>();
        for (int i = 0; i < cooksNumber; i++) {
            cooks.add(new Cook(i, CookType.multi, CookState.free));
        }
        return cooks;
    }
}
