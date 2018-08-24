package lda.ring_topology_2;

import javax.jms.*;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by "P.Khodaparast" on 2018-08-15.
 */
public class MessageCoordinator implements MessageListener {
    private boolean stop = false;

    public static void main(String[] args) {
        new MessageCoordinator().start();

    }
    public void start() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));



        try {
            System.out.println("key gen try block......");

            String factoryName = "java:comp/DefaultJMSConnectionFactory";
            String sendToQ1 = "Queue01";
            String sendToQ2 = "Queue02";
            String sendToQ3 = "Queue03";
            String readFrom = "Queue04";




            //Look up administered objects
            InitialContext initContext = new InitialContext();
            ConnectionFactory factory =
                    (ConnectionFactory) initContext.lookup(factoryName);
            Destination senDestination1 = (Destination) initContext.lookup(sendToQ1);
            Destination senDestination2 = (Destination) initContext.lookup(sendToQ2);
            Destination senDestination3 = (Destination) initContext.lookup(sendToQ3);
            Destination reaDestination = (Destination) initContext.lookup(readFrom);

            initContext.close();

            //Create JMS objects
            Connection connection = factory.createConnection();
            Session session =
                    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer sender1 = session.createProducer(senDestination1);
            MessageProducer sender2 = session.createProducer(senDestination2);
            MessageProducer sender3 = session.createProducer(senDestination3);

            MessageConsumer receiver = session.createConsumer(reaDestination);
            receiver.setMessageListener(this);
            connection.start();


            //Send messages
            String messageText = null;
//            MapMessage message=session.createMapMessage();
//            message.setString("PK","");

            while (true) {
                System.out.println("key gen while block..........");
                System.out.println("Enter message to send or 'quit':");
                messageText = reader.readLine();
                if ("quit".equals(messageText))
                    break;
                TextMessage message = session.createTextMessage(messageText);

                sender1.send(message);
                sender2.send(message);
                sender3.send(message);
            }

            //Exit
//            System.out.println("Exiting...");
//            reader.close();
//            connection.close();
//            System.out.println("Goodbye!");

// ----------------------------------------------------


            //Wait for stop
//            while (!stop) {
//                Thread.sleep(1000);
//            }
//
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void onMessage(Message message) {

        try {
            TextMessage msgText = (TextMessage) message;

            System.out.println(msgText);
            if ("stop".equals(msgText))
                stop = true;
        } catch (Exception e) {
            e.printStackTrace();
            stop = true;
        }
    }
}
