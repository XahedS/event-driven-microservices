package com.microservices.demo.kafka.admin.client;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.config.RetryConfigData;
import com.microservices.demo.kafka.admin.exception.KafkaClientException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.boot.logging.LogLevel;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Component
public class KafkaAdminClient {
//    private static final Logger LOG = LoggerFactory.getLogger(k);
    private final KafkaConfigData kafkaConfigData;
    private final RetryConfigData retryConfigData;
    private final AdminClient adminClient;
    private final RetryTemplate retryTemplate;

    public KafkaAdminClient(KafkaConfigData kafkaConfigData, RetryConfigData retryConfigData,
                            AdminClient adminClient, RetryTemplate retryTemplate) {
        this.kafkaConfigData = kafkaConfigData;
        this.retryConfigData = retryConfigData;
        this.adminClient = adminClient;
        this.retryTemplate = retryTemplate;
    }

    public void createTopics(){
        CreateTopicsResult createTopicsResult;
        try {
            createTopicsResult = retryTemplate.execute(this::doCreateTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for creating kafka topic(s)", t);
        }
        checkTopicsCreated();//placed here so that we can double check topics are created
    }

    private CreateTopicsResult doCreateTopics(RetryContext retryContext) {
        List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
//        LOG.INFO("Creating {} topic(s), attempt {}", topicNames.size(), retryContext.getRetryCount());
        List<NewTopic> kafkaTopics = topicNames.stream().map(topic -> new NewTopic(
                topic.trim(),
                kafkaConfigData.getNumOfPartitions(),
                kafkaConfigData.getReplicationFactor()
        )).collect(Collectors.toList());
        return adminClient.createTopics(kafkaTopics);
    }

    private Collection<TopicListing> getTopics(){
        Collection<TopicListing> topics;
        try {
            topics = retryTemplate.execute(this::doGetTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of Retry for reading kafka topics", t);
        }
        return topics;
    }

    private Collection<TopicListing> doGetTopics(RetryContext retryContext) {
//        LOG.INFO("Reading kafka topic {}, attempt {}",
//                kafkaConfigData.getTopicNamesToCreate().toArray(), retryContext.getRetryCount());

        Collection<TopicListing> topics = null;
        try {
            topics = adminClient.listTopics().listings().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if(topics != null){
//          topics.forEach(topic -> LOG.debug("Topic with name {}", topic.name()));
      }
      return topics;
    }

    private void checkSchemaRegistry(){
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        Integer multiplier = retryConfigData.getMultiplier();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        while (true){
            checkMaxRetry(retryCount++, maxRetry);
            sleep(sleepTimeMs);
            sleepTimeMs *= multiplier;

        }
    }
    public void checkTopicsCreated(){
        Collection<TopicListing> topics = getTopics();
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        Integer multiplier = retryConfigData.getMultiplier();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();

        for(String topic: kafkaConfigData.getTopicNamesToCreate()){
            while (!isTopicCreated(topics, topic)){
                checkMaxRetry(retryCount++, maxRetry);
                sleep(sleepTimeMs);
                sleepTimeMs *= multiplier;
                topics = getTopics();
            }
        }
    }

    private void sleep(Long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Error while sleeping for waiting new created topics");
        }
    }

    private void checkMaxRetry(int rety, Integer maxRetry) {
        if(rety > maxRetry){
            throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)");
        }
    }

    private boolean isTopicCreated(Collection<TopicListing> topics, String topicName) {
        if(topics == null){
            return false;
        }
        return topics.stream().anyMatch(topic -> topic.name().equals(topicName));
    }
}
