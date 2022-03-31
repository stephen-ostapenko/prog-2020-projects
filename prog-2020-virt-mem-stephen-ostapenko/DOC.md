# Virtual Memory Simulator

This application simulates 3 algorithms of virtual memory managing (FIFO, LRU, OPT) and compares their efficiency by
number of replacements in memory (see `README.md`).

## Usage

App runs with Gradle: run.

#### Text input mode

In this mode you specify virtual memory size, RAM size and queries inside a text file (see below for details).
Program simulates process of accessing memory and prints the result into file.

`gradle run --args="-f <input file> <output file>"`

For example:

`gradle run --args="-f input.txt output.txt"`

##### Input format

```
<virtual memory size>
<RAM size>
<initial memory>
<queries to memory>
```

First line contains single integer `N` which is equal to virtual memory size `(1 <= N)`.\
Second line contains single integer `M` which is equal to RAM size `(1 <= M <= N)`.\
Then `M` lines specifying what is stored in memory at an initial moment of time\
(each line contains a single integer `I` `(1 <= I <= N)`).\
The rest lines contain queries to virtual memory (each line contains a single integer `Q` `(1 <= Q <= N)`).

For example:\
`Input:`
```
7
3
1
2
4
1
4
7
6
5
4
3
```

Here we have `N = 7`, `M = 3`. Initially memory contains pages with numbers `1, 2, 4`. Queries are `1, 4, 7, 6, 5, 4, 3`.

`Output:`
```
FIFO: 5
LRU: 5
OPT: 4


```

Input samples can be found in `data/`.

#### Random input mode

This mode is used for benchmarking with several runs. For each run program generates random input with specified
virtual memory size, RAM size and number of queries to memory and then runs on each input.

`gradle run --args="-r <output file> <virtual memory size> <RAM size> <number of accesses> <nmber of runs>"`

For example:

`gradle run --args="-r output.txt 100 50 10000 10"`

`Possible output:`
```
FIFO: 4995
LRU: 5037
OPT: 2102

FIFO: 5057
LRU: 5027
OPT: 2098

FIFO: 4964
LRU: 4972
OPT: 2080

FIFO: 4962
LRU: 4920
OPT: 2082

FIFO: 4919
LRU: 4978
OPT: 2081

FIFO: 5108
LRU: 5077
OPT: 2127

FIFO: 5043
LRU: 5098
OPT: 2099

FIFO: 4947
LRU: 5000
OPT: 2081

FIFO: 4919
LRU: 4904
OPT: 2047

FIFO: 5050
LRU: 5073
OPT: 2107


```

#### Tests

Tests can be runned using `test.sh` (requires bash installed on your device).

`tst/` is a folder for tests.

## Issues

No issues detected at the moment.