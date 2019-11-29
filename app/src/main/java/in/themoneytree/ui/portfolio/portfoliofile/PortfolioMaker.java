package in.themoneytree.ui.portfolio.portfoliofile;


import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

/*import org.json.JSONObject;*/
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/*
import org.json.JSONObject;
*/


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created By  Archit
 * on 11/27/2019
 * for TheMoneyTree
 */
public class PortfolioMaker {
    public static void makeNewFile(String filePath) throws Exception {
        File fi = new File(filePath);
        if (!fi.exists()) {

            try {
                fi.createNewFile();
            } catch (IOException e) {
                Log.d("PORTFOLIO_MAKER", "BNANE SE PEHLE PNGA");
                e.printStackTrace();
            }

            try (FileWriter file = new FileWriter(fi)) {
                file.write(makeJsonArray().toJSONString());
                file.flush();
            } catch (IOException e) {
                Log.d("PORTFOLIO_MAKER", "BNANE K BAAD PNGA");
                e.printStackTrace();
            }
        }
    }

    private static JSONArray makeJsonArray() {
        JSONArray streams = new JSONArray();
        JSONObject incomeStream = new JSONObject();
        streams.add(incomeStream);
        JSONArray investments = new JSONArray();
        JSONObject investment = new JSONObject();
        investments.add(investment);
        JSONArray transactions = new JSONArray();
        JSONObject transact = new JSONObject();
        transactions.add(transact);
        JSONArray finalArray = new JSONArray();
        finalArray.add(streams);
        finalArray.add(investments);
        finalArray.add(transactions);
        return finalArray;
    }

}
