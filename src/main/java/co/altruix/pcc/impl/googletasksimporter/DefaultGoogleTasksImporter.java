/**
 * This file is part of Project Control Center (PCC).
 * 
 * PCC (Project Control Center) project is intellectual property of 
 * Dmitri Anatol'evich Pisarenko.
 * 
 * Copyright 2010, 2011 Dmitri Anatol'evich Pisarenko
 * All rights reserved
 *
 **/

package co.altruix.pcc.impl.googletasksimporter;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.draft10.AccessTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessTokenRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.tasks.v1.Tasks;
import com.google.inject.Injector;

import at.silverstrike.pcc.api.gcaltasks2pcc.GoogleCalendarTasks2PccImporter;
import at.silverstrike.pcc.api.gcaltasks2pcc.GoogleCalendarTasks2PccImporterFactory;
import at.silverstrike.pcc.api.model.SchedulingObject;
import at.silverstrike.pcc.api.model.UserData;
import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.googletasksimporter.GoogleTasksImporter;

/**
 * @author DP118M
 * 
 */
class DefaultGoogleTasksImporter implements GoogleTasksImporter {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultGoogleTasksImporter.class);

    private UserData user;
    private String clientId;
    private String clientSecret;
    private String consumerKey;
    private Injector injector;
    private List<SchedulingObject> createdTasks;

    @Override
    public void run() throws PccException {
        final HttpTransport httpTransport = new NetHttpTransport();
        final JacksonFactory jsonFactory = new JacksonFactory();

        try {
            final String googleTasksRefreshToken =
                user.getGoogleTasksRefreshToken();

            LOGGER.debug("googleTasksRefreshToken: {}", googleTasksRefreshToken);

            final AccessTokenResponse response =
                    new GoogleAccessTokenRequest.GoogleRefreshTokenGrant(
                            httpTransport,
                            jsonFactory,
                            clientId, clientSecret,
                            googleTasksRefreshToken)
                            .execute();

            final GoogleAccessProtectedResource accessProtectedResource =
                    new GoogleAccessProtectedResource(
                            response.accessToken, httpTransport, jsonFactory,
                            clientId, clientSecret,
                            googleTasksRefreshToken);

            final Tasks service =
                    new Tasks(httpTransport, accessProtectedResource,
                            jsonFactory);
            service.setApplicationName(this.consumerKey);

            final GoogleCalendarTasks2PccImporterFactory importerFactory =
                    this.injector
                            .getInstance(GoogleCalendarTasks2PccImporterFactory.class);
            final GoogleCalendarTasks2PccImporter importer =
                    importerFactory.create();

            importer.setInjector(this.injector);
            importer.setService(service);
            importer.setUser(user);

            importer.run();
            
            this.createdTasks = importer.getCreatedPccTasks();
        } catch (final IOException exception) {
            LOGGER.error("", exception);
        } catch (final PccException exception) {
            LOGGER.error("", exception);
        }

    }

    public void setUser(final UserData aUser) {
        this.user = aUser;
    }

    @Override
    public void setClientId(final String aClientId) {
        this.clientId = aClientId;
    }

    @Override
    public void setClientSecret(final String aClientSecret) {
        this.clientSecret = aClientSecret;
    }

    @Override
    public void setConsumerKey(final String aConsumerKey) {
        this.consumerKey = aConsumerKey;
    }

    public void setInjector(final Injector aInjector) {
        this.injector = aInjector;
    }

    @Override
    public List<SchedulingObject> getCreatedTasks() {
        return createdTasks;
    }
}
