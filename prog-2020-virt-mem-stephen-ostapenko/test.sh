#!/bin/bash

wrong=0
for input in $(ls intTst | grep '.\.in')
do
  diff <(gradle run --args="-f intTst/$input intTst/.tmpOutput" -q) intTst/${input//.in/.out}

  if [ $? -eq 1 ]
  then
    echo Test $input failed!!!
    wrong=$(( $wrong + 1 ))
  else
    echo Test $input passed
  fi

  echo
done

echo Failed: $wrong