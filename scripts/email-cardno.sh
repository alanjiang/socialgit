#!/bin/sh
cd $1
startTime="$2"
endTime="$3"
git log --since="${startTime}" --until="${endTime}" --grep="\#[0-9]\{1,\}" --oneline --author=$4 --all 
