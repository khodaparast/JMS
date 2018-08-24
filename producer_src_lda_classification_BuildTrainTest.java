package lda.classification;

import java.io.*;
import java.security.SecureRandom;
import java.util.HashMap;

/**
 * Created by "P.Khodaparast" on 2018-08-19.
 */
public class BuildTrainTest {

    void seprateTrainTest() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File("src/resources/diabetes.csv")));
        BufferedWriter bwTrain = new BufferedWriter(new FileWriter(new File("src/resources/train.csv")));
        BufferedWriter bwTest = new BufferedWriter(new FileWriter(new File("src/resources/test.csv")));
        String line = br.readLine();
        int counter = 0;
        SecureRandom random = new SecureRandom();
//        Random rand = new Random(50);
        HashMap<Integer, Integer> testMap = new HashMap<>();
        int j = 0;
        while (j < 168) {
            int randomVal = random.nextInt(769);
            if (!testMap.containsValue(randomVal)) {
                testMap.put(j, randomVal);
                j = j + 1;
            }

        }


        while ((line = br.readLine()) != null) {
            counter = counter + 1;
            System.out.println(counter + "   " + line);
            if (testMap.containsValue(counter)) {
                bwTest.write(line + "\n");
            } else {
                bwTrain.write(line + "\n");
            }
        }
        bwTest.close();
        bwTrain.close();
        br.close();
    }

    void makePartiesTrainDataset() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File("src/resources/train.csv")));
        BufferedWriter bwP1 = new BufferedWriter(new FileWriter(new File("src/resources/P1.csv")));
        BufferedWriter bwP2 = new BufferedWriter(new FileWriter(new File("src/resources/P2.csv")));
        BufferedWriter bwP3 = new BufferedWriter(new FileWriter(new File("src/resources/P3.csv")));
        String line = br.readLine();
        int counter = 0;
        SecureRandom random = new SecureRandom();
//        Random rand = new Random(50);
        HashMap<Integer, Integer> testMap = new HashMap<>();
        for (int i = 1; i < 200; i++) {
            line = br.readLine();
            bwP1.write(line+"\n");
        }

        for (int i = 200; i < 400; i++) {
            line = br.readLine();
            bwP2.write(line+"\n");
        }

        for (int i = 400; i < 600; i++) {
            line = br.readLine();
            bwP3.write(line+"\n");
        }

        br.close();
        bwP1.close();
        bwP2.close();
        bwP3.close();
    }

    public static void main(String[] args) throws Exception {
        new BuildTrainTest().makePartiesTrainDataset();
    }
}
