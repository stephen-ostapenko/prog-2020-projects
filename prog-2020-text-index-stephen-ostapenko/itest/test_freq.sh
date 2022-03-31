#!/bin/bash

echo Testing frequent words finding

diff <(gradle run --args="-n itest/test_index/Childhood.txt 10000" -q) itest/test_freq/Childhood.out
if [ $? -eq 1 ]
then
  echo Failed Childhood.txt
  exit 1
fi
echo Finished Childhood.txt

diff <(gradle run --args="-n itest/test_index/Post.txt 10000" -q) itest/test_freq/Post.out
if [ $? -eq 1 ]
then
  echo Failed Post.txt
  exit 1
fi
echo Finished Post.txt

diff <(gradle run --args="-n itest/test_index/RusskoePoleEksperimentov.txt 10000" -q) itest/test_freq/RusskoePoleEksperimentov.out
if [ $? -eq 1 ]
then
  echo Failed RusskoePoleEksperimentov.txt
  exit 1
fi
echo Finished RusskoePoleEksperimentov.txt

echo Done!