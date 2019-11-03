package in.themoneytree.data.model.expense;

public class Expense {
    private Integer expenseId;
    private String expenseName;
    private Double expenseAmount;
    private Integer userId;
    private Integer paymentType;//how you spent Cash,Bank Account
    private Integer paymentSourceId;//PaymentId and Its Details

    public Expense() {
    }

    public Expense(Integer expenseId, String expenseName, Double expenseAmount, Integer userId, Integer paymentType, Integer paymentSourceId) {
        this.expenseId = expenseId;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.userId = userId;
        this.paymentType = paymentType;
        this.paymentSourceId = paymentSourceId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getPaymentSourceId() {
        return paymentSourceId;
    }

    public void setPaymentSourceId(Integer paymentSourceId) {
        this.paymentSourceId = paymentSourceId;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
