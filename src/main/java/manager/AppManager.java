package manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import handlers.TopicHandler;
import interfaces.Subscriber;
import model.Message;
import model.Topic;
import model.TopicSubscriber;

/**
 * Manager Class which takes care of various operations needs to be performed.
 * Eg. create topic, subscribers, send messages etc.
 * 
 * @author anujkhandelwal
 *
 */
public class AppManager {
    private final Map<String, TopicHandler> topicMap;

    public AppManager() {
        this.topicMap = new HashMap<>();
    }

    public Topic createTopic(final String topicName) {
        final Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        TopicHandler topicHandler = new TopicHandler(topic);
        topicMap.put(topic.getTopicId(), topicHandler);
        System.out.println("Created a topic: " + topic.getTopicName());
        return topic;
    }

    public void subscribe(Subscriber subscriber, Topic topic) {
        topic.addSubscriber(new TopicSubscriber(subscriber));
        System.out.println(subscriber.getId() + " is subscribed to topic : " + topic.getTopicName());
    }

    public void unSubscribe(Subscriber subscriber, Topic topic) {
        topic.removeSubscriber(subscriber);
        TopicHandler handler = topicMap.get(topic.getTopicId());
        handler.stopSubsriberWorker(subscriber.getId());
        System.out.println(subscriber.getId() + " is unsubscribed to topic : " + topic.getTopicName());
    }

    public void publish( final Topic topic, final Message message) {
        topic.addMessage(message);
        System.out.println(message.getMsg() + " is published to the topic : " + topic.getTopicName());
        TopicHandler handler = topicMap.get(topic.getTopicId());
        handler.publish();
    }

    public void resetOffset(final Topic topic,  final Subscriber subscriber,  final Integer newOffset) {
        for (TopicSubscriber topicSubscriber : topic.getSubscribers()) {
            if (topicSubscriber.getSubscriber().equals(subscriber)) {
                System.out.println(topicSubscriber.getSubscriber().getId() + " offset is reset from: " + topicSubscriber.getOffset());
                topicSubscriber.getOffset().set(newOffset);
                System.out.println(topicSubscriber.getSubscriber().getId() + " offset is reset to: " + newOffset);
                new Thread(() -> topicMap.get(topic.getTopicId()).startSubsriberWorker(topicSubscriber)).start();
                break;
            }
        }
    }    
}
