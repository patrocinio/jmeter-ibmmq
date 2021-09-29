package org.patrocinio.ibmmq;

import javax.jms.CompletionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.MessageProducer;

public class MQProducer {
    private final MessageProducer     sender;
    private final CompletionListener  listener;


    private CompletionListener createListener() {
        System.out.println ("Creating listener");
        return new CompletionListener() {
            @Override
            public void onCompletion(Message msg) {
                try {
                    System.out.println(msg.getBody(String.class));
                } catch (JMSException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onException(Message msg, Exception e) {
                try {
                    System.out.println(msg.getBody(String.class));
                } catch (JMSException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public MQProducer (MessageProducer sender) {
        this.sender = sender;
        System.out.println ("Sender: " + sender.getClass());
        this.listener = createListener();
    }

    public void send (TextMessage msg) {
        try {
            sender.send(msg, listener);
        } catch (IllegalArgumentException | UnsupportedOperationException | JMSException e) {
            e.printStackTrace();
        }
    }

    public void close () throws JMSException {
        sender.close();
    }
}
