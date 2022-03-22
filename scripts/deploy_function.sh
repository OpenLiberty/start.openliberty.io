#!/bin/bash
function pushApp {
  mkdir -p server_directory/apps/
  cp src/main/liberty/config/server.xml server_directory/
  cp target/openliberty-starter-1.0-SNAPSHOT.war server_directory/apps/

  cf stop "${CF_APP}"
  cf push "${CF_APP}" --no-start -p server_directory
  
  if [[ -z $CURRENT_STATE ]]
  then
    cf bind-service "${CF_APP}" "${AVAILABILITY_SERVICE}"
  fi
  cf start "${CF_APP}"
  cf set-env "${CF_APP}" LAST_DEPLOY_NUMBER "${BUILD_NUMBER}"
}
