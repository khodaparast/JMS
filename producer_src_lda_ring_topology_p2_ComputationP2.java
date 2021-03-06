package lda.ring_topology.p2;

import lda.classification.LDAUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by "P.Khodaparast" on 2018-08-20.
 */
public class ComputationP2 {
    LDAUtils ldaUtils=new LDAUtils();

    ArrayList<BigInteger> getAttributesMean(){
        List<Double> Temp_Y = new ArrayList<Double>(Arrays.asList(64.0, 69.0, 75.0));
        List<Double> Hum_Y = new ArrayList<Double>(Arrays.asList( 65.0, 70.0, 80.0));

        double mu_Temp_Y = ldaUtils.getMean((ArrayList<Double>) Temp_Y) ;
        double mu_Hum_Y = ldaUtils.getMean((ArrayList<Double>) Hum_Y) ;
        int mty = (int) Math.ceil( mu_Temp_Y);
        int mhy = (int) Math.ceil( mu_Hum_Y);
        BigInteger meanTempY = BigInteger.valueOf(mty);
        BigInteger meanHumY = BigInteger.valueOf(mhy);
        ArrayList<BigInteger> result=new ArrayList<>();
        result.add(meanTempY);
        result.add(meanHumY);

        return result;
    }


}
