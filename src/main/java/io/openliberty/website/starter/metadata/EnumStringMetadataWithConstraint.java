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
import javax.json.Json;
import javax.json.JsonObject;

import javax.json.bind.annotation.JsonbProperty;

public class EnumStringMetadataWithConstraint extends EnumStringMetadata {
    @JsonbProperty("constraints")
    public JsonObject constraints;

    public EnumStringMetadataWithConstraint() {}
    public EnumStringMetadataWithConstraint(String n, String dv, JsonObject c, String ... v) {
        super(n, dv, v);
        constraints = c;
    }
    
    public void updateString(String n) {
    	name = n;
    }
}