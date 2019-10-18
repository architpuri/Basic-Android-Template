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

    public static HashMap<Integer, Float> getSpendingChange(int expectedAge) {
        //Change in spennding values 0,2,3 changed to 1,2,3 for ease
        //data from newspaper surveys
        HashMap<Integer, Float> hm = new HashMap<>();
        switch (expectedAge) {
            case 10:
                hm.put(0, 8.37f);
                hm.put(1, 9.11f);
                hm.put(2, 9.50f);
                break;
            case 15:
                hm.put(0, 11.71f);
                hm.put(1, 13.29f);
                hm.put(2, 14.13f);
                break;
            case 20:
                hm.put(0, 14.57f);
                hm.put(1, 17.23f);
                hm.put(2, 18.75f);
                break;
            case 25:
                hm.put(0, 17.03f);
                hm.put(1, 20.92f);
                hm.put(2, 23.28f);
                break;
            default:
                hm.put(0, 14.57f);
                hm.put(1, 17.23f);
                hm.put(2, 18.75f);
        }
        return hm;
    }

}
