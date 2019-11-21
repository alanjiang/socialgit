#!/bin/sh
cd $1

#git log --since='2019-08-06 00:00:00' --until='2019-08-06 23:59:59' --author=jiangpeng@agilean.cn --all --no-merges --pretty=tformat: --numstat | gawk '{ add += $1 ; subs += $2 ; loc += $1 - $2 } END { printf "added:%s,removed:%s,total:%s\n",add,subs,loc }' 

startTime="$2"
endTime="$3"
git log --since="${startTime}" --until="${endTime}" --author=$4 --all --no-merges --pretty=tformat: --numstat | gawk '{ add += $1 ; subs += $2 ; loc += $1 - $2 } END { printf "added:%s,removed:%s,total:%s",add,subs,loc }' 
