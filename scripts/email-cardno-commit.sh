#!/bin/sh
cd $1

startTime="$2"
endTime="$3"

git log  --since="${startTime}" --until="${endTime}" --author="$4"  --grep="$6" --all --no-merges --pretty=tformat: --numstat | gawk '{ add += $1 ; subs += $2 ; loc += $1 - $2 } END { printf "added:%s,removed:%s,total:%s",add,subs,loc }'