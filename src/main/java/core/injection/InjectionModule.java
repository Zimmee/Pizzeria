package core.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import core.services.CooksService;
import core.services.PizzeriaService;
import core.services.VisitorsService;


public class InjectionModule extends AbstractModule {
    @Override
    protected void configure() {
        //managers
        bind(VisitorsService.class).in(Scopes.SINGLETON);
        bind(PizzeriaService.class).in(Scopes.SINGLETON);
        bind(CooksService.class).in(Scopes.SINGLETON);
    }
}
