package core.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import core.services.VisitorsService;


public class InjectionModule extends AbstractModule {
    @Override
    protected void configure() {
        //managers
        bind(VisitorsService.class).in(Scopes.SINGLETON);

    }
}
