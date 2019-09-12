#!/bin/bash

LINES=50000
i=1

while [[ 1 ]]; do
	echo "            - ${i}"
	((i++))
	#sleep 1
	if [[ $i -gt $LINES ]]; then
		break
	fi
done
