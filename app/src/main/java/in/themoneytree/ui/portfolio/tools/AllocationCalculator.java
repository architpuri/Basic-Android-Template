package in.themoneytree.ui.portfolio.tools;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.themoneytree.data.model.investments.Investment;

/**
 * Created By  Archit
 * on 11/28/2019
 * for TheMoneyTree
 */
public class AllocationCalculator {
    public static Pair<ArrayList<String>,ArrayList<Double>> getPortfolioAllocation(List<Investment> investments){
        double totalAmount =0.0;
        if(investments==null || investments.size()<=0){
            return null;
        }else{
            HashMap<String,Double> hm = new HashMap<>();
            for(Investment invest : investments){
                String instrumentName = getInstrumentList().get(invest.getCategory());
                Double amount = invest.getPrincipalAmount();
                totalAmount = totalAmount+amount;
                if(hm.get(instrumentName)!=null){
                    hm.put(instrumentName,hm.get(instrumentName)+amount);
                }else{
                    hm.put(instrumentName,amount);
                }
            }
            ArrayList<Double> values = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            for(Map.Entry<String,Double> m : hm.entrySet()){
                labels.add((String)m.getKey());
                values.add(returnChartForm((Double)m.getValue(),totalAmount));
            }
            Pair<ArrayList<String>,ArrayList<Double>> allocation = new Pair<>(labels,values);
            return allocation;
        }

    }

    private static Double returnChartForm(Double a,Double total){
        Double result = (a/total)*100;
        return result;
    }
    private static List<String> getInstrumentList() {
        List<String> labels = new ArrayList<>();
        labels.add("F.D.");
        labels.add("Mutual Fund");
        labels.add("Cash");
        labels.add("PPF");
        labels.add("Stocks");
        labels.add("RD");
        labels.add("Metals");
        labels.add("Property");
        return labels;
    }

    public static Double getTotalInvestment(List<Investment> investments){
        Double totalAmount=0.0;
        for(Investment investment : investments){
            totalAmount = totalAmount+investment.getPrincipalAmount();
        }
        return totalAmount;
    }

}
