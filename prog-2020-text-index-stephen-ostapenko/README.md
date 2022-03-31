# Text Index Builder

This program is used to build text index and do some specific tasks with it. Powered by Gradle.

### Capabilities

This program can:
1) Build text index and output it to terminal.
2) Find most frequently used words (taking in count same words in different forms).
3) Information about occurrences of specified word (number of occurrences, line and page numbers, used forms).
4) Finding words in text by specified tag.
5) Printing lines with occurrences of specified word.

### Usage

Program runs with Gradle: run.

It can take any coherent text with russian words and do some specific tasks with it.

#### Tasks

##### Build text index and output it to terminal.

`run --args="-x <input file name>"`

For example:

`run --args="-x input.txt"`

##### Build text index and output it to special storage file (force build).

`run --args="-f <input file name>"`

For example:

`run --args="-f input.txt"`


##### Find most frequently used words (taking in count same words in different forms).

`run --args="-n <input file name> <number of words to find>"`

For example:

`run --args="-n input.txt 10"`

##### Information about occurrences of specified word (number of occurrences, line and page numbers, used forms).

`run --args="-i <input file name> <words>"`

For example:

`run --args="-i input.txt word1 word2 word3 wordN"`

##### Finding words in text by specified tag.

`run --args="-t <input file name> <tags>"`

For example:

`run --args="-t input.txt tag1 tag2 tag3 tagN"`

##### Printing lines with occurrences of specified word.

`run --args="-w <input file name> <words>"`

For example:

`run --args="-w input.txt word1 word2 word3 wordN"`

##### File output

You can add `o` to runtime key if you want to write output to file.\
If you do that, you have to state `<input file name> <output file name>` instead of `<input file name>`.

**Doesn't work with `-f` key.**

For example:

`run --args="-xo input.txt output.txt"`\
`run --args="-no input.txt output.txt 10"`\
`run --args="-io input.txt output.txt word1 word2 word3 wordN"`\
`run --args="-to input.txt output.txt tag1 tag2 tag3 tagN"`\
`run --args="-wo input.txt output.txt word1 word2 word3 wordN"`

#### Index

**Attention! This application will try to store built index in a special resource file `<input file name>.index` (for example index for file `input.txt` will be stored in file `input.txt.index`).**

**If you already have file with this name, it will be overwritten.**

Index automatically rebuilds after each change in input file. If you want to force index rebuild, you can either\
execute `gradle run --args="-x <input file name>"` or delete a file with index or modify input file.

**If folder with input file has limited access to it, you will have problems.**

**Manual rewriting of this file will cause some problems too.**

#### Dictionaries

This program uses two dictionaries stored in `dict/` (and the third one is used for testing).

After downloading you will find archive `dict/dictionaries.zip` with these dictionaries. Gradle will automatically extract them and place into folder `dict/` during the first run. If something goes wrong, run `gradle install`.

First is a dictionary for forms of words. It is called `dict/formDict`. If you want to use your own dictionary, you can just replace original dictionary in `dict/`.

Second is a dictionary with tags. It is called `dict/tagDict`. If you want to use your own dictionary, you can just replace original dictionary in `dict/`.

You can also find dictionary which is used for testing (`dict/testDict`).

#### Tests

Integration tests can be ran with `gradle itest` or with `itest/test.sh`.

### Issues

Program is based on two dictionaries (form and tag). Since they are not wonderful, program output can be wrong or rubbish.