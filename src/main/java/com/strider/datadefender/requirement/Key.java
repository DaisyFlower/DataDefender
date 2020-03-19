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
package com.strider.datadefender.requirement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import lombok.Data;

/**
 * JAXB class defining a Table's key column.
 *
 * Represents a single key column in a table's primary key - could be part of a
 * set comprising a compound key.
 *
 * @see Table.getKeys
 * @author Zaahid Bateson
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Key {
    /**
     * The column name in the database
     */
    @XmlAttribute(name = "Name")
    private String name;

    public Key() {
    }

    public Key(String name) {
        this.name = name;
    }
}
