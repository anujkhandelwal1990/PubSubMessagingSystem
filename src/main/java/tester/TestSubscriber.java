package tester;

import Exception.ExampleException;
import interfaces.Subscriber;
import model.Message;

/**
 * Client side test subscriber which implements Subscriber interface and get messages.
 * @author anujkhandelwal
 *
 */
public class TestSubscriber implements Subscriber {
    private String id;
    private int sleepTimeMs;

    public TestSubscriber(String id, int sleepTimeMs) {
        this.id = id;
        this.sleepTimeMs = sleepTimeMs;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void consume(Message message) throws ExampleException {
        System.out.println("Subscriber: " + id + " started consuming: " + message.getMsg());
        try {
			Thread.sleep(sleepTimeMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        System.out.println("Subscriber: " + id + " done consuming: " + message.getMsg());
    }
}