package co.altruix.schedulingrequestmessageprocessor;

import at.silverstrike.pcc.api.gtaskexporter.GoogleTasksExporterFactory;
import at.silverstrike.pcc.api.persistence.Persistence;
import at.silverstrike.pcc.impl.gtaskexporter.DefaultGoogleTasksExporterFactory;
import at.silverstrike.pcc.impl.mockpersistence.MockPersistenceAdapter;

import com.google.inject.AbstractModule;

final class MockInjectorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GoogleTasksExporterFactory.class).toInstance(
                new DefaultGoogleTasksExporterFactory());
        bind(Persistence.class).toInstance(new MockPersistenceAdapter() {
        });
    }

}
