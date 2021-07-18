package tester;

import org.json.JSONObject;

import manager.AppManager;
import model.Message;
import model.Topic;

/**
 * 
 * @author anujkhandelwal
 *
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        final AppManager manager = new AppManager();

        // Create the reuiqred topics.
        Topic topic1 = manager.createTopic("t1");
        Topic topic2 = manager.createTopic("t2");
        
        // Create the required subscribers
        TestSubscriber sub1 = new TestSubscriber("sub1", 5000);
        TestSubscriber sub2 = new TestSubscriber("sub2", 3000);
        TestSubscriber sub3 = new TestSubscriber("sub3", 5000);

        // Subscribe the created subscribers to the topics.
        manager.subscribe(sub1, topic1);
        manager.subscribe(sub2, topic1);
        manager.subscribe(sub3, topic2);

//        manager.unSubscribe(sub1, topic1);

        // Start message publish. 
        manager.publish(topic1, new Message(JSONMessageCreator.generateMesage("key1", "m1")));
        manager.publish(topic1, new Message(JSONMessageCreator.generateMesage("key2", "m2")));
        manager.publish(topic1, new Message(JSONMessageCreator.generateMesage("key3", "m3")));


    }
}