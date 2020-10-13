package hoau.com.cn.demo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;

public class JobOutputRenameApp extends Configured implements Tool {


    static class JobOutputRenameRecordWriter extends RecordWriter<Text, LongWritable> {

        FSDataOutputStream out;

        public JobOutputRenameRecordWriter(){

        }

        public JobOutputRenameRecordWriter(FSDataOutputStream out){
            this.out = out;
        }

        @Override
        public void write(Text key, LongWritable value) throws IOException,
                InterruptedException {
            out.writeUTF(key.toString());
            out.writeUTF("\t");
            out.writeLong(value.get());
            out.writeUTF("\n");
        }

        @Override
        public void close(TaskAttemptContext context) throws IOException,
                InterruptedException {
            out.close();
        }

    }

    static class JobOutputRenameOutputFormat extends OutputFormat<Text, LongWritable> {
        @Override
        public RecordWriter<Text, LongWritable> getRecordWriter(
                TaskAttemptContext context) throws IOException,
                InterruptedException {
            final String output_dir = context.getConfiguration().get("output_dir");
            final String output_name = context.getConfiguration().get("output_name");
            FSDataOutputStream out = null;
            try {
                FileSystem fs = FileSystem.get(new URI(output_dir), context.getConfiguration(), "root");
                out = fs.create(new Path(output_dir+output_name), true);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return new JobOutputRenameRecordWriter(out);
        }

        @Override
        public void checkOutputSpecs(JobContext context) throws IOException,
                InterruptedException {
        }

        @Override
        public OutputCommitter getOutputCommitter(TaskAttemptContext context)
                throws IOException, InterruptedException {
            final String output_dir = context.getConfiguration().get("output_dir");
            return new FileOutputCommitter(new Path(output_dir), context);
        }

    }

    static class JobOutputRenameMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        private Text k = null;
        private LongWritable v = null;

        @Override
        protected void setup(
                Mapper<LongWritable, Text, Text, LongWritable>.Context context)
                throws IOException, InterruptedException {
            k = new Text();
            v = new LongWritable(1L);
        }

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] words = value.toString().split("\t");
            for (String word : words) {
                k.set(word);
                context.write(k, v);
            }
        }

    }

    static class JobOutputRenamePartition extends Partitioner<Text, LongWritable> {

        @Override
        public int getPartition(Text key, LongWritable value, int numPartitions) {
            if (!key.toString().equals("hello")) {
                return 1;
            }
            return 0;
        }

    }

    static class JobOutputRenameReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> value,
                              Reducer<Text, LongWritable, Text, LongWritable>.Context context)
                throws IOException, InterruptedException {
            long count = 0L;
            for (LongWritable item : value) {
                count += item.get();
            }
            context.write(key, new LongWritable(count));
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        conf.set("output_dir", args[1]);
        conf.set("output_name", args[2]);
        Job job = Job.getInstance(conf, JobOutputRenameApp.class.getSimpleName());
        job.setJarByClass(JobOutputRenameApp.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        job.setInputFormatClass(TextInputFormat.class);
        job.setMapperClass(JobOutputRenameMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

//        job.setPartitionerClass(JobOutputRenamePartition.class);
//        job.setNumReduceTasks(2);

        job.setReducerClass(JobOutputRenameReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        job.setOutputFormatClass(JobOutputRenameOutputFormat.class);

        return job.waitForCompletion(true)?0:1;
    }

    public static int createJob(String[] params){
        Configuration conf = new Configuration();
        int status = 1;
        try {
            status = ToolRunner.run(conf, new JobOutputRenameApp(), params);
        } catch (Exception e) {
            e.printStackTrace();
            new RuntimeException(e);
        }

        return status;
    }

    public static void main(String[] args) throws Exception {
        args = new String[3];
        args[0] = "/testdata/words";
        args[1] = "/job/mapreduce/"+JobOutputRenameApp.class.getSimpleName()+"_"+new SimpleDateFormat("yyyyMMddhhMMss").format(new Date())+"/";
        args[2] = "wordcount.txt";

//        String[] otherArgs = new GenericOptionsParser(args).getRemainingArgs();
        if (args != null && args.length !=3) {
            System.out.println("Usage: "+JobOutputRenameApp.class.getSimpleName()+" <input dir> <output dir> <output name> ");
            System.exit(3);
        } else {
            int status = createJob(args);
            System.exit(status);
        }
    }
}