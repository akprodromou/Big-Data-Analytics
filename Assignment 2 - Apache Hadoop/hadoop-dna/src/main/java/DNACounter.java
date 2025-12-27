import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured; // New import
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool; // New import
import org.apache.hadoop.util.ToolRunner; // New import

public class DNACounter extends Configured implements Tool {

    @Override
    // ρυθμίζει τις παραμέτρους της εργασίας
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: DNATupleCounter <input file> <output path>");
            // return non-zero for error
            return -1;
        }

        // get the Configuration from the Configured base class
        Configuration conf = this.getConf();
        Job job = Job.getInstance(conf, "DNA Tuple Counter");

        job.setJarByClass(DNACounter.class);
        // ορίζουμε τον Mapper και τον Reducer
        job.setMapperClass(CountMapper.class);
        job.setReducerClass(CountReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // δώσε το αποτέλεσμα
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        // αναλαμβάνει την προετοιμασία του περιβάλλοντος και την εκτέλεση της εργασίας
        int res = ToolRunner.run(new Configuration(), new DNACounter(), args);
        System.exit(res);
    }
}