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

import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonObject;

public class Constants {
    public static final String[] SUPPORTED_JAVA_VERSIONS = new String[] {"8", "11", "15"};
    public static final String[] SUPPORTED_JAKARTAEE_VERSIONS = new String[] {"7", "8"};
    public static final String[] SUPPORTED_MICROPROFILE_VERSIONS = new String[] {"1.0", "1.1", "1.2", "1.3", "1.4", "2.0", "2.1", "2.2", "3.0", "3.2", "3.3"};

    public static final JsonObject JAKARTA_EE_MICROPROFILE_COMPATIBILITIES = Json.createObjectBuilder()
    .add("7", Json.createObjectBuilder().add("m", Json.createArrayBuilder().add("1.1").add("1.2").add("1.3").add("1.4"))).add("8", Json.createObjectBuilder()
    .add("m", Json.createArrayBuilder().add("1.1").add("1.2").add("1.3").add("1.4")))
    .build();
}