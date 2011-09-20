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

package co.altruix.pcc.api.googletasksimporter;

import java.util.List;

import at.silverstrike.pcc.api.model.SchedulingObject;
import at.silverstrike.pcc.api.model.UserData;
import ru.altruix.commons.api.conventions.SingleActivityModule;
import ru.altruix.commons.api.di.ModuleWithInjectableDependencies;

/**
 * @author DP118M
 * 
 */
public interface GoogleTasksImporter extends SingleActivityModule,
        ModuleWithInjectableDependencies {
    void setUser(final UserData aUser);

    void setClientSecret(final String aClientSecret);

    void setClientId(final String aClientId);

    void setConsumerKey(final String aConsumerKey);

    List<SchedulingObject> getCreatedTasks();
}
