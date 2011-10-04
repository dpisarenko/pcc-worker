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

package co.altruix.pcc.impl.gcaleventimporter;

import java.io.IOException;
import java.net.URL;
import java.security.PrivateKey;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.altruix.commons.api.di.PccException;

import at.silverstrike.pcc.api.model.UserData;
import at.silverstrike.pcc.api.privatekeyreader.PrivateKeyReader;
import at.silverstrike.pcc.api.privatekeyreader.PrivateKeyReaderFactory;
import at.silverstrike.pcc.impl.privatekeyreader.DefaultPrivateKeyReaderFactory;

import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthRsaSha1Signer;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.ServiceException;
import com.google.inject.Injector;

import co.altruix.pcc.api.existingeventsfilter.ExistingEventsFilter;
import co.altruix.pcc.api.existingeventsfilter.ExistingEventsFilterFactory;
import co.altruix.pcc.api.gcaleventimporter.GoogleCalendarEventImporter;

/**
 * @author DP118M
 * 
 */
final class DefaultGoogleCalendarEventImporter implements
        GoogleCalendarEventImporter {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultGoogleCalendarEventImporter.class);

    private String calendarScope;
    private String consumerKey;
    private List<CalendarEventEntry> eventsToDelete;
    private List<CalendarEventEntry> eventsToImport;
    private UserData user;
    private Injector injector;

    @Override
    public void run() throws PccException {
        try {
            final PrivateKey privKey = getPrivateKey();
            final OAuthRsaSha1Signer signer = new OAuthRsaSha1Signer(privKey);

            final GoogleOAuthParameters oauthParameters =
                    new GoogleOAuthParameters();
            oauthParameters.setOAuthConsumerKey(consumerKey);
            oauthParameters.setScope(calendarScope);

            oauthParameters.setOAuthVerifier(user
                    .getGoogleCalendarOAuthVerifier()); // Verifier from the
                                                        // interactive part
            oauthParameters.setOAuthToken(user.getGoogleCalendarOAuthToken()); // Access
                                                                               // token
                                                                               // from
                                                                               // the
                                                                               // interactive
                                                                               // part
            oauthParameters.setOAuthTokenSecret(user
                    .getGoogleCalendarOAuthTokenSecret()); // Token secret from
                                                           // the interactive
                                                           // part

            final CalendarService calendarService =
                    new CalendarService(consumerKey);

            calendarService
                    .setOAuthCredentials(oauthParameters, signer);

            final URL defaultCalendarUrl =
                    new URL(
                            "https://www.google.com/calendar/feeds/${email}/private/full"
                                    .replace("${email}", user.getUsername()));

            final CalendarEventFeed pccEventFeed =
                    calendarService.getFeed(defaultCalendarUrl,
                            CalendarEventFeed.class);

            final ExistingEventsFilterFactory factory =
                    this.injector
                            .getInstance(ExistingEventsFilterFactory.class);
            final ExistingEventsFilter eventFilter = factory.create();

            eventFilter.setExistingEvents(pccEventFeed.getEntries());
            eventFilter.run();

            this.eventsToDelete = eventFilter.getEventsToDelete();
            this.eventsToImport = eventFilter.getEventsToImport();
        } catch (final IOException exception) {
            LOGGER.error("", exception);
        } catch (final OAuthException exception) {
            LOGGER.error("", exception);
        } catch (final ServiceException exception) {
            LOGGER.error("", exception);
        }

    }
    private PrivateKey getPrivateKey() {
        final PrivateKeyReaderFactory factory =
                new DefaultPrivateKeyReaderFactory();
        final PrivateKeyReader reader = factory.create();

        reader.setInputStream(getClass().getClassLoader()
                        .getResourceAsStream("privatekey"));

        try {
            reader.run();

            return reader.getPrivateKey();
        } catch (final PccException exception) {
            LOGGER.error("", exception);
            return null;
        }
    }

    @Override
    public void setInjector(final Injector aInjector) {
        this.injector = aInjector;
    }

    public List<CalendarEventEntry> getEventsToDelete() {
        return eventsToDelete;
    }

    public List<CalendarEventEntry> getEventsToImport() {
        return eventsToImport;
    }

    public void setCalendarScope(final String aCalendarScope) {
        this.calendarScope = aCalendarScope;
    }

    public void setConsumerKey(final String aConsumerKey) {
        this.consumerKey = aConsumerKey;
    }
    public void setUser(UserData user) {
        this.user = user;
    }
}
