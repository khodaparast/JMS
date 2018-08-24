package lda.ring_topology.p3;

import lda.classification.LDAUtils;
import lda.paillier.Encryption;
import lda.ring_topology.p2.ComputationP2;

import javax.jms.*;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by "P.Khodaparast" on 2018-08-15.
 */
public class Party3 implements MessageListener {
    private int state = 0; // state=1 --> pk, state=2 --> mean of attrib,
    private String strMessage;
    private String recieveStrMessage;

    int flag = 0;

    BigInteger n;
    BigInteger g;
    BigInteger r;


    Encryption encryption = new Encryption();
    LDAUtils ldaUtils = new LDAUtils();


    public static void main(String[] args) {
        new Party3().start();

    }

    public void start() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        try {
            System.out.println("sr1 try block..............");
            //Prompt for JNDI names
            String factoryName = "java:comp/DefaultJMSConnectionFactory";
            String readFrom = "Queue03";
            String sendToQ = "Queue04";

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
            MapMessage message = session.createMapMessage();

            while (state == 0) {
                Thread.sleep(1000);
                System.out.println("state in party3 == 0");
            }


// calculate mean's of all attributes, encrypt and send to next party
            String[] pkMessage = recieveStrMessage.split(",");

            n = new BigInteger(pkMessage[0]);
            g = new BigInteger(pkMessage[1]);
            r = randomZStarN();
            ArrayList<BigInteger> means = new ComputationP3().getAttributesMean();
            BigInteger mu_Attribute1 = means.get(0);
            BigInteger mu_Attribute2 = means.get(1);
            BigInteger en_mu_Attribute1 = encryption.encrypt(mu_Attribute1, n, g, r);
            BigInteger en_mu_Attribute2 = encryption.encrypt(mu_Attribute2, n, g, r);

            System.out.println("n: "+n+"   g: "+g+"   mu_Attribute1: " + mu_Attribute1 + "   mu_Attribute2: " + mu_Attribute2);
            System.out.println("en_mu_Attribute1: " + en_mu_Attribute1 + " en_mu_Attribute2: " + en_mu_Attribute2);

            while (state == 1) {
                Thread.sleep(1000);
                System.out.println("state in party 3 == 0");
            }
            String[] attributeMeanMessage = recieveStrMessage.split(",");// BigInteger sumOfMeanAttrib1 = new BigInteger(en_meanAttribute1);

            BigInteger cipher1 = new BigInteger(attributeMeanMessage[0]);
            BigInteger cipher2 = new BigInteger(attributeMeanMessage[1]);
            System.out.println("cipher1: "+cipher1+" cipher2: "+cipher2);


            BigInteger cipherAttrib1 = cipher1.multiply(en_mu_Attribute1);
            BigInteger cipherAttrib2 = cipher2.multiply(en_mu_Attribute2);
            System.out.println("cipherAttrib1: "+cipherAttrib1+"  cipherAttrib2: "+cipherAttrib2);


            strMessage = cipherAttrib1 + "," + cipherAttrib2;
            message.setInt("state", 2);
            message.setString("strMessage", strMessage);
            sender.send(message);
            System.out.println("strMessage: " + strMessage);

            while (flag == 0) {
                Thread.sleep(3000);
                System.out.println("flag==0 in party 3");
            }
// ----------------------------------------------------
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


    BigInteger randomZStarN() {
        System.out.println("randomZStarN()");
        BigInteger r;

        do {
            r = new BigInteger(20, new Random());
        }
        while (r.compareTo(n) >= 0 || r.gcd(n).intValue() != 1);

        return r;
    }
}
