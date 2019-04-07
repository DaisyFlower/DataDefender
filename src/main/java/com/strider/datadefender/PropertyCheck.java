/*
 *
 * Copyright 2014-2016, Armenak Grigoryan, and individual contributors as indicated
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
 *
 */



package com.strider.datadefender;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.strider.datadefender.utils.AppProperties.loadProperties;
import static com.strider.datadefender.utils.AppProperties.loadPropertiesFromClassPath;

/**
 * Check for properties required for execution of data defender and data anonymizer
 * and stop the execution if not all required properties are defined
 * @author Armenak Grigoryan
 */
public class PropertyCheck {
    private static final String[] fileDiscoveryProperties = {
        "probability_threshold", "english_tokens", "english_sentenses", "names", "models", "directories"
    };
    private static final String[] dataDiscoveryProperties = {
        "probability_threshold", "english_tokens", "names", "models", "limit", "extentions", "score_calculation",
        "threshold_count", "threshold_highrisk"
    };
    private static final String[] databaseProperties       = { "vendor", "driver", "username", "password", "url" };
    private static final String[] dataAnonymizerProperties = { "requirement", "batch_size" };
    private static final String   IS_NOT_DEFINED           = " is not defined";
    private static final String   PROPERTY                 = "Property ";

    @SuppressWarnings("unchecked")
    public static List check(final String utiity, final char option) throws DatabaseDiscoveryException {
        final List errors = new ArrayList<>();

        if ("file-discovery".equals(utiity)) {
            final Properties properties = loadPropertiesFromClassPath("filediscovery.properties");

            for (int i = 0; i < fileDiscoveryProperties.length; i++) {
                final String property = properties.getProperty(fileDiscoveryProperties[i]);

                if ((property == null) || "".equals(property)) {
                    errors.add(PROPERTY + fileDiscoveryProperties[i] + IS_NOT_DEFINED);
                }
            }
        } else if ("anonymize".equals(utiity)) {
            final Properties properties = loadProperties("anonymizer.properties");

            for (int i = 0; i < dataAnonymizerProperties.length; i++) {
                final String property = properties.getProperty(dataAnonymizerProperties[i]);

                if ((property == null) || "".equals(property)) {
                    errors.add(PROPERTY + dataAnonymizerProperties[i] + IS_NOT_DEFINED);
                }
            }
        } else if ("database-discovery".equals(utiity)) {
            if ("c".equals(option)) {
                final Properties properties = loadProperties("datadiscovery.properties");

                if (properties == null) {
                    errors.add("Column discovery properties are not defined");
                }
            } else if ("d".equals(option)) {
                final Properties properties = loadProperties("datadiscovery.properties");

                for (int i = 0; i < dataDiscoveryProperties.length; i++) {
                    final String property = properties.getProperty(dataDiscoveryProperties[i]);

                    if ((property == null) || "".equals(property)) {
                        errors.add(PROPERTY + dataDiscoveryProperties[i] + IS_NOT_DEFINED);
                    }
                }
            }
        }

        return errors;
    }

    @SuppressWarnings("unchecked")
    public static List checkDtabaseProperties() throws DatabaseDiscoveryException {
        final List<String> errors       = new ArrayList<>();
        final Properties   dbProperties = loadProperties("db.properties");

        for (int i = 0; i < databaseProperties.length; i++) {
            final String property = dbProperties.getProperty(databaseProperties[i]);

            if ((property == null) || "".equals(property)) {
                errors.add(PROPERTY + databaseProperties[i] + IS_NOT_DEFINED);
            }
        }

        return errors;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
