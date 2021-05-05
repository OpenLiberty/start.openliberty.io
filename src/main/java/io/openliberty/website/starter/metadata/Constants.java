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
import java.util.Map;
import java.util.HashMap;

public class Constants {
    public static final String[] SUPPORTED_JAVA_VERSIONS = new String[] {"8", "11", "15"};
    public static final String[] SUPPORTED_JAKARTAEE_VERSIONS = new String[] {"7", "8"};
    public static final String[] SUPPORTED_MICROPROFILE_VERSIONS = new String[] {"1.0", "1.1", "1.2", "1.3", "1.4", "2.0", "2.1", "2.2", "3.0", "3.2", "3.3"};
    // Versions of MicroProfile that each Jakarta EE version will with.
    public static final Map<String, ArrayList<String>> JAKARTA_EE_MICROPROFILE_COMPATIBILITIES = new HashMap<String, ArrayList<String>> () {{
        put("7", new ArrayList<String>(Arrays.asList("1.1", "1.2", "1.3", "1.4")));
        put("8", new ArrayList<String>(Arrays.asList("2.0", "2.1", "2.2", "2.2", "3.0", "3.2", "3.3")));
    }};
}