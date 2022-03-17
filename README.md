# Open Liberty Starter

## How to update the starter cloud instance:
1) Download the server.zip from this repo, unzip it, and add it as a server to your wlp instance.
1) Run `mvn -B package` in this repo to build a new .war file with your changes.
1) Move the starter .war file to the apps directory of the server.
1) Run `./server package starter --include=usr` to package the server usr directory.
1) Commit the new starter.zip to this repo.

## How to deliver code and test
1) `git checkout main && git pull`
1) `git checkout -b feature-branch-name`
1) Push new commits to the feature branch
1) When done developing and testing locally, create a pull request to merge feature branch to `staging`
1) When pull request is merged into `staging`, wait for https://staging-starter.mybluemix.net/api/start/info to refresh with merged code
1) Visit https://ui-staging-openlibertyio.mybluemix.net/ to test the APIs from https://staging-starter.mybluemix.net
1) When ready for production, [open a pull request](https://github.com/OpenLiberty/start.openliberty.io/compare/main...staging) to merge `staging` to `main`
