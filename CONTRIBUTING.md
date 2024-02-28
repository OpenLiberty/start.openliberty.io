

# Contributing to start.openliberty.io

Anyone can contribute to the start.openliberty.io project and we welcome your contributions!

 - [Legal](#legal)
 - [Raising issues](#raising-issues)
 - [Setup Guide](#setup-guide)
 - [How to deliver your changes](#how-to-deliver-code-and-test-on-staging-environment)


## Legal
### Contributor License Agreement

In order for us to accept pull requests, you must declare that you wrote the code or, at least, have the right to contribute it to the repo under the open source licence of the project in the repo. It's dead easy...

1. Read this (from [developercertificate.org](http://developercertificate.org/)):

  ```
  Developer Certificate of Origin
Version 1.1

Copyright (C) 2004, 2006 The Linux Foundation and its contributors.
660 York Street, Suite 102,
San Francisco, CA 94110 USA

Everyone is permitted to copy and distribute verbatim copies of this
license document, but changing it is not allowed.


Developer's Certificate of Origin 1.1

By making a contribution to this project, I certify that:

(a) The contribution was created in whole or in part by me and I
    have the right to submit it under the open source license
    indicated in the file; or

(b) The contribution is based upon previous work that, to the best
    of my knowledge, is covered under an appropriate open source
    license and I have the right under that license to submit that
    work with modifications, whether created in whole or in part
    by me, under the same open source license (unless I am
    permitted to submit under a different license), as indicated
    in the file; or

(c) The contribution was provided directly to me by some other
    person who certified (a), (b) or (c) and I have not modified
    it.

(d) I understand and agree that this project and the contribution
    are public and that a record of the contribution (including all
    personal information I submit with it, including my sign-off) is
    maintained indefinitely and may be redistributed consistent with
    this project or the open source license(s) involved.
  ```

2. If you can certify that it is true, sign off your `git commit` with a message like this:
  ```
  Signed-off-by: Laura Cowen <laura.cowen@email.com>
  ```
  You must use your real name (no pseudonyms or anonymous contributions, sorry).
  
  Instead of typing that in every git commit message, your Git tools might let you automatically add the details for you. If you configure them to do that, when you issue the `git commit` command, just add the `-s` option.

If you are an IBMer, please contact us directly as the contribution process is
slightly different.


## Raising issues

Please raise any bug reports on the [issue tracker](https://github.com/OpenLiberty/start.openliberty.io/issues). Be sure to search the list to see if your issue has already been raised.

A good bug report makes it easy for us to understand what you were trying to do and what went wrong. Provide as much context as possible so we can try to recreate the issue.

## Setup Guide
### How to run locally in dev mode
1) Clone the repository
2) Run `cd start.openliberty.io`
3) Run `mvn liberty:dev` 
4) Then you can test all endpoints at `https://localhost:9443/api/`   

### Running/Debugging using Liberty Tools 
1) [Using Liberty Tools for IntelliJ](https://github.com/OpenLiberty/liberty-tools-intellij/blob/main/docs/user-guide.md)
2) [Using Liberty Tools for Eclipse](https://github.com/OpenLiberty/liberty-tools-eclipse/blob/main/docs/user-guide.md)
3) [Using Liberty Tools for Visual Studio Code](https://github.com/OpenLiberty/liberty-tools-vscode/blob/HEAD/docs/user-guide.md)

### How to run tests
1) Run `mvn liberty:dev -DhotTests`

## How to deliver code and test on staging environment
1) `git checkout main && git pull`
2) `git checkout -b feature-branch-name`
3) Push new commits to the feature branch
4) When done developing and testing locally, create a pull request to merge feature branch to `staging`
5) When pull request is merged into `staging`, wait for https://starter-staging.rh9j6zz75er.us-east.codeengine.appdomain.cloud/api/start/info to refresh with merged code
6) Visit https://ui-staging-openlibertyio.mqj6zf7jocq.us-south.codeengine.appdomain.cloud/start/ to test the APIs from https://starter-staging.rh9j6zz75er.us-east.codeengine.appdomain.cloud/
7) When ready for production, [open a pull request](https://github.com/OpenLiberty/start.openliberty.io/compare/main...staging) to merge `staging` to `main`
