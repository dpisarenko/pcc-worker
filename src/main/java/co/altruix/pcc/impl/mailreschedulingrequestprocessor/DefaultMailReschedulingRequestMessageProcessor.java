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

package co.altruix.pcc.impl.mailreschedulingrequestprocessor;

import java.util.List;

import javax.jms.Message;

import at.silverstrike.pcc.api.model.Booking;
import at.silverstrike.pcc.api.model.SchedulingObject;
import at.silverstrike.pcc.api.model.UserData;

import ru.altruix.commons.api.di.PccException;

import co.altruix.pcc.api.cdm.PccMessage;
import co.altruix.pcc.api.mailreschedulingrequestprocessor.MailReschedulingRequestMessageProcessor;
import co.altruix.pcc.api.schedulingrequestmessageprocessor.AbstractSchedulingRequestMessageProcessor;

/**
 * @author DP118M
 * 
 */
public class DefaultMailReschedulingRequestMessageProcessor extends
        AbstractSchedulingRequestMessageProcessor implements
        MailReschedulingRequestMessageProcessor {
    private Message message;
    
    @Override
    public void run() throws PccException {
        final String eMail = getUserMail(this.message);
        final UserData user = null/* persistence.getUserByUserName(eMail) */; 
        
        LOGGER.debug(
                "Immediate rescheduling request for user {}, start processing",
                user.getUsername());
    
        sendConfirmationForTester(user, START_CONFIRMATION_MESSAGE);
    
        final List<SchedulingObject> createdTasks =
                importDataFromGoogleTasks(user);
        final List<Booking> bookings = calculatePlan(user, createdTasks);
        exportDataToGoogleCalendar(user, bookings);
    
        LOGGER.debug(
                "Immediate rescheduling request for user {}, processing finished",
                user.getUsername());
    
        sendConfirmationForTester(user,
                END_CONFIRMATION_MESSAGE);

    }

    private String getUserMail(final Message aMessage) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMessage(final Message aMessage) {
        this.message = aMessage;
    }

    @Override
    public void setPccMessage(final PccMessage aMessage) {
        /**
         * We are not interested in PccMessages.
         */
    }
}
