/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.website.starter.metadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

public class EnumStringMetadata {
    @JsonbProperty("name")
    public String name;
    @JsonbProperty("default")
    public String defaultValue;
    @JsonbProperty("options")
    public List<String> values = new ArrayList<>();

    public EnumStringMetadata() {}
    public EnumStringMetadata(String n, String dv, String ... v) {
        name = n;
        defaultValue = dv;
        values = Arrays.asList(v);
    }
    
    public void updateString(String n) {
    	name = n;
    }
}