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
        BuildSystemType.gradle.create().appName("test").groupName("io.openliberty.demo").javaVersion("17")
                .jakartaEEVersion("8.0").microProfileVersion("3.3").gradleVersion("8")
                .buildType("gradle").build(mockZip);

        assertFilesPresent(mockZip);
        assertDependency(mockZip, "jakarta.platform", "jakarta.jakartaee-api", "8.0.0");
        assertDependency(mockZip, "org.eclipse.microprofile", "microprofile", "3.3");
        assertServerFeature(mockZip, "jakartaee-8.0");
        assertProjectName(mockZip, "test");
    }

    @Test
    public void testJavaEE7() throws UnsupportedEncodingException, IOException, XmlPullParserException {
        MockZipOutputStream mockZip = MockZipOutputStream.create().capture("build.gradle").capture("src/main/liberty/config/server.xml").capture("settings.gradle");
        BuildSystemType.gradle.create().appName("test").groupName("io.openliberty.demo").javaVersion("17")
                .jakartaEEVersion("7.0").microProfileVersion("3.3").gradleVersion("8")
                .buildType("gradle").build(mockZip);

        assertFilesPresent(mockZip);
        assertDependency(mockZip, "javax", "javaee-api", "7.0");
        assertDependency(mockZip, "org.eclipse.microprofile", "microprofile", "3.3");
        assertServerFeature(mockZip, "javaee-7.0");
        assertProjectName(mockZip, "test");
    }

    @Test
    public void testGradle8Versions() throws UnsupportedEncodingException {
        MockZipOutputStream mockZip = buildWithGradleVersion("8");

        assertGeneratedVersions(mockZip, "8.14.5", "3.10.0");
    }

    @Test
    public void testGradle9Versions() throws UnsupportedEncodingException {
        MockZipOutputStream mockZip = buildWithGradleVersion("9");

        assertGeneratedVersions(mockZip, "9.7.1", "4.0.2");
    }

    private MockZipOutputStream buildWithGradleVersion(String gradleVersion) {
        MockZipOutputStream mockZip = MockZipOutputStream.create().capture("build.gradle")
                .capture("gradle/wrapper/gradle-wrapper.properties");
        BuildSystemType.gradle.create().appName("test").groupName("io.openliberty.demo").javaVersion("17")
                .jakartaEEVersion("11.0").microProfileVersion("7.1").gradleVersion(gradleVersion)
                .buildType("gradle").build(mockZip);
        return mockZip;
    }

    private void assertGeneratedVersions(MockZipOutputStream mockZip, String gradleVersion,
            String libertyGradlePluginVersion) throws UnsupportedEncodingException {
        String wrapperProperties = new String(
                mockZip.getCapturedFile("gradle/wrapper/gradle-wrapper.properties"), "utf-8");
        String buildGradle = new String(mockZip.getCapturedFile("build.gradle"), "utf-8");

        assertTrue(wrapperProperties.contains("gradle-" + gradleVersion + "-bin.zip"));
        assertTrue(buildGradle.contains("io.openliberty.tools.gradle.Liberty' version '"
                + libertyGradlePluginVersion + "'"));
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