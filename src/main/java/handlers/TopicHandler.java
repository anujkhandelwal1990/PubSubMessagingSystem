package handlers;


import java.util.HashMap;
import java.util.Map;

import model.Topic;
import model.TopicSubscriber;

/**
 * Handler Class for topic. Handler takes care of fan out process where the messages will be send to the subscribers.  
 * 
 * @author anujkhandelwal
 *
 */
public class TopicHandler {
	
    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberWorkers;

    public TopicHandler(final Topic topic) {
        this.topic = topic;
        subscriberWorkers = new HashMap<>();
    }

    public void publish() {
        for (TopicSubscriber topicSubscriber : topic.getSubscribers()) {
            startSubsriberWorker(topicSubscriber);
        }
    }

    public void startSubsriberWorker(final TopicSubscriber topicSubscriber) {
        final String subscriberId = topicSubscriber.getSubscriber().getId();
        if (!subscriberWorkers.containsKey(subscriberId)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, topicSubscriber);
            subscriberWorkers.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        }
        final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
        subscriberWorker.wakeUpSubscriber();
    }

    public void stopSubsriberWorker(final String subscriberId) {
        if (!subscriberWorkers.containsKey(subscriberId)) {
        	System.out.println("Subscriber doesn't have any worker. It's already unsubscribed");
            return;
        }
        final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
        subscriberWorker.stop();
    }
}