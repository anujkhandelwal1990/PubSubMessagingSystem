package model;

import java.util.concurrent.atomic.AtomicInteger;

import interfaces.Subscriber;

/**
 * 
 * @author anujkhandelwal
 *
 */
public class TopicSubscriber {
	private final AtomicInteger offset;
    private final Subscriber subscriber;

    public TopicSubscriber(final Subscriber subscriber) {
        this.subscriber = subscriber;
        this.offset = new AtomicInteger(0);
    }
    
    public AtomicInteger getOffset() {
		return offset;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}
}
