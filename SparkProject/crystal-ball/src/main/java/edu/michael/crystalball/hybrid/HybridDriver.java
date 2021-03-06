package edu.michael.crystalball.hybrid;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Hello world!
 *
 */
public class HybridDriver 
{
	  public static void main(String[] args) throws Exception {
		  
	    Configuration conf = new Configuration();
	    
	    Job job = Job.getInstance(conf, "relative_frequency_hybrid");
	    
	    job.setJarByClass(HybridDriver.class);
	    job.setMapperClass(HybridMapper.class);
	    //job.setCombinerClass(StubMapper.class);
	    job.setReducerClass(HybridReducer.class);
	    
	    job.setOutputKeyClass(Pair.class);
	    job.setOutputValueClass(IntWritable.class);
	    
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
