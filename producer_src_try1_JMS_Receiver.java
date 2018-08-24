package try1_JMS;

import javax.jms.*;
import javax.naming.*;

public class Receiver implements MessageListener {

    private boolean stop = false;

    public static void main(String[] args) {

        new Receiver().receive();
    }

    public void receive() {

//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
//            Prompt for JNDI names
//            System.out.println("Enter ConnectionFactory name:");
//            String factoryName = reader.readLine();
//            System.out.println("Enter Destination name:");
//            String readFrom = reader.readLine();
//            reader.close();

            String factoryName = "java:comp/DefaultJMSConnectionFactory";
            String readFrom = "Queue02";

            //Look up administered objects
            InitialContext initContext = new InitialContext();
            ConnectionFactory factory =
                    (ConnectionFactory) initContext.lookup(factoryName);
            Destination reaDestination = (Destination) initContext.lookup(readFrom);
            initContext.close();

            //Create JMS objects
            Connection connection = factory.createConnection();
            Session session =
                    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer receiver = session.createConsumer(reaDestination);
            receiver.setMessageListener(this);
            connection.start();

            //Wait for stop
            while (!stop) {
                Thread.sleep(1000);
            }

            //Exit
            System.out.println("Exiting...");
            connection.close();
            System.out.println("Goodbye!");

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