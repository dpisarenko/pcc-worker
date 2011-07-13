package co.altruix.pcc.impl.di;

import com.google.inject.Guice;
import com.google.inject.Injector;

import co.altruix.pcc.api.di.PccWorkerInjectorFactory;

public class DefaultPccWorkerInjectorFactory implements PccWorkerInjectorFactory {

    public Injector createInjector() {
        final InjectorModule injectorModule = new InjectorModule();
        final Injector injector = Guice.createInjector(injectorModule);

        return injector;

    }
}
