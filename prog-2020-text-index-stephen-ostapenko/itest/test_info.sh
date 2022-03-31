#!/bin/bash

echo Testing word info

diff <(gradle run --args="-i itest/test_info/Childhood.txt и Карл Иваныч голова бабушка хотеть наш будет любовь ничего время" -q) itest/test_info/Childhood.out
if [ $? -eq 1 ]
then
  echo Failed Childhood.txt
  exit 1
fi
echo Finished Childhood.txt

diff <(gradle run --args="-i itest/test_index/Post.txt я не как всё там пол комната человек пыль хорошо думать здоровье" -q) itest/test_info/Post.out
if [ $? -eq 1 ]
then
  echo Failed Post.txt
  exit 1
fi
echo Finished Post.txt

diff <(gradle run --args="-i itest/test_index/RusskoePoleEksperimentov.txt русский пол поле эксперимент вечность смех сердобольный нефть славно" -q) itest/test_info/RusskoePoleEksperimentov.out
if [ $? -eq 1 ]
then
  echo Failed RusskoePoleEksperimentov.txt
  exit 1
fi
echo Finished RusskoePoleEksperimentov.txt

echo Done!