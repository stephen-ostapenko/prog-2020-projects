#!/bin/bash

echo Testing index building

diff <(gradle run --args="-x itest/test_index/Childhood.txt" -q) itest/test_index/Childhood.out
if [ $? -eq 1 ]
then
  echo Failed Childhood.txt
  exit 1
fi
echo Finished Childhood.txt

diff <(gradle run --args="-x itest/test_index/Post.txt" -q) itest/test_index/Post.out
if [ $? -eq 1 ]
then
  echo Failed Post.txt
  exit 1
fi
echo Finished Post.txt

diff <(gradle run --args="-x itest/test_index/RusskoePoleEksperimentov.txt" -q) itest/test_index/RusskoePoleEksperimentov.out
if [ $? -eq 1 ]
then
  echo Failed RusskoePoleEksperimentov.txt
  exit 1
fi
echo Finished RusskoePoleEksperimentov.txt

echo Done!