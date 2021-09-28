
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.CompletionListener;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import  com.ibm.msg.client.jms.JmsFactoryFactory;

import com.ibm.msg.client.wmq.WMQConstants;
import com.ibm.msg.client.jms.JmsConnectionFactory;

class Producer {

    private static final String   HOST_NAME           = "secureqm-ibm-mq-qm-mq.patrocinio-684-dallas-ddd93d3a0fef01f6b396b69d343df410-0000.us-south.containers.appdomain.cloud";
    private static final int      HOST_PORT           = 443;
    private static final String   CHANNEL_NAME        = "SECUREQMCHL";
    private static final String   QUEUE_MANAGER_NAME  = "SECUREQM";
    private static final String   QUEUE_NAME          = "EXAMPLE.QUEUE";

    private static final String TLS_KEYSTORE_PATH = "/Users/edu/github/jmeter-ibmmq/tests/ibmmq/clientkey.jks";
    private static final String TLS_KEYSTORE_PWD  = "password";

    private static final String  CIPHER_SPEC 		= "ECDHE_RSA_AES_128_CBC_SHA256";

    private Connection          conn;
    private Destination         destination;
    private Session             sess;
    private CompletionListener  listener;
    private MessageProducer     producer;

    private void setUp () {
        try {
            System.out.println ("Creating FactoryFactory...");
            final JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);

            System.out.println ("Creating ConnectionFactory...");
            final JmsConnectionFactory cf = ff.createConnectionFactory();

            System.out.println ("Defining ConnectionFactory properties...");
            cf.setStringProperty	(WMQConstants.WMQ_HOST_NAME, 		HOST_NAME);
            cf.setIntProperty		(WMQConstants.WMQ_PORT, 			HOST_PORT);
            cf.setStringProperty	(WMQConstants.WMQ_CHANNEL, 			CHANNEL_NAME);
            cf.setIntProperty		(WMQConstants.WMQ_CONNECTION_MODE, 	WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty	(WMQConstants.WMQ_QUEUE_MANAGER, 	QUEUE_MANAGER_NAME);
            cf.setStringProperty    (WMQConstants.WMQ_SSL_CIPHER_SPEC,  CIPHER_SPEC);


            System.out.println ("Defining System properties");
            System.setProperty("com.ibm.mq.cfg.preferTLS", 			    "true");
            System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", 	"false");
            System.setProperty("javax.net.ssl.keyStore", 			    TLS_KEYSTORE_PATH);
            System.setProperty("javax.net.ssl.keyStorePassword", 		TLS_KEYSTORE_PWD);
            System.setProperty("javax.net.ssl.trustStore", 			    TLS_KEYSTORE_PATH);
            System.setProperty("javax.net.ssl.trustStorePassword", 	    TLS_KEYSTORE_PWD);

            System.out.println ("Creating connection");
            conn = cf.createConnection("app", "test");

            System.out.println ("Creating session");
            sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            System.out.println ("Defining destination");
            destination = sess.createQueue(QUEUE_NAME);

            System.out.println ("Starting connection");
            conn.start();

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    private void createListener() {
        System.out.println ("Creating listener");
        listener = new CompletionListener() {
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

    private void createProducer() {
        System.out.println ("Creating producer");
        try {
            producer = sess.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        final String payload = "My message";
        try {
            TextMessage msg = sess.createTextMessage(payload);

            System.out.println ("Sending message");
            producer.send(msg, listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void run () {
        System.out.println ("Running producer...");

        setUp();
        createListener();
        createProducer();
        sendMessage();
    }

    public static void main(String[] args) {
        new Producer().run();
    }
}