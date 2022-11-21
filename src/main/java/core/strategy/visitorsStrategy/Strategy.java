package core.strategy.visitorsStrategy;

import core.models.Visitor;

public abstract class Strategy {
    public abstract Visitor createVisitor();
    public abstract int calculateDelay();
}
