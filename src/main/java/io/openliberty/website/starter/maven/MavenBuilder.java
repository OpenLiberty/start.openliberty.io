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
package io.openliberty.website.starter.maven;

import java.io.IOException;
import java.util.zip.ZipOutputStream;

import io.openliberty.website.starter.AbstractBaseStarterBuilder;

public class MavenBuilder extends AbstractBaseStarterBuilder {

    private static final String POM_XML;

    static {
        POM_XML = readFile(MavenBuilder.class.getResourceAsStream("pom.xml"));
    }

    @Override
    public boolean build(ZipOutputStream zipOut) {
        try {
            addDirectory(zipOut, "src/main/java/" + groupId.replaceAll("\\.", "/"));
            addFile(zipOut, "mvnw", MavenBuilder.class.getResourceAsStream("mvnw"));
            addFile(zipOut, "mvnw.cmd", MavenBuilder.class.getResourceAsStream("mvnw.cmd"));
            addFile(zipOut, ".mvn/wrapper/maven-wrapper.jar", MavenBuilder.class.getResourceAsStream("maven-wrapper.jar"));
            addFile(zipOut, ".mvn/wrapper/maven-wrapper.properties", MavenBuilder.class.getResourceAsStream("maven-wrapper.properties"));
            addFileWithPropertyReplacement(zipOut, "pom.xml", POM_XML);
            addFileWithPropertyReplacement(zipOut, "src/main/liberty/config/server.xml", SERVER_XML);
            return true;
        } catch (IOException ioe) {

        }

        return false;
    }
}
