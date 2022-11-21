package core.strategy.cooksStrategy;

import core.models.Cook;
import core.models.Visitor;

import java.util.ArrayList;

public abstract class Strategy {
    public abstract ArrayList<Cook> createCooks(int cooksNumber);
}
