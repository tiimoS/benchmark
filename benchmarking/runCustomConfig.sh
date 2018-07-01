#!/bin/bash

toolDir=$(PWD)

#Trigger Shell scripts of all tools to change to common configuration
cd $toolDir/../tools/covert
./runCustomConfig.sh

cd ../flowDroid
./runCustomConfig.sh

cd ../horndroid
./runCustomConfig.sh

cd ../ic3
./runCustomConfig.sh

cd ../iccTA
./runCustomConfig.sh
