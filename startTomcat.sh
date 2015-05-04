#!/bin/sh
BASEDIR=$(dirname $0)
cd $BASEDIR
[ -z `docker-compose ps -q` ] && docker-compose up -d
exit 0
