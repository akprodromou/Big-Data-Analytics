package com.anton.numeronym;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.StringTokenizer;

// Input text
public class NumeronymMapper extends Mapper<Object, Text, Text, IntWritable> {
    // IntWritable serializes the int data type, for writing and reading integer values as binary data
    private final static IntWritable oneValue = new IntWritable(1);
    // for storing the numeronyms
    private Text numeronym = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // κανονικοποίηση των γραμμών εισόδου αφαιρώντας σημεία στίξης και ειδικά σύμβολα
        String line = value.toString().toLowerCase().replaceAll("[^a-z0-9\\s]", " ");
        // tokens returned are words: defined as sequences of characters separated by whitespace
        StringTokenizer tokenizedLine = new StringTokenizer(line);

        // iterate over each word in the line
        while (tokenizedLine.hasMoreTokens()) {
            String word = tokenizedLine.nextToken();
            // map only words that are 3 characters or more
            if (word.length() >= 3) {
                // charAt() returns the character at the specified index in a string
                String num = word.charAt(0) + String.valueOf(word.length() - 2) + word.charAt(word.length() - 1);
                // numeronym is a Text object, which is mutable, hence reusable
                // num is a String, i.e. immutable
                // set() serializes the String
                numeronym.set(num);
                context.write(numeronym, oneValue);
            }
        }
    }
}
