#!/bin/bash

toolDir=$(PWD)

#Trigger Shell scripts of all tools to change to common configuration
cd $toolDir/../tools/covert
./runCommonConfig.sh

cd ../flowDroid
./runCommonConfig.sh

cd ../horndroid
./runCommonConfig.sh

cd ../ic3
./runCommonConfig.sh

cd ../iccTA
./runCommonConfig.sh
