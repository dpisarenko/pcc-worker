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

package co.altruix.existingeventsfilter;

import static co.altruix.pcc.api.booking2calendarevententry.Booking2CalendarEventEntryConverter.PCC_EVENT_MARKER;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;

import ru.altruix.commons.api.di.PccException;

import co.altruix.pcc.api.existingeventsfilter.ExistingEventsFilter;
import co.altruix.pcc.api.existingeventsfilter.ExistingEventsFilterFactory;
import co.altruix.pcc.impl.existingeventsfilter.DefaultExistingEventsFilterFactory;

/**
 * @author DP118M
 * 
 */
public final class TestDefaultExistingEventsFilter {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TestDefaultExistingEventsFilter.class);

    @Test
    public void testRun() {
        final ExistingEventsFilterFactory factory =
                new DefaultExistingEventsFilterFactory();
        final ExistingEventsFilter objectUnderTest = factory.create();

        final List<CalendarEventEntry> inputEvents =
                new LinkedList<CalendarEventEntry>();

        final CalendarEventEntry entryWithoutPccTag1 =
                getCalendarEntry("Bla-bla-bla 1");
        final CalendarEventEntry entryWithoutPccTag2 =
                getCalendarEntry("Bla-bla-bla 2");
        final CalendarEventEntry entryWithPccTag1 =
                getCalendarEntry("Bla-bla-bla 1 " + PCC_EVENT_MARKER);
        final CalendarEventEntry entryWithPccTag2 =
                getCalendarEntry("Bla-bla-bla 2 " + PCC_EVENT_MARKER);
        final CalendarEventEntry entryWithPccTag3 =
                getCalendarEntry("Bla-bla-bla 3 " + PCC_EVENT_MARKER);

        inputEvents.add(entryWithoutPccTag1);
        inputEvents.add(entryWithoutPccTag2);
        inputEvents.add(entryWithPccTag1);
        inputEvents.add(entryWithPccTag2);
        inputEvents.add(entryWithPccTag3);

        objectUnderTest.setExistingEvents(inputEvents);

        try {
            objectUnderTest.run();
        } catch (final PccException exception) {
            LOGGER.error("", exception);
            Assert.fail(exception.getMessage());
        }

        final List<CalendarEventEntry> eventsToDelete =
                objectUnderTest.getEventsToDelete();
        
        Assert.assertNotNull(eventsToDelete);
        Assert.assertEquals(3, eventsToDelete.size());
        Assert.assertTrue(eventsToDelete.contains(entryWithPccTag1));
        Assert.assertTrue(eventsToDelete.contains(entryWithPccTag2));
        Assert.assertTrue(eventsToDelete.contains(entryWithPccTag3));
        
        final List<CalendarEventEntry> eventsToImport =
                objectUnderTest.getEventsToImport();
        
        Assert.assertNotNull(eventsToImport);
        Assert.assertEquals(2, eventsToImport.size());
        Assert.assertTrue(eventsToDelete.contains(entryWithoutPccTag1));
        Assert.assertTrue(eventsToDelete.contains(entryWithoutPccTag2));
    }

    private CalendarEventEntry getCalendarEntry(final String aName) {
        final CalendarEventEntry entryWithoutPccTag1 = new CalendarEventEntry();
        entryWithoutPccTag1.setTitle(new PlainTextConstruct(aName));
        return entryWithoutPccTag1;
    }
}
