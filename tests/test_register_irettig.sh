#!/usr/local/bin/bash

if (( $# < 2 )) 
then
  echo "Usage: test host port\n"
  exit 1
fi


HOST=$1
PORT=$2
EMAIL='irettig@purdue.edu'
PASS='12345'
FIRST='Ian'
LAST='Rettig'

test_var=$(nc -z -v -w 3 $HOST $PORT <<END
"REGISTER $EMAIL $PASS $FIRST $LAST"
END
)

if (( test_var=="REGISTER SUCCESS" ))
then
  exit 0
else
  echo "Register Failed"
  exit 1
fi
