package core.strategy;

import core.models.Visitor;

public abstract class Strategy {
    public abstract Visitor createVisitor();
    public abstract int calculateDelay();
}
