Homework7
=========

Partners:

Name: Edward Schembor
E-mail: eschemb1@jhu.edu

Name: Sayge Schell
E-mail: sschell3@jhu.edu


Part 1: Baseline Experiments
============================

For part 1, we immedietly noticed that the datasets "dictionary.txt" and
"cracklib.txt" caused StackOverflow exceptions when run with the ordinary
BinarySearchTreeMap. This is because these datasets are ordered, so when
BTS rules are applied, the datapoints are only appended to the right side
of the tree. This results in essentially an extremely inefficient linked 
list of data. Eventually, when the insert method runs, there is too much
data to keep in memory and a StackOverflow exception is thrown. To fix 
this, we simply randomized the ordered the strings in these text files.
