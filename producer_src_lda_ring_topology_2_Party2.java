package lda.ring_topology_2;

import javax.jms.*;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by "P.Khodaparast" on 2018-08-15.
 */
public class Party2 implements MessageListener {
    private boolean stop = false;

    public static void main(String[] args) {
        new Party2().start();

    }
    public void start() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));



        try {
            System.out.println("sr2 try block..............");
            //Prompt for JNDI names
//            System.out.println("Enter ConnectionFactory name:");
//            String factoryName = reader.readLine();
//            System.out.println("Enter Destination name:");//destination for a sender is the Q that send its messages
//            String sendToQ = reader.readLine();

            String factoryName = "java:comp/DefaultJMSConnectionFactory";
            String readFrom = "Queue02";
            String sendToQ = "Queue03";

            //Look up administered objects
            InitialContext initContext = new InitialContext();
            ConnectionFactory factory =
                    (ConnectionFactory) initContext.lookup(factoryName);
            Destination senDestination = (Destination) initContext.lookup(sendToQ);
            Destination reaDestination = (Destination) initContext.lookup(readFrom);

            initContext.close();

            //Create JMS objects
            Connection connection = factory.createConnection();
            Session session =
                    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer sender = session.createProducer(senDestination);

            MessageConsumer receiver = session.createConsumer(reaDestination);
            receiver.setMessageListener(this);
            connection.start();

            //Send messages
            String messageText = null;
            while (true) {
                System.out.println("sr2 while block.......");
                System.out.println("Enter message to send or 'quit':");
                messageText = reader.readLine();
                if ("quit".equals(messageText))
                    break;
                TextMessage message = session.createTextMessage(messageText);
                sender.send(message);
            }

            //Exit
            System.out.println("Exiting...");
            reader.close();
            connection.close();
            System.out.println("Goodbye!");

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
            String msgText = ((TextMessage) message).getText();
            System.out.println(msgText);
            if ("stop".equals(msgText))
                stop = true;
        } catch (JMSException e) {
            e.printStackTrace();
            stop = true;
        }
    }
}
