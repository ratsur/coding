Instruction to run 

The code is build using openjdk-17.0.1
uses gradle as the build tool 

The jar file is located in build/libs folder.
Command to run the 
java -jar <pathToAboveJar> <absolutePathToDirYouWantTOIndex>

Please see runningSnap.png for example run

<b>Note: Currently the search consider's everything splitted with space as a word.
It doesn't ignore punctuation marks. (I was not sure if punctuation marks to be ignored or not)
</b>

The main method to run the code is in
com/ratsur/search/Main.java
<br>This launches a console to enter the directory path 
<br>Indexes only the text files.
<br>Post that it opens an interactive search so the user can put words and search.

FileIndexer.java - 

<p>
Index Algorithm - 
This file has the core logic for Indexing the file. 
It creates an HashMap(fileWordsMap) of all the files in the input dir as key 
and a Map of words and word count as value.
Time Complexity O(nxmxk) where n is the number of files in the dir
    m the number of lines and k number of words 
Space Complexity 
    Requires space to hold the file names and distinct words inside the files
    O(nxm) where m is the number of distinct words. keeps the count of the distinct words in each file
</p>


Search Algorithm
It takes a string containing words as input. 
Creates an Hashmap of words and word count in the input string (wordCountMap). 

    Iterates over all the files in fileWordsMap created above by Index Algo.
    For every word in wordCountMap
    initilize count  = zero 
    Check if word present in fileWordsMap's value
    IF yes then take the minimum of count from the fileWordsMap's value and wordCountMap
    Do for all words in wordCountMap and keep adding the count 
    At the end count will have the count of words found in given file
    IF count > 0  
        Ranking = (count/totalWordsInInputString)*100
    Also maintains a SearchResults of files to have a max of MAX_SIZE default (10)
    which internally uses a PriorityQueue to have at MAX 10 results in order of ranking



SearchResults.java
It internally uses a PriorityQueue of SearchResult to hold only max results as required

SearchResult.java
Simple pojo class to hold filename and ranking. Implements comparable to be compared based on ranking
If the ranking is same then uses alphabetical order of filename.

Please have a look at runningSnap.png in case any confusion in running the jar





