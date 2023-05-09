# How to run automated UI starter tests

## For testing the Gradle versions of the starter:
- go to actions/workflows/verifyAllGradleStarterZips.yml
- Click the gray `Run workflow` button on the right hand side of the table 
- Select the branch and version of Java you wish to test and click the green 'Run workflow' button
- All valid combinations of this java version and the available jakarta and microprofile versions will be tested using gradle

## For testing the Maven versions of the starter:
- go to actions/workflows/verifyAllMavenStarterZips.yml
- Click the gray `Run workflow` button on the right hand side of the table 
- Select the branch and version of Java you wish to test and click the green 'Run workflow' button
- All valid combinations of this java version and the available jakarta and microprofile versions will be tested using maven
