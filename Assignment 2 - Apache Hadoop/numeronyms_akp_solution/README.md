# Numeronym MapReduce

This project is a short Hadoop MapReduce application that generates numeronyms from text input and counts their occurrences.

## Project Aim

The application processes text files and performs the following operations:
1. Extracts all words that have 3 or more characters
2. Converts each word to its numeronym format
3. Counts how many words share the same numeronym pattern

A numeronym is an abbreviation where a word is shortened to its first letter, the count of letters in between, and its last letter. 

## Components
- **NumeronymDriver**: Main driver class that configures and runs the MapReduce job
- **NumeronymMapper**: Processes input text, converts words to numeronyms
- **NumeronymReducer**: Aggregates counts for each unique numeronym

## Running the App

Enter the below in a terminal to compile:
```bash
# Compile
javac -classpath $(hadoop classpath) -d numeronym_classes src/main/java/com/anton/numeronym/*.java

# Create JAR
jar -cvf numeronym.jar -C numeronym_classes/ .
```

and to run the main job:

```bash
hadoop jar numeronym.jar com.anton.numeronym.NumeronymDriver <input_path> <output_path>
```

## Results

The app was run with the "The Adventures of Sherlock Holmes" by Sir Arthur Conan Doyle as input,
as is provided in the Gutenberg library. The ten most frequent numeronyms found were:

```
t1e	80074
a1d	38444
t2t	12701
h1s	11633
w1s	11409
w2h	9995
h1d	7409
f1r	7382
t3e	6722
n1t	6656
```

Where each line shows:
- The numeronym pattern
- The count of words matching that pattern

## Requirements

- Java 8 or higher
- Hadoop 3.x
- Input text files stored in HDFS