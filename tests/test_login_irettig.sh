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

test_var=$(nc -z -v -w 3 $HOST $PORT <<END
"REGISTER $EMAIL $PASS"
END
)

if (( test_var=="LOGIN SUCCESS" ))
then
  exit 0
else
  echo "Login Failed"
  exit 1
fi
