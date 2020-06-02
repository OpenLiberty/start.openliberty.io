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
package io.openliberty.website.starter.gradle;

import java.io.IOException;

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import io.openliberty.website.starter.AbstractBaseStarterBuilder;

public class GradleBuilder extends AbstractBaseStarterBuilder {

    private static final String BUILD_GRADLE;
    private static final String GRADLE_SETTINGS;

    static {
        BUILD_GRADLE = readFile(GradleBuilder.class.getResourceAsStream("build.gradle"));
        GRADLE_SETTINGS = readFile(GradleBuilder.class.getResourceAsStream("settings.gradle"));
    }

    @Override
    public boolean build(ZipArchiveOutputStream zipOut) {
        try {
            addDirectory(zipOut, "src/main/java/" + groupId.replaceAll("\\.", "/"));
            addExecutableFile(zipOut, "gradlew", GradleBuilder.class.getResourceAsStream("gradlew"));
            addFile(zipOut, "gradlew.bat", GradleBuilder.class.getResourceAsStream("gradlew.bat"));
            addFile(zipOut, "gradle/wrapper/gradle-wrapper.jar", GradleBuilder.class.getResourceAsStream("gradle-wrapper.jar"));
            addFile(zipOut, "gradle/wrapper/gradle-wrapper.properties", GradleBuilder.class.getResourceAsStream("gradle-wrapper.properties"));
            addFileWithPropertyReplacement(zipOut, "build.gradle", BUILD_GRADLE);
            addFileWithPropertyReplacement(zipOut, "settings.gradle", GRADLE_SETTINGS);
            addFileWithPropertyReplacement(zipOut, "src/main/liberty/config/server.xml", SERVER_XML);
            return true;
        } catch (IOException ioe) {

        }

        return false;
    }
}
