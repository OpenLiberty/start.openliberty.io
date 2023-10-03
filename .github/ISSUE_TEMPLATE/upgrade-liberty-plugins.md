---
name: Upgrade Liberty Plug-ins
about: Request to upgrade the Liberty Maven and Gradle Plug-in versions
title: 'Upgrade to latest Liberty Plug-in versions'
labels: ""
assignees: ''

---
# Upgrade to the latest Liberty Maven and Gradle Plug-ins Releases

***Make sure to also update the `Add to an existing application` snippets on the start page via the [website code](https://github.com/OpenLiberty/openliberty.io)**

**Steps**
1. Get the latest versions of the Liberty Plug-ins
  - https://github.com/OpenLiberty/ci.maven/releases
  - https://github.com/OpenLiberty/ci.gradle/releases

2. Create a new feature branch off of `main` branch

3. Update Liberty Maven Plug-in version
  - https://github.com/OpenLiberty/start.openliberty.io/blob/main/src/main/resources/templates/maven/pom.xml
    ```
      <plugin>
        <groupId>io.openliberty.tools</groupId>
        <artifactId>liberty-maven-plugin</artifactId>
        <version>...</version>
      </plugin>
    ```

4. Update the Liberty Gradle Plug-in version
  - https://github.com/OpenLiberty/start.openliberty.io/blob/main/src/main/resources/templates/gradle/build.gradle
    ```
      plugins {
          id 'war'
          id 'io.openliberty.tools.gradle.Liberty' version '...'
      }
    ```

5. Update the Liberty Maven Plug-in version **used by the Open Liberty Starter service itself**. This step is like Step 3.
  - https://github.com/OpenLiberty/start.openliberty.io/blob/main/pom.xml

6. Open a pull request to merge the new feature branch (step 2) targeting branch `staging`.
