# Normal Algorithms Parser

## Info

This program is used to calculate the result of normal Markov substitution algorithm.

It can make up to 50.000 iterations in a single substitution scheme.

Program supports parsing of several schemes at once.

You can see examples in /data/.

## Usage

### Alphabet

You can use arabic digits, english letters, minus sign and underscore sign in your substitution scheme (64 characters are allowed to use at all).

### Input format

#### Substitutions

The format of a substitution is {substitution}#{replacement}{terminal}, where
* {substitution} is a string to substitute,
* {replacement} is a string to replace with,
* {terminal} can be "." (single dot) if this substitution is terminal or an empty string if not.

##### Examples
1) "abc#de" is a command for non-terminal substitution of string "abc" with string "de".
2) "abc#de." is a command for terminal substitution of string "abc" with string "de".
3) "#x" is a substitution of an empty string by string "x".
4) "#x." terminal version of 3).
5) "abc#" is a command to delete string "abc".

#### Single scheme

If you want to run a single scheme, you should do the following:
* Place the initial string at the top of the file,
* Place needed substitutions in next lines without spaces and empty lines.

See data/ex1.in for more details.

#### Multiple scheme

If you want to run several schemes, you should put them one after another, separating with "$" string.

See data/ex2.in for more details.

### Output format

For each scheme you will see the result on a separate line. If there is something wrong in the scheme, you will see a message about it.

### Execution

Program is executed in terminal and takes two arguments: name of input file and name of output file.

Example: "./${application name} input.txt output.txt"

This will take input from input.txt and write result to output.txt.

## Issues

This application can't calculate whether the process is finite, because it's an unsolved problem.

The program wasn't optimised and sometimes it works slowly.