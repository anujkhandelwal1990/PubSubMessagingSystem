package handlers;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import model.Message;
import model.Topic;
import model.TopicSubscriber;

/**
 * Subscriber Worker Class where you actually fan out the messages and send it to the interface which can be consumed by the 
 * client subscriber application. 
 * 
 * @author anujkhandelwal
 *
 */
public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final TopicSubscriber topicSubscriber;
    private AtomicBoolean start = new AtomicBoolean(false);
    private final int MAX_RETRIES = 10;

    public SubscriberWorker(final Topic topic, final TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
        start.set(true);
    }

	@Override
    public void run(){
        synchronized (topicSubscriber) {
            int noOfRetries = 0;
        	while(start.get()) {    		
            	try {
                    int curOffset = topicSubscriber.getOffset().get();

                    // If consumer's offset is already higher which means all messages have been consumed -> we can
                    // put this thread is wait state so that it doesn't run unnecessarly.
                    while (curOffset >= topic.getMessages().size()) {
                        topicSubscriber.wait(5000);
                    }

                    Message message = topic.getMessages().get(curOffset);

                    // This is where we are sending message to the client interface.
                    try {
                        topicSubscriber.getSubscriber().consume(message);                    	
                        topicSubscriber.getOffset().compareAndSet(curOffset, curOffset + 1);            		
                    } catch (Exception e) {
                    	// In case of exception, retry the same message again. We can have exponential backoffs and better 
                    	// retry handling but for now we are retrying max no of time before moving on to the next msg. 
                    	System.out.println("Caught Exception from listener side to retrying again..");

                    	noOfRetries++;
                    	Thread.sleep(1000);
                    	if (noOfRetries >= MAX_RETRIES) {
                            topicSubscriber.getOffset().compareAndSet(curOffset, curOffset + 1);     
                            noOfRetries = 0;
                    	}
					}

            	} catch (InterruptedException e) {
            		System.out.println("Error while consuming message : " + e.getMessage());
				}
            }
        }
    }

	synchronized public void wakeUpSubscriber() {
		synchronized (topicSubscriber) {	  
            topicSubscriber.notify();
        }
    }

    synchronized public void stop() {
        start.set(false);
    }

    public Topic getTopic() {
		return topic;
	}

	public TopicSubscriber getTopicSubscriber() {
		return topicSubscriber;
	}
}