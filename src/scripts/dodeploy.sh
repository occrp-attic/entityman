#!/bin/bash

CUR=`cat /proc/self/cgroup | grep systemd`

if [ -f /root/store/firstrun ]; then\
	TMP=`cat /root/store/firstrun`
	
	if [ "$CUR" != "$TMP" ]; then
		echo "Removing old firstrun"
		rm /root/store/firstrun
	fi
fi

# deploy nerwrapper 
if [ ! -f /root/store/firstrun ]; then
	echo "Building entityman"
	mvn install -Dmaven.test.skip=true

	rm /root/store/*
  echo "$CUR" > /root/store/firstrun
fi

# deploy nerwrapper 
if [ ! -f /root/store/isdeployed_nerwrapper ]; then
	curl -T "nerwrapper/target/nerwrapper.war" "http://entityman:entityman@${NERWRAPPER_PORT_8080_TCP_ADDR}:8080/manager/text/deploy?path=/nerwrapper&update=true"
	if [ $? -eq 0 ]; then
    echo "1" > /root/store/isdeployed_nerwrapper
	fi
fi
	
# deploy gluttonwar 
if [ ! -f /root/store/isdeployed_gluttonwar ]; then
	curl -T "gluttonwar/target/gluttonwar.war" "http://entityman:entityman@${GLUTTONWAR_PORT_8080_TCP_ADDR}:8080/manager/text/deploy?path=/gluttonwar&update=true"
	if [ $? -eq 0 ]; then
    echo "1" > /root/store/isdeployed_gluttonwar
	fi
fi

# deploy resource
if [ ! -f /root/store/isdeployed_resource ]; then
	curl -T "resource/target/resource.war" "http://entityman:entityman@${RESOURCE_PORT_8080_TCP_ADDR}:8080/manager/text/deploy?path=/resource&update=true"
	if [ $? -eq 0 ]; then
    echo "1" > /root/store/isdeployed_resource
	fi
fi

# deploy ui 
if [ ! -f /root/store/isdeployed_ui ]; then
	curl -T "ui/target/ui.war" "http://entityman:entityman@${RESOURCE_PORT_8080_TCP_ADDR}:8080/manager/text/deploy?path=/ui&update=true"
	if [ $? -eq 0 ]; then
    echo "1" > /root/store/isdeployed_ui
	fi
fi	



	