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

package co.altruix.pcc.impl.eventexporter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.When;

import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.eventexporter.EventExporter;

/**
 * @author DP118M
 * 
 */
class DefaultEventExporter implements EventExporter {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultEventExporter.class);

    private List<CalendarEventEntry> eventsToExport;
    private File targetFile;
    private static final String LINE_TEMPLATE =
            "${nr};${title};${start};${end}${lineSeparator}";
    private static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

    private static final String PLACEHOLDER_NR = "${nr}";
    private static final String PLACEHOLDER_TITLE = "${title}";
    private static final String PLACEHOLDER_START = "${start}";
    private static final String PLACEHOLDER_END = "${end}";
    private static final String PLACEHOLDER_LINE_SEPARATOR = "${lineSeparator}";

    private static final String[] SEARCH_LIST = new String[] { PLACEHOLDER_NR,
            PLACEHOLDER_TITLE,
            PLACEHOLDER_START, PLACEHOLDER_END,
            PLACEHOLDER_LINE_SEPARATOR };

    @Override
    public void run() throws PccException {
        if (this.eventsToExport == null) {
            return;
        }
        final StringBuilder fileContents = new StringBuilder();
        int counter = 0;
        for (final CalendarEventEntry curCalendarEntry : this.eventsToExport) {
            for (final When curTime : curCalendarEntry.getTimes()) {
                final String[] replacementList =
                        new String[] {
                                Integer.toString(counter++),
                                StringUtils.defaultString(curCalendarEntry
                                        .getTitle().getPlainText(), ""),
                                curTime.getStartTime().toString(),
                                curTime.getEndTime().toString(),
                                LINE_SEPARATOR
                };
                fileContents.append(StringUtils.replaceEach(LINE_TEMPLATE,
                        SEARCH_LIST, replacementList));

            }
        }
        try {
            FileUtils.writeStringToFile(this.targetFile,
                    fileContents.toString());
        } catch (final IOException exception) {
            LOGGER.error("", exception);
        }

    }

    @Override
    public void setEventsToExport(final List<CalendarEventEntry> aEvents) {
        this.eventsToExport = aEvents;
    }

    @Override
    public void setTargetFile(final File aFile) {
        this.targetFile = aFile;
    }
}
