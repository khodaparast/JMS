package lda.classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by "P.Khodaparast" on 2018-08-18.
 */
public class Train {



    public static void main(String[] args) throws Exception {
//        BufferedReader br=new BufferedReader(new FileReader(new File("src/resources/diabetes.csv")));
//        String line=br.readLine();
//        ArrayList<Double> pregnancyHistory = new ArrayList<>();
//        ArrayList<Double> plasma =new ArrayList<>();
//        ArrayList<Double> bloodPressure = new ArrayList<>();
//        ArrayList<Double> skinThickness = new ArrayList<>();
//        ArrayList<Double> insulin = new ArrayList<>();
//        ArrayList<Double> bodyMass = new ArrayList<>();
//        ArrayList<Double> pedigree = new ArrayList<>();
//        ArrayList<Double> age = new ArrayList<>();
//        ArrayList<Double> classLable = new ArrayList<>();
//        while ((line=br.readLine())!= null){
//
//            String[] attributes=line.split(",");
//            pregnancyHistory.add(Double.valueOf(attributes[0]));
//            plasma.add(Double.valueOf(attributes[1]));
//            bloodPressure.add(Double.valueOf(attributes[2]));
//            skinThickness.add(Double.valueOf(attributes[3]));
//            insulin.add(Double.valueOf(attributes[4]));
//            bodyMass.add(Double.valueOf(attributes[5]));
//            pedigree.add(Double.valueOf(attributes[6]));
//            age.add(Double.valueOf(attributes[7]));
//            classLable.add(Double.valueOf(attributes[8]));
//        }
//       new  Train().prepareDataSet();
        ArrayList<Double> t=new ArrayList<>();
        LDAUtils ldaUtils=new LDAUtils();
        // Mean
        List<Double> Temp_Y = new ArrayList<Double>(Arrays.asList(83.0, 70.0, 68.0, 64.0, 69.0, 75.0, 75.0, 72.0, 81.0));
        List<Double> Hum_Y = new ArrayList<Double>(Arrays.asList(78.0, 96.0, 80.0, 65.0, 70.0, 80.0, 70.0, 90.0, 75.0));
        List<Double> Temp_N = new ArrayList<Double>(Arrays.asList(85.0, 80.0, 65.0, 72.0, 71.0));
        List<Double> Hum_N = new ArrayList<Double>(Arrays.asList(85.0, 90.0, 70.0, 95.0, 80.0));
        double mty=ldaUtils.getMean((ArrayList<Double>) Temp_Y)*100;
        double mtn=ldaUtils.getMean((ArrayList<Double>) Temp_N)*100;
        double mhy=ldaUtils.getMean((ArrayList<Double>) Hum_Y)*100;
        double mhn= ldaUtils.getMean((ArrayList<Double>) Hum_N)*100;
        int mu_Temp_Y= (int) mty;
        int mu_Temp_N= (int) mtn;
        int mu_Hum_Y= (int) mhy;
        int mu_Hum_N= (int)mhn;
        System.out.println(mty+" "+mhy+" "+mtn+" "+mhn);
        System.out.println(mu_Temp_Y+" "+mu_Hum_Y+" "+mu_Temp_N+" "+mu_Hum_N);
//        System.out.println(ldaUtils.getCovariance((ArrayList<Double>) Temp_Y, (ArrayList<Double>) Temp_Y));
//        System.out.println(ldaUtils.getCovariance((ArrayList<Double>) pregnancyHistory, (ArrayList<Double>) plasma));
    }
}
