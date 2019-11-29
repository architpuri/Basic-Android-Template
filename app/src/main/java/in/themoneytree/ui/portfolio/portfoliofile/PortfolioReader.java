package in.themoneytree.ui.portfolio.portfoliofile;

/**
 * Created By  Archit
 * on 11/27/2019
 * for TheMoneyTree
 */

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import in.themoneytree.data.model.incomestreams.IncomeStream;
import in.themoneytree.data.model.investments.Investment;
import in.themoneytree.data.model.transaction.Transaction;

public class PortfolioReader {
    private static JSONArray incomeStreams;
    private static JSONArray investments;
    private static JSONArray transactions;
    private final static String TAG = "PORTFOLIO_READER";

    public static void readPortfolio(String filePath) {
        JSONParser jsonParser = new JSONParser();
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("File Exists - " + filePath);
        } else {
            System.out.println("File Khaan hai Bhai? - " + filePath);
        }
        try (FileReader reader = new FileReader(file)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray portfolio = (JSONArray) obj;
            System.out.println("PORTFOLIO IS "+portfolio.toString());

            //Iterate over employee array
            //employeeList.forEach(emp -> parseEmployeeObject((JSONObject) emp));
            int counter = 1;
            for (Object component : portfolio) {
                Log.d(TAG,counter +" "+component.toString());
                parseObject((JSONArray) component, counter);
                counter++;
            }
            reader.close();
            printStuff();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void printStuff() {
        System.out.println(investments.toString());
    }

    private static void parseObject(JSONArray component, int counter) {
        switch (counter) {
            case 1:
                incomeStreams = component;
                break;
            case 2:
                investments = component;
                break;
            case 3:
                transactions = component;
                break;
            default:
                break;
        }
    }

    public static List<IncomeStream> getIncomeStreams() {
        int counter = 1;
        List<IncomeStream> streams = new ArrayList<IncomeStream>();
        if (incomeStreams == null) {
            return null;
        }
        for (Object component : incomeStreams) {
            JSONObject obj = (JSONObject) component;
            if (!obj.isEmpty()) {
                IncomeStream incomeStream = new IncomeStream(counter, 0,
                        (String) (obj.get("streamName").toString()),
                        Double.parseDouble(obj.get("streamAmount").toString()),
                        Integer.parseInt(obj.get("streamFrequency").toString()), obj.get("streamInfo").toString(),
                        Integer.parseInt(obj.get("streamType").toString()));
                streams.add(incomeStream);
                counter++;
            }
        }
        return streams;
    }

    public static List<Investment> getInvestments() {
        List<Investment> invests = new ArrayList<Investment>();
        if (investments == null) {
            return null;
        }
        for (Object component : investments) {
            JSONObject obj = (JSONObject) component;
            if (!obj.isEmpty()) {
                Investment investment = new Investment(Integer.parseInt(obj.get("investmentId").toString()),
                        (String) obj.get("investmentName").toString(), Double.parseDouble(obj.get("principalAmount").toString()),
                        obj.get("expiryDate").toString(), Double.parseDouble(obj.get("interestRate").toString()),
                        Integer.parseInt(obj.get("category").toString()), obj.get("extraInfo").toString());
                invests.add(investment);
            }
        }
        return invests;
    }

    public static List<Transaction> getTransactions() {
        List<Transaction> transacts = new ArrayList<Transaction>();
        if (transactions == null) {
            return null;
        }
        for (Object component : transactions) {
            JSONObject obj = (JSONObject) component;
            if (!obj.isEmpty()) {
                Transaction transact = new Transaction(Integer.parseInt(obj.get("transactionId").toString()),
                        Double.parseDouble(obj.get("transactionAmount").toString()), (String) obj.get("transactionName").toString(),
                        obj.get("transactionMethod").toString(), obj.get("transactionReceiver").toString(),
                        obj.get("transactionDate").toString(), obj.get("transactionInfo").toString());
                transacts.add(transact);
            }
        }
        return transacts;
    }

}

