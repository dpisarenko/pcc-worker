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

package co.altruix.pcc.impl.immediatereschedulingrequestprocessor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.silverstrike.pcc.api.gcaltasks2pcc.GoogleCalendarTasks2PccImporter;
import at.silverstrike.pcc.api.gcaltasks2pcc.GoogleCalendarTasks2PccImporterFactory;
import at.silverstrike.pcc.api.model.UserData;
import at.silverstrike.pcc.api.persistence.Persistence;

import com.google.api.client.auth.oauth2.draft10.AccessTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessTokenRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.tasks.v1.Tasks;
import com.google.inject.Injector;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessor;
import co.altruix.pcc.impl.cdm.DefaultImmediateSchedulingRequest;

/**
 * @author DP118M
 * 
 */
class DefaultImmediateSchedulingRequestMessageProcessor implements
        ImmediateSchedulingRequestMessageProcessor {
    private static final String PCCHQ_COM = "pcchq.com";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultImmediateSchedulingRequestMessageProcessor.class);

    private static final String CLIENT_ID =
            "294496059397.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "J1JRmoTA-EmOjTwKkW-eLHLY";

    private Persistence persistence;

    private PccMessage message;

    private Injector injector;
    
    public void setMessage(final PccMessage aMessage) {
        this.message = aMessage;
    }

    public boolean isMessageProcessingSucceeded() {
        return true;
    }

    public void run() throws PccException {
        final DefaultImmediateSchedulingRequest request =
                (DefaultImmediateSchedulingRequest) this.message;

        final UserData userData = persistence.getUser(request.getUserId());
        LOGGER.debug(
                "Immediate rescheduling request for user {}, start processing",
                userData.getUsername());

        importDataFromGoogleTasks(userData);
        calculatePlan();
        exportDataToGoogleCalendar();

        LOGGER.debug(
                "Immediate rescheduling request for user {}, processing finished",
                userData.getUsername());
    }

    private void calculatePlan() {
    }

    private void exportDataToGoogleCalendar() {
        // TODO Auto-generated method stub

    }

    private void importDataFromGoogleTasks(final UserData aUserData) {
        final HttpTransport httpTransport = new NetHttpTransport();
        final JacksonFactory jsonFactory = new JacksonFactory();

        try {
            final AccessTokenResponse response =
                    new GoogleAccessTokenRequest.GoogleRefreshTokenGrant(
                            httpTransport,
                            jsonFactory,
                            CLIENT_ID, CLIENT_SECRET, aUserData.getGoogleTasksRefreshToken())
                            .execute();

            final GoogleAccessProtectedResource accessProtectedResource =
                    new GoogleAccessProtectedResource(
                            response.accessToken, httpTransport, jsonFactory,
                            CLIENT_ID, CLIENT_SECRET,
                            aUserData.getGoogleTasksRefreshToken());

            final Tasks service =
                    new Tasks(httpTransport, accessProtectedResource,
                            jsonFactory);
            service.setApplicationName(PCCHQ_COM);

            final GoogleCalendarTasks2PccImporterFactory importerFactory =
                    this.injector
                            .getInstance(GoogleCalendarTasks2PccImporterFactory.class);
            final GoogleCalendarTasks2PccImporter importer =
                    importerFactory.create();

            importer.setInjector(this.injector);
            importer.setService(service);
            importer.setUser(aUserData);

            importer.run();
        } catch (final IOException exception) {
            LOGGER.error("", exception);
        } catch (final PccException exception) {
            LOGGER.error("", exception);
        }
    }
    
    public void setInjector(final Injector aInjector) {
        if (aInjector != null) {
            this.persistence = aInjector.getInstance(Persistence.class);
            
            this.injector = aInjector;
        }
    }

}
