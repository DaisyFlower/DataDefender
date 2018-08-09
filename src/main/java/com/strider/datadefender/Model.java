/*
 *
 * Copyright 2014-2015, Armenak Grigoryan, and individual contributors as indicated
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

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.tokenize.Tokenizer;

public class Model {
    final private Tokenizer    tokenizer;
    final private NameFinderME nameFinder;
    final private String       name;

    public Model(final Tokenizer tokenizer, final NameFinderME nameFinder, final String name) {
        this.name       = name;
        this.tokenizer  = tokenizer;
        this.nameFinder = nameFinder;
    }

    public String getName() {
        return this.name;
    }

    public NameFinderME getNameFinder() {
        return this.nameFinder;
    }

    public Tokenizer getTokenizer() {
        return this.tokenizer;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
