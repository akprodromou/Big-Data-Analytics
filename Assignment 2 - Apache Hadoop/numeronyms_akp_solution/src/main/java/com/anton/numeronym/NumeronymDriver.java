package com.anton.numeronym;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class NumeronymDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println(
                    "Usage: NumeronymDriver <input path> <output path> <min occurrences (k)>"
            );
            System.exit(-1);
        }

        int k = Integer.parseInt(args[2]);
        // για να αποθηκεύσουμε όλες τις ρυθμίσεις και παραμέτρους της συγκεκριμένης εργασίας που θα τρέξουμε
        Configuration conf = new Configuration();
        // θέσε το ως παράμετρο
        conf.setInt("numeronym.min.count", k);

        // passing conf here so the Job inherits all Hadoop settings
        Job job = Job.getInstance(conf, "Numeronym Count");
        // προσδιορίζουμε ποιό JAR περιέχει τις κλάσεις μας
        job.setJarByClass(NumeronymDriver.class);
        // Hadoop will create instances of this class to process the input data, line by line
        job.setMapperClass(NumeronymMapper.class);
        job.setReducerClass(NumeronymReducer.class);
        // sets the data type of the output key for both Reducer and the final output file
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Specifies the input path for the job (τα περνάμε ως arguments)
        // Hadoop reads all files in this path as input for the Mapper
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        // true enables verbose progress reports in the console
        // returns 0 if the job succeeded or 1 if it failed, and exits the program.
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
