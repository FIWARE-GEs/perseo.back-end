#!/bin/bash

DEFAULT_PERSEO_FE_URL=perseo_fe_endpoint

PERSEO_FE_URL_ARG=${1}
PERSEO_FE_URL_VALUE=${2}
if [ "$PERSEO_FE_URL_ARG" == "-perseo_fe_url" ]; then
    sed -i 's/'$DEFAULT_PERSEO_FE_URL'/'$PERSEO_FE_URL_VALUE'/g' /etc/perseo-core.properties
fi

touch /var/log/perseo/perseo-core.log


# We use tomcat from Apache, then will be started using catalina.sh, instead service tomcat
exec ${CATALINA_HOME}/bin/catalina.sh run && tail -f /var/log/perseo/perseo-core.log

