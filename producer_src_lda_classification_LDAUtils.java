package lda.classification;

import java.util.ArrayList;

/**
 * Created by "P.Khodaparast" on 2018-08-08.
 */
public class LDAUtils {

    public double getMean(ArrayList<Double> values) {
        double result = 0;
        for (int i = 0; i < values.size(); i++) {
            result += values.get(i);
        }
        result = result / values.size();

        int precision = 100; //keep 2 digits
        result = Math.floor(result * precision + .5) / precision;
        return result;
    }

    public double getMean(int[] values) {
        double result = 0;
        for (int i = 0; i < values.length; i++) {
            result += values[i];
        }
        result = result / values.length;

        int precision = 100; //keep 2 digits
        result = Math.floor(result * precision + .5) / precision;
        return result;
    }


    public double getCovariance(int[] val1, int[] val2) {
        LDAUtils ldaUtils = new LDAUtils();

        double covariance = 0;
        if (val1.length != val2.length) {
            covariance = -1;
            return covariance;
        }

        double avg_val1 = ldaUtils.getMean(val1);
        double avg_val2 = ldaUtils.getMean(val2);
        double sum = 0;
        for (int i = 0; i < val1.length; i++) {
            sum += (val1[i] - avg_val1) * (val2[i] - avg_val2);

        }
        sum = sum / val1.length;
        int precision = 100; //keep 2 digits
        sum = Math.floor(sum * precision + .5) / precision;
        return sum;
    }

    public double getCovariance(ArrayList<Double> val1, ArrayList<Double> val2) {
        LDAUtils ldaUtils = new LDAUtils();

        double covariance = 0;
        if (val1.size() != val2.size()) {
            covariance = -1;
            return covariance;
        }

        double avg_val1 = ldaUtils.getMean(val1);
        double avg_val2 = ldaUtils.getMean(val2);
        double sum = 0;
        for (int i = 0; i < val1.size(); i++) {
            sum += (val1.get(i) - avg_val1) * (val2.get(i) - avg_val2);

        }
        sum = sum / val1.size();
        int precision = 100; //keep 2 digits
        sum = Math.floor(sum * precision + .5) / precision;
        return sum;
    }


    public double[] getTotalCovariance(double[] cov_Y, double[] cov_N, double n_Y, double n_N) {
        double[] totalCov = new double[3];
        double zaribY = (n_Y / (n_Y + n_N));
        double zaribN = (n_N / (n_Y + n_N));
        int precision = 100; //keep 2 digits

        for (int i = 0; i < cov_Y.length; i++) {
//            System.out.println(cov_Y[i]+" "+cov_N[i]);
            totalCov[i] = (zaribY * cov_Y[i]) + (zaribN * cov_N[i]);
            totalCov[i] = Math.floor(totalCov[i] * precision + .5) / precision;

//            System.out.println(totalCov[i]);
        }

        return totalCov;
    }

    public double[] getInverseCov(double[] totalCovariance) {
        double[] inverseCov = new double[3];
        int precision = 10000; //keep 4 digits

        double zarib = 1 / ((totalCovariance[0] * totalCovariance[2]) - (totalCovariance[1] * totalCovariance[1]));
        for (int i = 0; i < totalCovariance.length; i++) {
            inverseCov[i] = zarib * totalCovariance[i];
            inverseCov[i] = Math.floor(inverseCov[i] * precision + .5) / precision;
//To Do. the result should be reversed!!!!!!!!!!!!!!!!!! based on formula
        }
        double temp = inverseCov[0];
        inverseCov[0] = inverseCov[2];
        inverseCov[2] = temp;
        inverseCov[1] = -inverseCov[1];
        return inverseCov;
    }

    public double[] getBetas(double[] inversedCov, double[] muOfAttributeInEachClass) {
        double[] betas = new double[2];
        double mu_x1y = muOfAttributeInEachClass[0];
        double mu_x1n = muOfAttributeInEachClass[1];
        double mu_x2y = muOfAttributeInEachClass[2];
        double mu_x2n = muOfAttributeInEachClass[3];
        double b1 = inversedCov[0] * (mu_x1y - mu_x1y) + inversedCov[1] * (mu_x2y - mu_x2n);
        double b2 = inversedCov[1] * (mu_x1y - mu_x1y) + inversedCov[2] * (mu_x2y - mu_x2n);
        betas[0] = b1;
        betas[1] = b2;
        return betas;
    }

