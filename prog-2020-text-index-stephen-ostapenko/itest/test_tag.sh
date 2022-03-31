#!/bin/bash

echo Testing tag words finding

diff <(gradle run --args="-t itest/test_tag/Childhood.txt 'предмет мебели' 'глагол движения'" -q) itest/test_tag/Childhood.out
if [ $? -eq 1 ]
then
  echo Failed Childhood.txt
  exit 1
fi
echo Finished Childhood.txt

diff <(gradle run --args="-t itest/test_tag/Post.txt 'предмет мебели' 'глагол движения'" -q) itest/test_tag/Post.out
if [ $? -eq 1 ]
then
  echo Failed Post.txt
  exit 1
fi
echo Finished Post.txt

diff <(gradle run --args="-t itest/test_tag/RusskoePoleEksperimentov.txt 'предмет мебели' 'глагол движения'" -q) itest/test_tag/RusskoePoleEksperimentov.out
if [ $? -eq 1 ]
then
  echo Failed RusskoePoleEksperimentov.txt
  exit 1
fi
echo Finished RusskoePoleEksperimentov.txt

echo Done!