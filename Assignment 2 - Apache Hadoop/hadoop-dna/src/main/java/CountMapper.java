import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CountMapper extends Mapper<Object, Text, Text, IntWritable> {
    // σειρακοποίηση του '1'
    private final static IntWritable one = new IntWritable(1);
    private Text tuple = new Text();

    @Override
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        // αν η γραμμή είναι κενή προχώρα παρακάτω
        if (line.length() == 0) return;

        // δημιουργία των tuples με for loop
        for (int n = 2; n <= 4; n++) {
            for (int i = 0; i <= line.length() - n; i++) {
                String sub = line.substring(i, i + n);
                tuple.set(sub);
                context.write(tuple, one);
            }
        }
    }
}
