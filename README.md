# Open Liberty Starter

## How to update the starter cloud instance:
1) Download the starter.zip from this repo, unzip it, and add it as a server to your wlp instance.
2) Run `mvn -B package` in this repo to build a new .war file with your changes.
3) Move the starter .war file to the apps directory of the server.
4) Run `./server package starter --include=usr` to package the server usr directory.
5) Commit the new starter.zip to this repo.
