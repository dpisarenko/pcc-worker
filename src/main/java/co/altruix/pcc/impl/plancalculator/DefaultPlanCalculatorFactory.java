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

import co.altruix.pcc.api.plancalculator.PlanCalculator;
import co.altruix.pcc.api.plancalculator.PlanCalculatorFactory;

/**
 * @author DP118M
 * 
 */
public final class DefaultPlanCalculatorFactory implements
        PlanCalculatorFactory {

    @Override
    public PlanCalculator create() {
        return new DefaultPlanCalculator();
    }

}
