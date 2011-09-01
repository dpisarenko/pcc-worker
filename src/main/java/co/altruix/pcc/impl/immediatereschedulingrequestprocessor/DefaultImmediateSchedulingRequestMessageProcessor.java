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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.silverstrike.pcc.api.model.SchedulingObject;
import at.silverstrike.pcc.api.model.UserData;
import at.silverstrike.pcc.api.persistence.Persistence;

import com.google.inject.Injector;

import ru.altruix.commons.api.di.PccException;
import ru.altruix.commons.impl.appendutils.AppendUtils;
import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.exporter2googlecalendar.Exporter2GoogleCalendar;
import co.altruix.pcc.api.exporter2googlecalendar.Exporter2GoogleCalendarFactory;
import co.altruix.pcc.api.googletasksimporter.GoogleTasksImporter;
import co.altruix.pcc.api.googletasksimporter.GoogleTasksImporterFactory;
import co.altruix.pcc.api.immediatereschedulingrequestprocessor.ImmediateSchedulingRequestMessageProcessor;
import co.altruix.pcc.api.plancalculator.PlanCalculator;
import co.altruix.pcc.api.plancalculator.PlanCalculatorFactory;
import co.altruix.pcc.impl.cdm.DefaultImmediateSchedulingRequest;

/**
 * @author DP118M
 * 
 */
class DefaultImmediateSchedulingRequestMessageProcessor implements
        ImmediateSchedulingRequestMessageProcessor {
    private static final String TIMESTAMP_FORMAT = "dd.MM.yyyy HH:mm:ss";

    private static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

    private static final String END_CONFIRMATION_MESSAGE =
            "@{timestamp}: Finished calculation of plan for user '@{userId}'"
                    + LINE_SEPARATOR;

    private static final String START_CONFIRMATION_MESSAGE =
            "@{timestamp}: Started to calculate plan for user '@{userId}'"
                    + LINE_SEPARATOR;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultImmediateSchedulingRequestMessageProcessor.class);

    

    private Persistence persistence;

    private PccMessage message;

    private Injector injector;

    private String taskJugglerPath;

    private String consumerKey;

    private String calendarScope;

    private String allCalendarsFeedUrl;

    private String clientId;
    private String clientSecret;

    private File testerLogFilePath;

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

        sendConfirmationForTester(userData, START_CONFIRMATION_MESSAGE);

        final List<SchedulingObject> createdTasks = importDataFromGoogleTasks(userData);
        calculatePlan(userData, createdTasks);
        exportDataToGoogleCalendar(userData);

        LOGGER.debug(
                "Immediate rescheduling request for user {}, processing finished",
                userData.getUsername());

        sendConfirmationForTester(userData,
                END_CONFIRMATION_MESSAGE);
    }

    private void sendConfirmationForTester(final UserData aUser,
            String aTemplate) {
        try {
            final String[] searchList =
                    new String[] { "@{timestamp}", "@{userId}" };
            final String[] replacementList =
                    new String[] { getTimestamp(), Long.toString(aUser.getId()) };

            final String confirmationMessage =
                    StringUtils.replaceEach(aTemplate, searchList,
                            replacementList);

            AppendUtils.appendToFile(confirmationMessage,
                    this.testerLogFilePath);
        } catch (final IOException exception) {
            LOGGER.error("", exception);
        }
    }

    private String getTimestamp() {
        return new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date());
    }

    private void calculatePlan(final UserData aUser, final List<SchedulingObject> aCreatedTasks) {
        LOGGER.debug("calculatePlan, user: {}", aUser.getId());

        final PlanCalculatorFactory factory = this.injector.getInstance(PlanCalculatorFactory.class);
        final PlanCalculator calculator = factory.create();
        
        calculator.setSchedulingObjects(aCreatedTasks);
        calculator.setInjector(this.injector);
        calculator.setUser(aUser);
        calculator.setTaskJugglerPath(this.taskJugglerPath);
        try {
            calculator.run();
        } catch (final PccException exception) {
            LOGGER.error("", exception);
        }
        
    }

    private void exportDataToGoogleCalendar(final UserData aUser) {
        final Exporter2GoogleCalendarFactory factory = this.injector.getInstance(Exporter2GoogleCalendarFactory.class);
        final Exporter2GoogleCalendar exporter = factory.create();
        
        exporter.setAllCalendarsFeedUrl(this.allCalendarsFeedUrl);
        exporter.setCalendarScope(this.calendarScope);
        exporter.setConsumerKey(this.consumerKey);
        exporter.setInjector(this.injector);
        exporter.setUser(aUser);
        
        try {
            exporter.run();
        } catch (final PccException exception) {
            LOGGER.error("", exception);
        }
    }


    private List<SchedulingObject> importDataFromGoogleTasks(final UserData aUserData) {
        final GoogleTasksImporterFactory factory =
                this.injector.getInstance(GoogleTasksImporterFactory.class);
        final GoogleTasksImporter importer = factory.create();
        List<SchedulingObject> createdTasks = null;

        importer.setClientId(this.clientId);
        importer.setClientSecret(this.clientSecret);
        importer.setConsumerKey(this.consumerKey);
        importer.setInjector(this.injector);
        importer.setUser(aUserData);

        try {
            importer.run();
            createdTasks = importer.getCreatedTasks();
        } catch (final PccException exception) {
            LOGGER.error("", exception);
        }
        
        return createdTasks;
    }

    public void setInjector(final Injector aInjector) {
        if (aInjector != null) {
            this.persistence = aInjector.getInstance(Persistence.class);

            this.injector = aInjector;
        }
    }

    public void setTaskJugglerPath(final String aTaskJugglerPath) {
        this.taskJugglerPath = aTaskJugglerPath;
    }

    @Override
    public void setConsumerKey(final String aConsumerKey) {
        this.consumerKey = aConsumerKey;
    }

    @Override
    public void setCalendarScope(final String aCalendarScope) {
        this.calendarScope = aCalendarScope;
    }

    @Override
    public void setAllCalendarsFeedUrl(final String aAllCalendarsFeedUrl) {
        this.allCalendarsFeedUrl = aAllCalendarsFeedUrl;
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
    public void setTesterLogFilePath(final File aTesterLogFilePath) {
        this.testerLogFilePath = aTesterLogFilePath;
    }
}
