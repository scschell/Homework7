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

Words.java was run five times for each data file. *Please note that the
timing profiler was run for the cracklib.txt and war.txt data files and
the sampling profiler was run for the dictionary data file. Using the
sampling profiler may have skewed the dictionary.txt results.
                
                Results for BinarySearchTree implementation

                cracklib                    dictionary                  war

Time (sec)      0.625034817                 1.374846445                 0.93535028
                0.643090718                 1.391103477                 0.944398742
                0.647903398                 1.385745871                 0.943656495
                0.644028897                 1.504298788                 0.925617312
                0.663665153                 1.373356658                 0.934856524
    Average     0.6447445966                1.4058702478                0.9367758706
Hot Spots       304106 BST.insert           300210 java.io.FileOutput   303934 java.util.rege.
                                            Stream.writeBytes           Pattern$CharProperty.match
                303934 java.util.regex.     300182 BST.find             303928 java.nio.CharBuffer.
                Pattern$CharProperty.match                              charAt
                                                                        303930 java.lang.Character.
                                                                        codePointat
Profiler        9.63%                       19.81%                      16.90%