    public double[] getZs(double[] betas, double[] muOfAttributeInEachClass) {
        double[] results = new double[3];
        double b1 = betas[0];
        double b2 = betas[1];
        double mu_x1y = muOfAttributeInEachClass[0];
        double mu_x1n = muOfAttributeInEachClass[1];
        double mu_x2y = muOfAttributeInEachClass[2];
        double mu_x2n = muOfAttributeInEachClass[3];

        double Z_0 = b1 * ((mu_x1y + mu_x1n) / 2) + b2 * ((mu_x2y + mu_x2n) / 2);
        double Z_Y = b1 * mu_x1y + b2 * mu_x2y;
        double Z_N = b1 * mu_x1n + b2 * mu_x2n;
        results[0] = Z_0;
        results[1] = Z_Y;
        results[2] = Z_N;
        return results;
    }

    public String classify(double[] newInstanse, double[] Zs, double[] betas) {
        String classLable = null;
        double Z_0 = Zs[0];
        double Z_Y = Zs[1];
        double Z_N = Zs[2];
        double b1 = betas[0];
        double b2 = betas[1];
        double Z = b1 * newInstanse[0] + b2 * newInstanse[1];

        if (Z_Y > Z_N && Z > Z_0) {
            System.out.println("(Z_Y > Z_N && Z > Z_0)");
            return "Yes";
        }
        if (Z_Y <= Z_N && Z > Z_0){
            System.out.println("(Z_Y <= Z_N && Z > Z_0)");
            return "No";
        }

        if (Z_Y > Z_N && Z < Z_0) {
            System.out.println("(Z_Y > Z_N && Z < Z_0)");
            return "No";
        }


        if (Z_Y <= Z_N && Z < Z_0) {
            System.out.println("(Z_Y <= Z_N && Z < Z_0)");
            return "Yes";
        }
        else return "UnKnown";
    }

    public static void main(String[] args) {
        LDAUtils ldaUtils = new LDAUtils();

        // Mean
        int[] Temp_Y = new int[]{83, 70, 68, 64, 69, 75, 75, 72, 81};
        int[] Hum_Y = new int[]{78, 96, 80, 65, 70, 80, 70, 90, 75};
        int[] Temp_N = new int[]{85, 80, 65, 72, 71};
        int[] Hum_N = new int[]{85, 90, 70, 95, 80};
        double mu_Temp_Y = ldaUtils.getMean(Temp_Y);
        double mu_Temp_N = ldaUtils.getMean(Temp_N);
        double mu_Hum_Y = ldaUtils.getMean(Hum_Y);
        double mu_Hum_N = ldaUtils.getMean(Hum_N);
        double[] muOfAttributeInEachClass = new double[4];
        muOfAttributeInEachClass[0] = mu_Temp_Y;
        muOfAttributeInEachClass[1] = mu_Temp_N;
        muOfAttributeInEachClass[2] = mu_Hum_Y;
        muOfAttributeInEachClass[3] = mu_Hum_N;

        // Covariance
        double[] cov_Y = new double[]{ldaUtils.getCovariance(Temp_Y, Temp_Y),
                ldaUtils.getCovariance(Temp_Y, Hum_Y),
                ldaUtils.getCovariance(Hum_Y, Hum_Y)
        };

        double[] cov_N = new double[]{ldaUtils.getCovariance(Temp_N, Temp_N),
                ldaUtils.getCovariance(Temp_N, Hum_N),
                ldaUtils.getCovariance(Hum_N, Hum_N)
        };
//        System.out.println(cov_Y[0]+" "+ cov_Y[1]+" "+cov_Y[2]);
//        System.out.println(cov_N[0]+" "+ cov_N[1]+" "+cov_N[2]);


        double[] totalC = ldaUtils.getTotalCovariance(cov_Y, cov_N, 9, 5);
//        System.out.println(totalC[0]+" "+totalC[1]+" "+totalC[2]);


        double[] inverseOfTotalCov = ldaUtils.getInverseCov(totalC);
//        System.out.println(inverseOfTotalCov[0] + " " + inverseOfTotalCov[1] + " " + inverseOfTotalCov[2]);

        double[] betas= ldaUtils.getBetas(inverseOfTotalCov, muOfAttributeInEachClass);
//        System.out.println(" betas: "+betas[0]+" "+ betas[1]);
        double[] Zs= ldaUtils.getZs(betas, muOfAttributeInEachClass);
        System.out.println("z: "+Zs[0]+" "+Zs[1]+" "+Zs[2]);
        double[] newInstance=new double[]{84, 86};
        String classify= ldaUtils.classify(newInstance, Zs, betas);
        System.out.println(classify);
    }


}
