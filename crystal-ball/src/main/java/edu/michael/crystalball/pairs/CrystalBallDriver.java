package edu.michael.crystalball.pairs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Hello world!
 *
 */
public class CrystalBallDriver 
{
	  public static void main(String[] args) throws Exception {
		  
	    Configuration conf = new Configuration();
	    
	    Job job = Job.getInstance(conf, "relative_frequency_pairs");
	    
	    job.setJarByClass(CrystalBallDriver.class);
	    job.setMapperClass(CrystalBallMapper.class);
	    //job.setCombinerClass(StubMapper.class);
	    job.setReducerClass(CrystalBallReducer.class);
	    
	    job.setOutputKeyClass(Pair.class);
	    job.setOutputValueClass(IntWritable.class);
	    
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
