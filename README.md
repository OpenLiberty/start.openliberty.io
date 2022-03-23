# Open Liberty Starter

## How to deliver code and test
1) `git checkout main && git pull`
1) `git checkout -b feature-branch-name`
1) Push new commits to the feature branch
1) When done developing and testing locally, create a pull request to merge feature branch to `staging`
1) When pull request is merged into `staging`, wait for https://staging-starter.mybluemix.net/api/start/info to refresh with merged code
1) Visit https://ui-staging-openlibertyio.mybluemix.net/ to test the APIs from https://staging-starter.mybluemix.net
1) When ready for production, [open a pull request](https://github.com/OpenLiberty/start.openliberty.io/compare/main...staging) to merge `staging` to `main`
