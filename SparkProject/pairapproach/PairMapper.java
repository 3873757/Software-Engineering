package com.anilkc.crystalball.pairapproach;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anilkc.crystalball.CommonConstants;
import com.anilkc.crystalball.Pair;

/**
 * Pair Approach Mapper class
 * 
 * @author An
 *
 */
public class PairMapper extends Mapper<LongWritable, Text, Pair, IntWritable> {

	private static final Logger LOG = LoggerFactory.getLogger(PairMapper.class);

	private Map<Pair, Integer> map;

	/**
	 * Called once at the beginning of the task.
	 */
	protected void setup(Context context) throws IOException, InterruptedException {
		map = new HashMap<Pair, Integer>();
	}

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String[] datas = value.toString().split(CommonConstants.SPACE_REGEX);

		LOG.info("Received line: " + value.toString());
		LOG.info("Processing....");

		for (int i = 0; i < datas.length - 1; i++) {

			String data = datas[i];

			for (int j = i + 1; j < datas.length; j++) {

				if (data.equals(datas[j])) {
					break;
				}

				Pair pair = new Pair(data, datas[j]);
				LOG.info(pair.toString());

				if (!map.containsKey(pair)) {
					map.put(pair, 1);
				} else {
					map.put(pair, map.get(pair) + 1);
				}

				Pair specialPair = new Pair(data, CommonConstants.SPECIAL_CHARACTER);
				LOG.info(specialPair.toString());

				if (!map.containsKey(specialPair)) {
					map.put(specialPair, 1);
				} else {
					map.put(specialPair, map.get(specialPair) + 1);
				}
			}
		}
	}

	/**
	 * Called once at the end of the task.
	 */
	protected void cleanup(Context context) throws IOException, InterruptedException {

		LOG.info("Mapper output: ");

		for (Pair key : map.keySet()) {
			LOG.info(key + " count " + map.get(key));
			context.write(key, new IntWritable(map.get(key)));
		}
	}
}
