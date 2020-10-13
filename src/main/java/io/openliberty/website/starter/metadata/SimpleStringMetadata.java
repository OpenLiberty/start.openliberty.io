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

import javax.json.bind.annotation.JsonbProperty;

public class SimpleStringMetadata {
    @JsonbProperty("name")
    public String name;
    @JsonbProperty("type")
    public String type;
    @JsonbProperty("default")
    public String defaultValue;

    public SimpleStringMetadata() {}
    public SimpleStringMetadata(String n, String t, String dv) {
        name = n;
        type = t;
        defaultValue = dv;
    }
    
    public void updateString(String n) {
    	name = n;
    }
}