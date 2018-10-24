package com.xcar360.kafka.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 */

public class CidPartitioner implements Partitioner {

    private static Logger logger = LoggerFactory.getLogger(CidPartitioner.class);

//    public CidPartitioner() {
//        //注意 ： 构造函数的函数体没有东西，但是不能没有构造函数
//    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] bytes1, Cluster cluster) {

        logger.info("设置分区，topic：{}，key：{}，cluster：{},keyBytes：{}",topic,key,cluster,keyBytes);

        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        /**
         *由于我们按key分区，在这里我们规定：key值不允许为null。在实际项目中，key为null的消息*，可以发送到同一个分区。
         */
        if(keyBytes == null) {
            throw new InvalidRecordException("key cannot be null");
        }
        if(((String)key).equals("1")) {
            return 1;
        }
        //如果消息的key值不为1，那么使用hash值取模，确定分区。
        int partition =  Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;

        logger.info("设置分区成功：{},分区总数为：{}",partition,numPartitions);

        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
