#!/bin/sh
cd $1
startTime=$2
endTime=$3
git log --since="${startTime}" --until="${endTime}"  --all   --pretty=format:'%ce'