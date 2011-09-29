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

package co.altruix.pcc.impl.exporter2googlecalendar;

import java.io.IOException;
import java.net.URL;
import java.security.PrivateKey;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.silverstrike.pcc.api.model.Booking;
import at.silverstrike.pcc.api.model.UserData;
import at.silverstrike.pcc.api.privatekeyreader.PrivateKeyReader;
import at.silverstrike.pcc.api.privatekeyreader.PrivateKeyReaderFactory;
import at.silverstrike.pcc.impl.privatekeyreader.DefaultPrivateKeyReaderFactory;

import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthRsaSha1Signer;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.util.ServiceException;
import com.google.inject.Injector;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.booking2calendarevententry.Booking2CalendarEventEntryConverter;
import co.altruix.pcc.api.booking2calendarevententry.Booking2CalendarEventEntryConverterFactory;
import co.altruix.pcc.api.exporter2googlecalendar.Exporter2GoogleCalendar;

/**
 * @author DP118M
 * 
 */
class DefaultExporter2GoogleCalendar implements Exporter2GoogleCalendar {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultExporter2GoogleCalendar.class);

    private String consumerKey;

    private String calendarScope;

    private UserData user;

    private String allCalendarsFeedUrl;

    private List<Booking> bookings;

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

            final URL feedUrl =
                    new URL(
                            allCalendarsFeedUrl);
            final CalendarFeed resultFeed =
                    calendarService.getFeed(feedUrl, CalendarFeed.class);

            LOGGER.debug("resultFeed: {}", resultFeed);

            LOGGER.debug("Your calendars:");

            CalendarEntry pccCalendar = null;
            for (int i = 0; (i < resultFeed.getEntries().size())
                    && (pccCalendar == null); i++) {
                final CalendarEntry entry = resultFeed.getEntries().get(i);

                if ("PCC".equals(entry.getTitle().getPlainText())) {
                    pccCalendar = entry;
                }
            }

            // Delete all events in the PCC calendar

            LOGGER.debug(
                    "PCC calendar: edit link='{}', self link='{}', content='{}', id='{}'",
                    new Object[] { pccCalendar.getEditLink().getHref(),
                            pccCalendar.getSelfLink().getHref(),
                            pccCalendar.getContent(), pccCalendar.getId() });

            final String calendarId =
                    pccCalendar
                            .getId()
                            .substring(
                                    "http://www.google.com/calendar/feeds/default/calendars/"
                                            .length());
            final URL pccCalendarUrl =
                    new URL(
                            "https://www.google.com/calendar/feeds/${calendarId}/private/full"
                                    .replace("${calendarId}", calendarId));

            LOGGER.debug("pccCalendarUrl: {}", pccCalendarUrl);
            // calendarService.getFeed(feedUrl, feedClass)

            final CalendarEventFeed pccEventFeed =
                    calendarService.getFeed(pccCalendarUrl,
                            CalendarEventFeed.class);
            for (final CalendarEventEntry curEvent : pccEventFeed.getEntries()) {
                LOGGER.debug("Deleting event ''", curEvent);
                curEvent.delete();
            }

            if (this.bookings != null) {
                LOGGER.debug("Bookings to export: {}", bookings.size());
                exportBookings(calendarService, new URL(calendarId));
            }

        } catch (final IOException exception) {
            LOGGER.error("", exception);
        } catch (final OAuthException exception) {
            LOGGER.error("", exception);
        } catch (final ServiceException exception) {
            LOGGER.error("", exception);
        }

    }

    private void exportBookings(final CalendarService calendarService,
            final URL pccCalendarUrl) throws IOException, ServiceException,
            PccException {
        final Booking2CalendarEventEntryConverterFactory factory =
                this.injector
                        .getInstance(Booking2CalendarEventEntryConverterFactory.class);
        final Booking2CalendarEventEntryConverter converter = factory.create();

        for (final Booking curBooking : this.bookings) {
            LOGGER.debug(
                    "Exporting: start date time: {}, end date time: {}",
                    new Object[] { curBooking.getStartDateTime(),
                            curBooking.getEndDateTime() });

            converter.setBooking(curBooking);
            converter.run();

            final CalendarEventEntry event = converter.getCalendarEventEntry();

            calendarService.insert(pccCalendarUrl, event);
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
    public void setConsumerKey(final String aConsumerKey) {
        this.consumerKey = aConsumerKey;
    }

    @Override
    public void setCalendarScope(final String aCalendarScope) {
        this.calendarScope = aCalendarScope;
    }

    @Override
    public void setUser(final UserData aUser) {
        this.user = aUser;
    }

    @Override
    public void setAllCalendarsFeedUrl(final String aAllCalendarsFeedUrl) {
        this.allCalendarsFeedUrl = aAllCalendarsFeedUrl;
    }

    @Override
    public void setBookings(final List<Booking> aBookings) {
        this.bookings = aBookings;
    }

    @Override
    public void setInjector(final Injector aInjector) {
        this.injector = aInjector;
    }

}
