package com.ty.study.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaConsumerSimple implements Runnable {
    public String title;
    public KafkaStream<byte[], byte[]> stream;
    public KafkaConsumerSimple(String title, KafkaStream<byte[], byte[]> stream) {
        this.title = title;
        this.stream = stream;
    }
    public void run() {
        System.out.println("开始运行 " + title);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        /**
         * 不停地从stream读取新到来的消息，在等待新的消息时，hasNext()会阻塞
         * 如果调用 `ConsumerConnector#shutdown`，那么`hasNext`会返回false
         * */
        while (it.hasNext()) {
            MessageAndMetadata<byte[], byte[]> data = it.next();
            String topic = data.topic();
            int partition = data.partition();
            long offset = data.offset();
            String msg = new String(data.message());
            System.out.println(String.format(
                    "Consumer: [%s],  Topic: [%s],  PartitionId: [%d], Offset: [%d], msg: [%s]",
                    title, topic, partition, offset, msg));
        }
        System.err.println(String.format("Consumer: [%s] exiting ...", title));
    }

    public static void main(String[] args) throws Exception{
        // main方法
        Properties props = new Properties();
        props.put("group.id", "testGroup");
        props.put("zookeeper.connect", "zk01:2181,zk02:2181,zk03:2181");
        props.put("auto.offset.reset", "largest");
        props.put("auto.commit.interval.ms", "1000");
        props.put("partition.assignment.strategy", "roundrobin");
        ConsumerConfig config = new ConsumerConfig(props);
        String topic = "shuiguoshouji";
        //只要ConsumerConnector还在的话，consumer会一直等待新消息，不会自己退出
        ConsumerConnector consumerConn = Consumer.createJavaConsumerConnector(config);
        //创建topicCountMap
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic,4);

        Map<String, List<KafkaStream<byte[], byte[]>>> topicStreamsMap = consumerConn.createMessageStreams(topicCountMap);
        //取出 `kafkaTest` 对应的 streams
        List<KafkaStream<byte[], byte[]>> streams = topicStreamsMap.get(topic);
        //创建一个容量为20的线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);
        //创建20个consumer threads
        for (int i = 0; i < streams.size(); i++)
            executor.execute(new KafkaConsumerSimple("消费者" + (i + 1), streams.get(i)));
    }
}