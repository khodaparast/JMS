package try1_JMS;

import java.io.*;
import javax.jms.*;
import javax.naming.*;

public class Sender {

    public static void main(String[] args) {

        new Sender().send();
    }

    public void send() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));



        try {
            //Prompt for JNDI names
            System.out.println("Enter ConnectionFactory name:");
            String factoryName = reader.readLine();
            System.out.println("Enter Destination name:");//destination for a sender is the Q that send its messages
            String destinationName = reader.readLine();

            //Look up administered objects
            InitialContext initContext = new InitialContext();
            ConnectionFactory factory =
                    (ConnectionFactory) initContext.lookup(factoryName);
            Destination destination = (Destination) initContext.lookup(destinationName);
            initContext.close();

            //Create JMS objects
            Connection connection = factory.createConnection();
            Session session =
                    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer sender = session.createProducer(destination);

            //Send messages
            String messageText = null;
            while (true) {
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

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}