package model;

/**
 * 
 * @author anujkhandelwal
 *
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import interfaces.Subscriber;

public class Topic {
    private String topicId;
    private String topicName;
    private List<Message> messages;
    private List<TopicSubscriber> subscribers;

    public Topic(final String topicName, final String topicId) {
        this.topicName = topicName;
        this.topicId = topicId;
        this.messages = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    /*
     * Add a message to a topic.
     */
	public synchronized void addMessage(final Message message) {
        messages.add(message);
    }


    public List<Message> getMessages() {
		return messages;
	}
	public List<TopicSubscriber> getSubscribers() {
		return subscribers;
	}
    public void addSubscriber(final TopicSubscriber subscriber) {
        subscribers.add(subscriber);
    }
    public void removeSubscriber(final Subscriber subscriber) {
    	if (subscribers != null) {
        	Iterator<TopicSubscriber> iterator = subscribers.iterator();
        	while (iterator.hasNext()) {
        		TopicSubscriber topicSubscriber = iterator.next();
        	    if (topicSubscriber.getSubscriber().getId() == subscriber.getId()) {
        	        iterator.remove();
        	    }
        	}    		
    	}
    }

    public String getTopicName() {
		return topicName;
	}
	public String getTopicId() {
		return topicId;
	}
}