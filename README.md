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


Part 2: Spinning AVL Trees
==========================

//TODO Finish data analysis section

Words.java was run five times for each data file. *Please note that the timing profiler
was run for the cracklib.txt and war.txt data files and the sampling profiler was run for
the dictionary data file. Using the sampling profiler may have skewed the dictionary.txt
results.
                AVL Tree Map Implementation Results :

                cracklib                    dictionary                  war

Time (sec)      0.66275807                  1.504598647                 1.023242583
                0.680427409                 1.481751269                 1.033800726
                0.663901082                 1.508372056                 1.036844761
                0.684004796                 1.501435195                 1.009787024
                0.656835751                 1.48845912                  1.021393528
    Average     0.6695854216                1.4969232574                1.0250137244
Hot Spots       304125 AvlTreeMap.insert    300213 java.io              303934 java.util.regex.
                                            FileOutputStream.writeBytes Pattern$CharProperty.match
                304122 AvlTreeMap$Node.     300184 AvlTreeMap.find      303928 java.nio.CharBuffer.charAt
                access$000                                              .charAt
                304127 AvlTreeMap.balance                               303930 java.lang.Character
                                                                        .codePointAt
Profiler        23.98%                      20.59%                      16.77%


Analysis of Data: As one can see from our tests, the AVL tree is actually slower than our original Binary Search tree. This may be due to what needs to be added to keep the AVL tree balanced. For example, the AVL tree needs to keep track of heights and rotate sub-trees, while the BST does not. 

Part 3: Creepy Treaps
=====================


Words.java was run five times for each data file. *Please note that the timing profiler
was run for the cracklib.txt and war.txt data files and the sampling profiler was run for
the dictionary data file. Using the sampling profiler may have skewed the dictionary.txt
results.
                Treap Map Implementation Results :

                cracklib                    dictionary                  war

Time (sec)     0.655766418                  1.471288315                     1.03459172
               0.663439028                  1.498586679                     1.042862616
               0.684952291                  1.504919772                     1.075101934
               0.67410363                   1.494140051                     1.032936898
               0.672926003                  1.487561221                     1.049623024
    Average    0.670237474                  1.4912992076                    1.0470232384
Hot Spots      304130 TreapMap.insert       300199 java.io.FileOutputStream 303951 java.util.regex.
                                            .writeBytes                     Pattern$CharProperty.match
               303950 java.util.regex.      300180 TreapMap.find            303945 java.nio.CharBuffer.charAt
               Pattern$CharProperty.match   
               303944 java.nio.CharBuffer                                   303947 java.lang.Character.codePointAt
               .charAt
                                                                            304134 TreapMap.find
Profiler       13.40%                       25.34%                          14.03%

Analysis of Data: Our treap implementation actually ran slower than our original Binary Search Tree. This may be due to multiple factors. Although the worst case for a Treap runs faster than for that of a plain Binary Search Tree (O(logn) vs O(n)), it does not mean that a Treap will always run faster than a Binary Search Tree when given randomized data. The advantage of a Treap would become more apparent when dealing with already sorted data. Instead of consistently adding to one side of the tree until the tree becomes so tall that it takes O(n) time to insert, the Treap would sort the data left and right leading to faster insertion. We assume that the original intention of the sorted cracklib.txt and dictionary.txt files was to show this, but unfortunately, the Binary Search Tree height became so large that StackOverflow exceptions were caused and the data had to be shuffled in order to avoid this problem. Assuming that the original (sorted) text files did not cause an error, we would see that the Treap would run much faster than the Binary Search Tree (and also the AVL tree!). However, since the data was randomized, it led to more randomized Binary Search Tree shapes, such that the height did not get sufficiently large such that the time to find the node in order to insert in the BST implementation did not exceed that of the time to find and rotate the nodes in the Treap implementation. It should also be noted that while the Treap did not always run faster than the AVL Tree, the time spent in the map methods were generally shorter.
