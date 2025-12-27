package com.anton.numeronym;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class NumeronymReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    // IntWritable serializes the int data type, for writing and reading integer values as binary data
    // result holds the sum of all values associated with that key
    private IntWritable result = new IntWritable();
    // ελάχιστο πλήθος εμφανίσεων
    private int k;

    // overriding the lifecycle hook setup() from the parent class
    @Override
    // χρησιμοποιώ το context που μου δίνει πρόσβαση στο configuration της εργασίας
    protected void setup(Context context) {
        k = context.getConfiguration().getInt("numeronym.min.count", 0);
    }

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        // μηδένισε το counter
        int sum = 0;
        // άθροισε τις τιμές που περιέχονται στο κλειδί
        for (IntWritable val : values) {
            sum += val.get();
        }
        // για ελάχιστο πλήθος εμφανίσεων k
        if (sum >= k) {
            // σειριακοποιούμε και πάλι το άθροισμα
            result.set(sum);
            // επιστρέφει το κλειδί με το άθροισμα
            // this writes the key-value pair to the output of the Reducer, which is usually
            // stored in HDFS under /output/, in files like part-r-00000, part-r-00001, etc.
            context.write(key, result);
        }
    }
}
