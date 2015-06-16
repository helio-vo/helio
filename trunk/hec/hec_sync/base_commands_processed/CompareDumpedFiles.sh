#!/bin/bash

for f in DBCompare/*.dump
do
s=$( stat -c %s $f)
n=$( stat -c %s $f.new)

if [ $s -eq $n ]
then
      echo "File $f.new and $f are same size"
fi

if [ $s -gt $n ]
then
      echo "Problem: Previous Dump $f is bigger than new $f.new, rolling back"
      echo ${f#_prev*}
      subTableName=${f%_prev*}
      truncTable=${subTableName:10}
      truncSQL="Truncate Table $truncTable;"
      sed -i 1i"$truncSQL" $f
      psql -d hec -f $f
fi

if [ $s -lt $n ]
then
      echo "Possibly New Data: File $f.new is bigger than $f, possibly new data"
fi

done
 
