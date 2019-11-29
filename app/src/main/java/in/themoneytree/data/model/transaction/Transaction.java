package in.themoneytree.data.model.transaction;

/**
 * Created By  Archit
 * on 11/27/2019
 * for TheMoneyTree
 */
public class Transaction {
    private Integer transactionId;
    private Double transactionAmount;
    private String transactionName;
    private String transactionMethod;
    private String transactionReceiver;
    private String transactionDate;
    private String transactionInfo;

    public Transaction() {
    }

    public Transaction(Integer transactionId, Double transactionAmount, String transactionName,
                       String transactionMethod, String transactionReceiver,
                       String transactionDate, String transactionInfo) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.transactionName = transactionName;
        this.transactionMethod = transactionMethod;
        this.transactionReceiver = transactionReceiver;
        this.transactionDate = transactionDate;
        this.transactionInfo = transactionInfo;
    }

    public String getTransactionMethod() {
        return transactionMethod;
    }

    public void setTransactionMethod(String transactionMethod) {
        this.transactionMethod = transactionMethod;
    }

    public String getTransactionReceiver() {
        return transactionReceiver;
    }

    public void setTransactionReceiver(String transactionReceiver) {
        this.transactionReceiver = transactionReceiver;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(String transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }
}
