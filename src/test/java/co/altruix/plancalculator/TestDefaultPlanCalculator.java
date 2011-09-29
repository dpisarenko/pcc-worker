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

package co.altruix.plancalculator;

import junit.framework.Assert;

import org.junit.Test;

import ru.altruix.commons.api.di.PccException;

import co.altruix.pcc.api.plancalculator.PlanCalculator;
import co.altruix.pcc.api.plancalculator.PlanCalculatorFactory;
import co.altruix.pcc.impl.plancalculator.DefaultPlanCalculatorFactory;

/**
 * @author DP118M
 * 
 */
public final class TestDefaultPlanCalculator {
    @Test
    public void testOnNullSchedulingObjects() {
        final PlanCalculatorFactory factory =
                new DefaultPlanCalculatorFactory();
        final PlanCalculator objectUnderTest = factory.create();

        objectUnderTest.setSchedulingObjects(null);
        try {
            objectUnderTest.run();
        } catch (NullPointerException exception) {
            Assert.fail(exception.getMessage());
        } catch (PccException exception) {
        }
    }
}
