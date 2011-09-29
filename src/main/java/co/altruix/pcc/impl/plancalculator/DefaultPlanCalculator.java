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

package co.altruix.pcc.impl.plancalculator;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;

import at.silverstrike.pcc.api.export2tj3.InvalidDurationException;
import at.silverstrike.pcc.api.model.Booking;
import at.silverstrike.pcc.api.model.Resource;
import at.silverstrike.pcc.api.model.SchedulingObject;
import at.silverstrike.pcc.api.model.UserData;
import at.silverstrike.pcc.api.persistence.Persistence;
import at.silverstrike.pcc.api.projectscheduler.ProjectScheduler;
import ru.altruix.commons.api.di.PccException;
import co.altruix.pcc.api.plancalculator.PlanCalculator;

/**
 * @author DP118M
 * 
 */
class DefaultPlanCalculator implements PlanCalculator {
    private static final int ONE_MONTH = 1;
    
    private List<SchedulingObject> schedulingObjects;
    private Injector injector;
    private UserData user;
    private String taskJugglerPath;
    private List<Booking> bookings;
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultPlanCalculator.class);

    @Override
    public void run() throws PccException {
        if (this.schedulingObjects == null)
        {
            LOGGER.error("schedulingObjects is null, exiting run method.");
            return;
        }
        
        this.bookings = null;
        
        final Persistence persistence = this.injector.getInstance(Persistence.class);
        
        final ProjectScheduler scheduler =
                injector.getInstance(ProjectScheduler.class);

        LOGGER.debug("SCHEDULING OBJECTS TO EXPORT (START)");
        for (final SchedulingObject curSchedulingObject : schedulingObjects) {
            LOGGER.debug("Name: {}, ID: {}",
                    new Object[] { curSchedulingObject.getName(),
                            curSchedulingObject.getId() });
        }
        LOGGER.debug("SCHEDULING OBJECTS TO EXPORT (END)");

        scheduler.getProjectExportInfo().setSchedulingObjectsToExport(
                schedulingObjects);

        final List<Resource> resources = new LinkedList<Resource>();
        resources.add(persistence.getCurrentWorker(user));

        scheduler.getProjectExportInfo().setResourcesToExport(resources);

        scheduler.getProjectExportInfo().setProjectName("pcc");

        final Date now = new Date();

        scheduler.getProjectExportInfo().setNow(now);
        scheduler.getProjectExportInfo().setCopyright("Dmitri Pisarenko");
        scheduler.getProjectExportInfo().setCurrency("EUR");
        scheduler.getProjectExportInfo().setSchedulingHorizonMonths(ONE_MONTH);
        scheduler.getProjectExportInfo().setUserData(user);

        scheduler.setDirectory(System.getProperty("user.dir") + "/");
        scheduler.setInjector(injector);
        scheduler.setNow(now);
        scheduler.setTaskJugglerPath(taskJugglerPath);
        scheduler.setTransientMode(true);
        try {
            scheduler.run();
            this.bookings = scheduler.getBookings();
        } catch (final InvalidDurationException exception) {
            LOGGER.error("", exception);
        } catch (final PccException exception) {
            LOGGER.error("", exception);
        }

    }

    @Override
    public void setSchedulingObjects(final List<SchedulingObject> aObjects) {
        this.schedulingObjects = aObjects;
    }

    public void setInjector(final Injector aInjector) {
        this.injector = aInjector;
    }

    @Override
    public void setUser(final UserData aUser) {
        this.user = aUser;
    }

    @Override
    public void setTaskJugglerPath(final String aTaskJugglerPath) {
        this.taskJugglerPath = aTaskJugglerPath;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

}
