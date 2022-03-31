#!/bin/bash

cur_dir=$(pwd | grep -o '[^/]*$')
if [ $cur_dir == "itest" ]
then
  cd ..
fi

echo Intergation testing began
wrong=0

chmod +x itest/test_index.sh
./itest/test_index.sh
if [ $? -eq 1 ]
then
  echo Failed!
  wrong=$(( $wrong + 1 ))
fi

chmod +x itest/test_freq.sh
./itest/test_freq.sh
if [ $? -eq 1 ]
then
  echo Failed!
  wrong=$(( $wrong + 1 ))
fi

chmod +x itest/test_info.sh
./itest/test_info.sh
if [ $? -eq 1 ]
then
  echo Failed!
  wrong=$(( $wrong + 1 ))
fi

chmod +x itest/test_tag.sh
./itest/test_tag.sh
if [ $? -eq 1 ]
then
  echo Failed!
  wrong=$(( $wrong + 1 ))
fi

chmod +x itest/test_lines.sh
./itest/test_lines.sh
if [ $? -eq 1 ]
then
  echo Failed!
  wrong=$(( $wrong + 1 ))
fi

echo Intergation testing finished
echo Wrong: $wrong

exit $wrong