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

import co.altruix.pcc.api.exporter2googlecalendar.Exporter2GoogleCalendar;
import co.altruix.pcc.api.exporter2googlecalendar.Exporter2GoogleCalendarFactory;

/**
 * @author DP118M
 *
 */
public final class DefaultExporter2GoogleCalendarFactory implements
        Exporter2GoogleCalendarFactory {

    @Override
    public Exporter2GoogleCalendar create() {
        return new DefaultExporter2GoogleCalendar();
    }

}
