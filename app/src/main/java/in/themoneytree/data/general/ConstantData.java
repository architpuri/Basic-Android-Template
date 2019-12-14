package in.themoneytree.data.general;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created By  Archit
 * on 7/7/2019
 * for TheMoneyTree
 */
public class ConstantData {

    public static LinkedHashMap<String, Float> getTaxSlabs() {
        LinkedHashMap<String, Float> taxSlabs = new LinkedHashMap<>();
        taxSlabs.put("Upto 5L", 0.0f);
        taxSlabs.put("5L - 10L", 20.8f);
        taxSlabs.put("10L - 50L", 31.2f);
        taxSlabs.put("50L - 1cr", 34.3f);
        taxSlabs.put("1cr - 2cr", 35.9f);
        taxSlabs.put("2cr - 5cr", 39.0f);
        taxSlabs.put("Over 5cr", 42.7f);
        return taxSlabs;
    }

    public static LinkedHashMap<Double, Double> getTaxSlabsDouble() {
        LinkedHashMap<Double, Double> taxSlabs = new LinkedHashMap<>();
        taxSlabs.put(500000.0, 20.8);
        taxSlabs.put(1000000.0, 31.2);
        taxSlabs.put(5000000.0, 34.3);
        taxSlabs.put(10000000.0, 35.9);
        taxSlabs.put(30000000.0, 39.0);
        taxSlabs.put(800000000.0, 42.7);//minus remaining
        return taxSlabs;
    }

    public static HashMap<Integer, Double> getSpendingChange(int expectedAge) {
        //Change in spennding values 0,2,3 changed to 1,2,3 for ease
        //data from newspaper surveys
        HashMap<Integer, Double> hm = new HashMap<>();
        switch (expectedAge) {
            case 10:
                hm.put(0, 8.37);
                hm.put(1, 9.11);
                hm.put(2, 9.50);
                break;
            case 15:
                hm.put(0, 11.71);
                hm.put(1, 13.29);
                hm.put(2, 14.13);
                break;
            case 20:
                hm.put(0, 14.57);
                hm.put(1, 17.23);
                hm.put(2, 18.75);
                break;
            case 25:
                hm.put(0, 17.03);
                hm.put(1, 20.92);
                hm.put(2, 23.28);
                break;
            default:
                hm.put(0, 14.57);
                hm.put(1, 17.23);
                hm.put(2, 18.75);
        }
        return hm;
    }

}
