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
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbProperty;

public class EnumMetadata<T extends Enum<T>> {
    @JsonbProperty("name")
    public String name;
    @JsonbProperty("default")
    public String defaultValue;
    @JsonbProperty("options")
    public List<String> values = new ArrayList<>();

    public EnumMetadata() {}
    public EnumMetadata(String n, T dv, T[] v) {
        name = n;
        defaultValue = dv.toString();
        values = Arrays.asList(v).stream().map(t -> t.toString()).collect(Collectors.toList());
    }
}