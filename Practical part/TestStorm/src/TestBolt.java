import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import kafka.serializer.DefaultDecoder;


public final class TestBolt extends BaseBasicBolt {

	Map<String, Integer> counts = new HashMap<String, Integer>();

	@Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
		DefaultDecoder decoder = new DefaultDecoder(null);
		String word = "not defined";
		try
		{
			word = new String(decoder.fromBytes((byte[])tuple.getValue(0)), "UTF-8");
		}
		catch (Exception e) { }

      Integer count = counts.get(word);
      if (count == null)
        count = 0;
      count++;
      counts.put(word, count);
      collector.emit(new Values(word, count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("word", "count"));
    }

  }