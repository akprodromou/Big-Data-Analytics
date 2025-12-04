package com.anton.numeronym;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.StringTokenizer;

public class NumeronymMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text numeronym = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().toLowerCase().replaceAll("[^a-z0-9\\s]", " ");
        StringTokenizer itr = new StringTokenizer(line);

        while (itr.hasMoreTokens()) {
            String word = itr.nextToken();
            if (word.length() >= 3) {
                String num = word.charAt(0) + String.valueOf(word.length() - 2) + word.charAt(word.length() - 1);
                numeronym.set(num);
                context.write(numeronym, one);
            }
        }
    }
}
