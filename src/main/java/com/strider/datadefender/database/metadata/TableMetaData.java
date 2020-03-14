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
package com.strider.datadefender.database.metadata;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Holds metadata for a table.
 *
 * @author Armenak Grigoryan
 */
@Data
public class TableMetaData implements Comparable<TableMetaData> {

    @Data
    public class ColumnMetaData implements Comparable<ColumnMetaData> {

        private final String columnName;
        private final Class  columnType;
        private final int columnSize;
        private final boolean isPrimaryKey;
        private final boolean isForeignKey;
        private final String foreignKeyReference;

        @Override
        public int compareTo(ColumnMetaData t) {
            return Comparator
                .comparing(ColumnMetaData::getColumnName)
                .compare(this, t);
        }

        @Override
        public String toString() {
            return TableMetaData.this.toString() + "." + columnName;
        }
    }

    private final String schemaName;
    private final String tableName;
    @Setter(AccessLevel.NONE)
    private List<ColumnMetaData> columns = new ArrayList<>();

    /**
     * Filters and returns a list of columns that are designated as primary
     * keys.
     * @return
     */
    public List<ColumnMetaData> getPrimaryKeys() {
        return columns.stream().filter((c) -> c.isPrimaryKey).collect(Collectors.toList());
    }

    /**
     * Filters and returns a list of columns that are designated as foreign
     * keys.
     * @return 
     */
    public List<ColumnMetaData> getForeignKeys() {
        return columns.stream().filter((c) -> c.isForeignKey).collect(Collectors.toList());
    }

    /**
     * Adds a ColumnMetaData to the list of columns.
     *
     * @param columnName
     * @param columnType
     * @param columnSize
     * @param isPrimaryKey
     * @param foreignKeyReference
     */
    public void addColumn(
        String columnName,
        Class columnType,
        int columnSize,
        boolean isPrimaryKey,
        String foreignKeyReference
    ) {
        columns.add(new ColumnMetaData(
            columnName,
            columnType,
            columnSize,
            isPrimaryKey,
            StringUtils.isNotBlank(foreignKeyReference),
            foreignKeyReference
        ));
    }

    @Override
    public int compareTo(TableMetaData t) {
        return Comparator
            .comparing(TableMetaData::getSchemaName)
            .thenComparing(TableMetaData::getTableName)
            .compare(this, t);
    }

    @Override
    public String toString() {
        if (schemaName != null) {
            return schemaName + "." + tableName;
        }
        return tableName;
    }
}