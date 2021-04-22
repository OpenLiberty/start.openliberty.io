#!/bin/bash
function pushApp {
  cf push "${CF_APP}" -p starter.zip --no-start
  if [[ -z $CURRENT_STATE ]]
  then
    cf bind-service "${CF_APP}" "${AVAILABILITY_SERVICE}"
  fi
  cf start "${CF_APP}"
  cf set-env "${CF_APP}" LAST_DEPLOY_NUMBER "${BUILD_NUMBER}"
}
