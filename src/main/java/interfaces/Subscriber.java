package interfaces;

import Exception.ExampleException;
import model.Message;

/**
 * Client side subscriber interface
 * 
 * @author anujkhandelwal
 *
 */
public interface Subscriber {

    String getId();
    void consume(Message message) throws ExampleException;
}