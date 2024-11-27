#!/bin/bash

# Specify the directory where your Java crash logs are located
log_directory="./"

# Check if the directory exists
if [ ! -d "$log_directory" ]; then
    echo "Directory $log_directory does not exist."
    exit 1
fi

# Find and delete all hs_err_pid*.log files in the specified directory
echo "Deleting Java crash log files in $log_directory ..."

find "$log_directory" -name "hs_err_pid*.log" -type f -exec rm -f {} \;

echo "Deletion complete."
