import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CountMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text tuple = new Text();

    @Override
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        // Ignore empty lines
        if (line.length() == 0) return;

        // Generate and emit 2-tuples, 3-tuples, 4-tuples
        for (int n = 2; n <= 4; n++) {
            for (int i = 0; i <= line.length() - n; i++) {
                String sub = line.substring(i, i + n);
                tuple.set(sub);
                context.write(tuple, one);
            }
        }
    }
}
