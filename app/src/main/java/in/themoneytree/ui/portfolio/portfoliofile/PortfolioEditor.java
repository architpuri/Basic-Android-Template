package in.themoneytree.ui.portfolio.portfoliofile;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import in.themoneytree.data.model.incomestreams.IncomeStream;
import in.themoneytree.data.model.investments.Investment;
import in.themoneytree.data.model.transaction.Transaction;

/**
 * Created By  Archit
 * on 11/27/2019
 * for TheMoneyTree
 */
public class PortfolioEditor {
    private static JSONArray incomeStreams;
    private static JSONArray investments;
    private static JSONArray transactions;
    private final static String TAG = "PORTFOLIO_EDITOR";

    private static void readPortfolio(String filePath) {
        JSONParser jsonParser = new JSONParser();
        File file = new File(filePath);
        try (FileReader reader = new FileReader(file)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray portfolio = (JSONArray) obj;
            System.out.println(portfolio);

            //Iterate over employee array
            //employeeList.forEach(emp -> parseEmployeeObject((JSONObject) emp));
            int counter = 1;
            for (Object component : portfolio) {
                parseObject((JSONArray) component, counter);
                counter++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void addIncomeStream(String filePath, IncomeStream stream) {
        readPortfolio(filePath);
        JSONObject incomeStream = new JSONObject();
        incomeStream.put("streamId",stream.getStreamId());
        incomeStream.put("streamName", stream.getName());
        incomeStream.put("streamAmount", stream.getAmount());
        incomeStream.put("streamFrequency", stream.getFrequency());
        incomeStream.put("streamInfo", stream.getStreamInfo());
        incomeStream.put("streamType", stream.getStreamType());
        incomeStreams.add(incomeStream);
        savePortfolio(filePath);
    }

    public static void addInvestment(String filePath, Investment investment) {
        readPortfolio(filePath);
        JSONObject invest = new JSONObject();
        invest.put("investmentName", investment.getInvestmentName());
        invest.put("investmentId", investment.getInvestmentId());
        invest.put("principalAmount", investment.getPrincipalAmount());
        invest.put("expiryDate", investment.getExpiryDate());
        invest.put("interestRate", investment.getInterestRate());
        invest.put("category", investment.getCategory());
        invest.put("extraInfo", investment.getExtraInfo());
        investments.add(invest);
        savePortfolio(filePath);
    }

    public static void addTransaction(String filePath, Transaction transaction) {
        readPortfolio(filePath);
        JSONObject transact = new JSONObject();
        transact.put("transactionName", transaction.getTransactionName());
        transact.put("transactionId", transaction.getTransactionId());
        transact.put("transactionAmount", transaction.getTransactionAmount());
        transact.put("transactionMethod", transaction.getTransactionMethod());
        transact.put("transactionReceiver", transaction.getTransactionReceiver());
        transact.put("transactionDate", transaction.getTransactionDate());
        transact.put("transactionInfo", transaction.getTransactionInfo());
        transactions.add(transact);
        savePortfolio(filePath);
    }

    private static void savePortfolio(String filePath) {
        JSONArray portfolioData = new JSONArray();
        portfolioData.add(incomeStreams);
        portfolioData.add(investments);
        portfolioData.add(transactions);
        File fi = new File(filePath);
        /*if (!fi.exists()) {
            try {
                fi.createNewFile();
            } catch (IOException e) {
                Log.d(TAG,"BNANE SE PEHLE PNGA");
                e.printStackTrace();
            }
        }*/

        try (FileWriter file = new FileWriter(fi)) {
            file.write(portfolioData.toJSONString());
            file.flush();
        } catch (IOException e) {
            Log.d(TAG, "BNANE K BAAD PNGA");
            e.printStackTrace();
        }
    }

    public static void parseObject(JSONArray component, int counter) {
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

    public static boolean removeInvestment(String filePath,Integer investmentId){
        readPortfolio(filePath);
        int index =0;
        boolean isFound=false;
        for(Object stream : investments){
            JSONObject obj = (JSONObject) stream;
            if(!obj.isEmpty()){
                try {
                    if (Integer.parseInt(obj.get("investmentId").toString()) == investmentId) {
                        isFound = true;
                        break;
                    }
                }catch(NullPointerException n){

                }
            }
            index++;
        }
        if(isFound) {
            investments.remove(index);
            savePortfolio(filePath);
            return true;
        }
        return false;
    }

    public static boolean removeTransaction(String filePath,Integer transactionId){
        readPortfolio(filePath);
        int index =0;
        boolean isFound=false;
        for(Object stream : transactions){
            JSONObject obj = (JSONObject) stream;
            if(!obj.isEmpty()){
                try {
                    if (Integer.parseInt(obj.get("transactionId").toString()) == transactionId) {
                        isFound = true;
                        break;
                    }
                }catch(NullPointerException n){

                }
            }
            index++;
        }
        if(isFound) {
            transactions.remove(index);
            savePortfolio(filePath);
            return true;
        }
        return false;
    }

    public static boolean removeIncomeStream(String filePath,Integer streamId){
        readPortfolio(filePath);
        int index =0;
        boolean isFound=false;
        for(Object stream : incomeStreams){
            JSONObject obj = (JSONObject) stream;
            if(!obj.isEmpty()){
                try {
                    if (Integer.parseInt(obj.get("streamId").toString()) == streamId) {
                        isFound = true;
                        break;
                    }
                }catch(NullPointerException n){

                }
            }
            index++;
        }
        if(isFound) {
            incomeStreams.remove(index);
            savePortfolio(filePath);
            return true;
        }
        return false;
    }
}
