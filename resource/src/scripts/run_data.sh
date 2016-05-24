#!/bin/bash

if [ ! -d /var/lib/mongodb ]; then
  mkdir -p /var/lib/mongodb && chown mongodb:mongodb /var/lib/mongodb
fi
if [ ! -d /var/log/mongodb ]; then
  mkdir -p /var/log/mongodb && chown mongodb:mongodb /var/log/mongodb
fi

CONF=/etc/mongod.conf

mongodb_cmd="mongod --config $CONF --bind_ip 0.0.0.0"

cmd="$mongodb_cmd"

$cmd
