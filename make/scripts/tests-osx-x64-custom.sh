#! /bin/bash

export DYLD_LIBRARY_PATH=/usr/local/lib:$DYLD_LIBRARY_PATH

spath=`dirname $0`

. $spath/tests.sh  "`which java`" -d64 ../build-macosx $*


