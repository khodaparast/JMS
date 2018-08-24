package try1_JMS;

//import com.sun.messaging.ConnectionConfiguration;
//import com.sun.messaging.ConnectionFactory;
//import com.sun.messaging.Queue;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class TestJmsClientStandalone {
//To get it to work you will need to reference 2 openmq jars from the glassfishInstall/mq/lib directory - imq.jar and jms.jar
    public static void main( String[] args ) throws JMSException
    {
//        ConnectionFactory connFactory = new ConnectionFactory();
//        connFactory.setProperty(ConnectionConfiguration.imqAddressList, "192.168.1.1:7676");
//
//        Queue myQueue = new Queue("myRemoteQueue");
//
//        try (Connection connection = connFactory.createConnection();
//             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//             MessageProducer producer = session.createProducer(myQueue)) {
//
//            Message message = session.createTextMessage("this is my test message");
//            producer.send(message);
//        }
    }

}