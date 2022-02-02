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
package io.openliberty.website.starter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.Test;

public class GradleStarterBuilderTest {

    @Test
    public void testJakartaEE8() throws UnsupportedEncodingException, IOException, XmlPullParserException {
        MockZipOutputStream mockZip = MockZipOutputStream.create().capture("build.gradle").capture("src/main/liberty/config/server.xml").capture("settings.gradle");
        BuildSystemType.gradle.create().appName("test").groupName("io.openliberty.demo").javaVersion("11")
                .jakartaEEVersion("8.0").microProfileVersion("3.3").buildType("gradle").build(mockZip);

        assertFilesPresent(mockZip);
        assertDependency(mockZip, "jakarta.platform", "jakarta.jakartaee-api", "8.0.0");
        assertDependency(mockZip, "org.eclipse.microprofile", "microprofile", "3.3");
        assertServerFeature(mockZip, "jakartaee-8.0");
        assertProjectName(mockZip, "test");
    }

    @Test
    public void testJavaEE7() throws UnsupportedEncodingException, IOException, XmlPullParserException {
        MockZipOutputStream mockZip = MockZipOutputStream.create().capture("build.gradle").capture("src/main/liberty/config/server.xml").capture("settings.gradle");
        BuildSystemType.gradle.create().appName("test").groupName("io.openliberty.demo").javaVersion("11")
                .jakartaEEVersion("7.0").microProfileVersion("3.3").buildType("gradle").build(mockZip);

        assertFilesPresent(mockZip);
        assertDependency(mockZip, "javax", "javaee-api", "7.0.0");
        assertDependency(mockZip, "org.eclipse.microprofile", "microprofile", "3.3");
        assertServerFeature(mockZip, "javaee-7.0");
        assertProjectName(mockZip, "test");
    }

    private void assertProjectName(MockZipOutputStream mockZip, String appName)
            throws UnsupportedEncodingException {
        //rootProject.name = '${appName}'
        String gradleSettings = new String(mockZip.getCapturedFile("settings.gradle"), "utf-8");

        String expectedContent = "rootProject.name = '" + appName + "'";

        assertTrue(gradleSettings.contains(expectedContent), "build.gradle should contain: " + expectedContent + " actually contains: " + gradleSettings);
    }

    private void assertDependency(MockZipOutputStream mockZip, String groupId, String artifactId,
            String version)
            throws UnsupportedEncodingException {
        String buildGradle = new String(mockZip.getCapturedFile("build.gradle"), "utf-8");

        String expectedDependency = "providedCompile '" + groupId + ":" + artifactId + ":" + version + "'";

        assertTrue(buildGradle.contains(expectedDependency), "build.gradle should contain: " + expectedDependency + " actually contains: " + buildGradle);
    }

    private void assertFilesPresent(MockZipOutputStream mockZip) {
        mockZip.assertPresent("gradlew");
        mockZip.assertPresent("gradlew.bat");
        mockZip.assertPresent("gradle/wrapper/gradle-wrapper.jar");
        mockZip.assertPresent("gradle/wrapper/gradle-wrapper.properties");
        mockZip.assertPresent("build.gradle");
        mockZip.assertPresent("settings.gradle");
        mockZip.assertPresent("src/main/liberty/config/server.xml");    }

    private void assertServerFeature(MockZipOutputStream mockZip,
            String featureName)
            throws UnsupportedEncodingException {
        String serverXml = new String(mockZip.getCapturedFile("src/main/liberty/config/server.xml"), "utf-8");
        assertTrue(serverXml.contains("<feature>" + featureName + "</feature>"), "The feature: " + featureName + " was expected in the server.xml: \r\n" + serverXml);
        assertFalse(serverXml.contains("<webApplication>"), "There should not be a webApplication defined in server.xml: \r\n" + serverXml);
    }

}