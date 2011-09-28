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

package co.altruix.pcc.api.schedulingrequestmessageprocessor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.altruix.commons.api.di.PccException;
import ru.altruix.commons.impl.appendutils.AppendUtils;
import at.silverstrike.pcc.api.gtaskexporter.GoogleTasksExporter;
import at.silverstrike.pcc.api.gtaskexporter.GoogleTasksExporterFactory;
import at.silverstrike.pcc.api.model.Booking;
import at.silverstrike.pcc.api.model.SchedulingObject;
import at.silverstrike.pcc.api.model.UserData;
import at.silverstrike.pcc.api.persistence.Persistence;
import co.altruix.pcc.api.exporter2googlecalendar.Exporter2GoogleCalendar;
import co.altruix.pcc.api.exporter2googlecalendar.Exporter2GoogleCalendarFactory;
import co.altruix.pcc.api.googletasksimporter.GoogleTasksImporter;
import co.altruix.pcc.api.googletasksimporter.GoogleTasksImporterFactory;
import co.altruix.pcc.api.plancalculator.PlanCalculator;
import co.altruix.pcc.api.plancalculator.PlanCalculatorFactory;

import com.google.inject.Injector;

/**
 * @author DP118M
 * 
 */
public abstract class AbstractSchedulingRequestMessageProcessor {
    private static final String DIAGNOSTIC_GTASKS_FILENAME_TEMPLATE =
            "diagnostic_gtasks-${timestamp}.csv";
    private static final String DIAGNOSTIC_GTASKS_FILENAME =
            "yyyy-MM-dd___HH-mm-ss-SSS";
    protected static final String TIMESTAMP_FORMAT = "dd.MM.yyyy HH:mm:ss";
    protected static final String LINE_SEPARATOR = System
                .getProperty("line.separator");
    protected static final String END_CONFIRMATION_MESSAGE =
            "@{timestamp}: Finished calculation of plan for user '@{userId}'"
                        + LINE_SEPARATOR;
    protected static final String START_CONFIRMATION_MESSAGE =
            "@{timestamp}: Started to calculate plan for user '@{userId}'"
                        + LINE_SEPARATOR;
    protected Persistence persistence;

    private Injector injector;
    private String taskJugglerPath;
    private String consumerKey;
    private String calendarScope;
    private String allCalendarsFeedUrl;
    private String clientId;
    private String clientSecret;
    private File testerLogFilePath;

    public static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractSchedulingRequestMessageProcessor.class);

    public final boolean isMessageProcessingSucceeded() {
        return true;
    }

    protected final void sendConfirmationForTester(final UserData aUser,
            final String aTemplate) {
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

    protected final List<Booking> calculatePlan(final UserData aUser,
            final List<SchedulingObject> aCreatedTasks) {
        LOGGER.debug("calculatePlan, user: {}", aUser.getId());

        final PlanCalculatorFactory factory =
                this.injector.getInstance(PlanCalculatorFactory.class);
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
        return calculator.getBookings();
    }

    protected final void exportDataToGoogleCalendar(final UserData aUser,
            final List<Booking> aBookings) {
        final Exporter2GoogleCalendarFactory factory =
                this.injector.getInstance(Exporter2GoogleCalendarFactory.class);
        final Exporter2GoogleCalendar exporter = factory.create();

        exporter.setAllCalendarsFeedUrl(this.allCalendarsFeedUrl);
        exporter.setCalendarScope(this.calendarScope);
        exporter.setConsumerKey(this.consumerKey);
        exporter.setUser(aUser);
        exporter.setBookings(aBookings);

        try {
            exporter.run();
        } catch (final PccException exception) {
            LOGGER.error("", exception);
        }
    }

    protected final List<SchedulingObject> importDataFromGoogleTasks(
            final UserData aUserData) {
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

    public final void setInjector(final Injector aInjector) {
        if (aInjector != null) {
            this.persistence = aInjector.getInstance(Persistence.class);

            this.injector = aInjector;
        }
    }

    public final void setTaskJugglerPath(final String aTaskJugglerPath) {
        this.taskJugglerPath = aTaskJugglerPath;
    }

    public final void setConsumerKey(final String aConsumerKey) {
        this.consumerKey = aConsumerKey;
    }

    public final void setCalendarScope(final String aCalendarScope) {
        this.calendarScope = aCalendarScope;
    }

    public final void setAllCalendarsFeedUrl(final String aAllCalendarsFeedUrl) {
        this.allCalendarsFeedUrl = aAllCalendarsFeedUrl;
    }

    public final void setClientId(final String aClientId) {
        this.clientId = aClientId;
    }

    public final void setClientSecret(final String aClientSecret) {
        this.clientSecret = aClientSecret;
    }

    public final void setTesterLogFilePath(final File aTesterLogFilePath) {
        this.testerLogFilePath = aTesterLogFilePath;
    }

    protected void exportTasksToFile(final UserData aUser) {
        final GoogleTasksExporterFactory factory =
                this.injector.getInstance(GoogleTasksExporterFactory.class);
        final GoogleTasksExporter exporter = factory.create();

        exporter.setClientId(this.clientId);
        exporter.setClientSecret(this.clientSecret);
        exporter.setConsumerKey(this.consumerKey);
        exporter.setRefreshToken(aUser.getGoogleTasksRefreshToken());
        exporter.setTargetFile(getTimestampedFile());

        try {
            exporter.run();
        } catch (final PccException exception) {
            LOGGER.error("", exception);
        }

    }

    private File getTimestampedFile() {
        final SimpleDateFormat format =
                new SimpleDateFormat(DIAGNOSTIC_GTASKS_FILENAME);
        final String fileName =
                DIAGNOSTIC_GTASKS_FILENAME_TEMPLATE.replace("${timestamp}",
                        format.format(new Date()));
        return new File(fileName);
    }
}
