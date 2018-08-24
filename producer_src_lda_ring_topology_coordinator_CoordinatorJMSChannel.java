package lda.ring_topology.coordinator;

import lda.paillier.Decryption;

import javax.jms.*;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by "P.Khodaparast" on 2018-08-20.
 */
public class CoordinatorJMSChannel implements MessageListener {
    private Vector publicKey;
    private Vector privateKey;
    private Decryption decryption = new Decryption();
    private BigInteger lambda;
    private BigInteger mu;

    private BigInteger n;
    private BigInteger g;


    private String strMessage;
    private String recieveStrMessage;
    private int state = 0;


    void setPublicKey(Vector publicKey) {
        this.publicKey = publicKey;
        n = (BigInteger) publicKey.get(0);
        g = (BigInteger) publicKey.get(1);

    }

    void setPrivateKey(Vector privateKey) {
        this.privateKey = privateKey;
        lambda = (BigInteger) privateKey.get(0);
        mu = (BigInteger) privateKey.get(1);
    }


    public void start() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Coordinator start block .......");
            //Prompt for JNDI names
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
            MapMessage message = session.createMapMessage();
            strMessage = n + "," + g;
            message.setString("strMessage", strMessage);
            message.setInt("state", 1);
            System.out.println("n: "+n+" g: "+g+" strMessage: "+strMessage );

            sender1.send(message);
            sender2.send(message);
            sender3.send(message);

            long time1 = System.currentTimeMillis();

            while (state == 0) {
                Thread.sleep(2000);
                System.out.println("state 000000000000000000");

            }
//            do decryption
            String[] encryptedMeans = recieveStrMessage.split(",");
            ArrayList<BigInteger> decryptedMeans=new ArrayList<>();
            for (int i=0;i<encryptedMeans.length;i++){
                BigInteger en_mean=new BigInteger(encryptedMeans[i]);
                decryptedMeans.add(decryption.decrypt(en_mean, lambda, mu, n));

            }


            for (BigInteger decryptedMean : decryptedMeans) {
                System.out.println("decryptedMean: "+decryptedMean);
            }

            while (state == 2) {
                Thread.sleep(3000);
                System.out.println("state 11111111111111111");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void onMessage(Message message) {

        try {
            MapMessage mapMessage = (MapMessage) message;
            recieveStrMessage = mapMessage.getString("strMessage");
            state = mapMessage.getInt("state");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
