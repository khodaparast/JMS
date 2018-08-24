package lda.ring_topology.coordinator;

import lda.paillier.KeyGen;

import java.util.Vector;

/**
 * Created by "P.Khodaparast" on 2018-08-20.
 */
public class Coordinator {

    public static void main(String[] args) {
        KeyGen keyGen = new KeyGen();
        Vector publicKeyGen = keyGen.publicKeyGen();
        Vector privateKey = keyGen.privateKeyGen();

        CoordinatorJMSChannel coordinatorJMSChannel = new CoordinatorJMSChannel();
        coordinatorJMSChannel.setPublicKey(publicKeyGen);
        coordinatorJMSChannel.setPrivateKey(privateKey);
        coordinatorJMSChannel.start();

    }

}