package com.xcar360.kafka.config;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {



    //从配置文件中引入Kafka生产这的相关配置的值
    //kakfa服务端地址
    @Value("${kafka.producer.servers}")
    private String servers;
    //消息发送失败重试次数
    @Value("${kafka.producer.retries}")
    private int retries;
    //消息批量发送容量
    @Value("${kafka.producer.batch.size}")
    private int batchSize;
    @Value("${kafka.producer.linger}")
    private int linger;
    //缓存容量
    @Value("${kafka.producer.buffer.memory}")
    private int bufferMemory;


    /**
     * 生产者相关配置
     * @return
     */
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<String, Object>();
        System.out.println("-----------------servers---------");
        System.out.println(servers);
        //连接地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        //重试，0为不启用重试机制
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        //控制批处理大小，单位为字节
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        //批量发送，延迟为1毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        //生产者可以使用的总内存字节来缓冲等待发送到服务器的记录
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        //设置自定义的分区数
        props.put("partitioner.class","com.xcar360.kafka.config.CidPartitioner");

        //键的序列化方式
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //值的序列化方式
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }


    /**
     * 生产者创建工厂
     * @return
     */
    public ProducerFactory<String, String> producerFactory() {
//        DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory<>(producerConfigs());
//        factory.transactionCapable();
//        factory.setTransactionIdPrefix("tran-");
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }



    /**
     * kafkaTemplate 覆盖默认配置类中的kafkaTemplate
     * @return
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactory());
    }


    /*
    *
    * 使用KafkaTemplate.executeInTransaction开启事务
      这种方式开启事务是不需要配置事务管理器的，也可以称为本地事务。直接编写测试方法
    * */

//    @Test
//    public void testExecuteInTransaction() throws InterruptedException {
//        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
//            @Override
//            public Object doInOperations(KafkaOperations kafkaOperations) {
//                kafkaOperations.send("topic.quick.tran", "test executeInTransaction");
//                throw new RuntimeException("fail");
//                //return true;
//            }
//        });
//    }




}
