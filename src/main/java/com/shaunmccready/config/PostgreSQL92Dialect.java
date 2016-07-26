/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2014, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package com.shaunmccready.config;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

/**
 * An SQL dialect for Postgres 9.2 and later, adds support for JSON data type
 * <p/>
 * NOTE: This class comes from hibernate-core master (https://github.com/hibernate/hibernate-orm/blob/master/hibernate-core/src/main/java/org/hibernate/dialect/PostgreSQL92Dialect.java)
 * It is from 4.4 SNAPSHOT. If we ever upgrade, we can use the built-in hibernate class instead of this.
 *
 * @author Mark Robinson
 * @author Shaun McCready
 */
public class PostgreSQL92Dialect extends PostgreSQL9Dialect {

    /**
     * Constructs a PostgreSQL92Dialect
     */
    public PostgreSQL92Dialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "json");
    }
}
