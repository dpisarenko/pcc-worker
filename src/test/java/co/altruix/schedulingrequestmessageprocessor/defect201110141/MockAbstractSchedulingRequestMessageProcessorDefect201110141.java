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

package co.altruix.schedulingrequestmessageprocessor.defect201110141;

import at.silverstrike.pcc.api.model.UserData;
import at.silverstrike.pcc.impl.mockpersistence.MockObjectFactory;
import co.altruix.pcc.api.schedulingrequestmessageprocessor.AbstractSchedulingRequestMessageProcessor;

/**
 * @author DP118M
 *
 */
public class MockAbstractSchedulingRequestMessageProcessorDefect201110141
        extends AbstractSchedulingRequestMessageProcessor {
    public void run()
    {
        final MockObjectFactory objectFactory = new MockObjectFactory();
        final UserData user = objectFactory.createUserData();
        
        user.setGoogleCalendarOAuthToken(null);
        user.setGoogleCalendarOAuthTokenSecret(null);
        user.setGoogleCalendarOAuthVerifier(null);
        user.setGoogleTasksRefreshToken(null);

        this.importDataFromGoogleTasks(user);
    }
}
