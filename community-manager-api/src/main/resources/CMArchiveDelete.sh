#!/bin/bash

archiveDir="$1" # logs directory path of Sterling Integrator
fileAge=$2 #Age of the files to be removed from Archive directory

find $archiveDir -maxdepth 1 -name "*" -type f -mtime +$fileAge -exec rm -f {} \;  #command to delete aged files from pcm archive location

exit;


