/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.website.starter.metadata;

import javax.json.Json;
import javax.json.JsonObject;

public class Constants {

    public static final String[] SUPPORTED_JAVA_VERSIONS = new String[] { "8", "11", "17" };

    public static final String[] SUPPORTED_JAKARTAEE_VERSIONS = new String[] { "None", "7.0", "8.0", "9.1", "10" };

    public static final String[] SUPPORTED_MICROPROFILE_VERSIONS = 
        new String[] { "None", "1.4", "2.2", "3.3", "4.1", "5.0", "6" };

    public static final JsonObject JAKARTA_EE_MICROPROFILE_COMPATIBILITIES = Json.createObjectBuilder()
            .add("None", Json.createObjectBuilder().add("m", Json.createArrayBuilder().add("1.4").add("2.2").add("3.3").add("4.1").add("5.0").add("6")))
            .add("7.0", Json.createObjectBuilder().add("m", Json.createArrayBuilder().add("None").add("1.4")))
            .add("8.0", Json.createObjectBuilder().add("m", Json.createArrayBuilder().add("None").add("2.2").add("3.3").add("4.1")))
            .add("9.1", Json.createObjectBuilder().add("m", Json.createArrayBuilder().add("None").add("5.0")))
            .add("10", Json.createObjectBuilder().add("m", Json.createArrayBuilder().add("None").add("6")))
            .build();
}
