/*
 * Copyright 2014, Armenak Grigoryan, and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package com.strider.datadefender.requirement.registry;

import com.strider.datadefender.database.IDbFactory;

/**
 * Configures a member variable IDbFactory in a concrete 'initialize' method.
 *
 * @author Zaahid Bateson <zaahid.bateson@ubc.ca>
 */
public abstract class DatabaseAwareRequirementFunction extends RequirementFunction {

    protected IDbFactory dbFactory;

    public final void initialize(IDbFactory dbFactory) {
        this.dbFactory = dbFactory;
    }
}
