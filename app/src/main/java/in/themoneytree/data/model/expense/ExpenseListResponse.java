package in.themoneytree.data.model.expense;

/**
 * Created By  Archit
 * on 10/17/2019
 * for TheMoneyTree
 */
import java.util.List;

import in.themoneytree.data.model.GeneralResponse;

public class ExpenseListResponse {
    private GeneralResponse generalResponse;
    private List<Expense> expenses;

    protected ExpenseListResponse() {
    }

    public ExpenseListResponse(List<Expense> expenses, int statusCode, String message) {
        this.expenses = expenses;
        this.generalResponse=new GeneralResponse(statusCode,message);
    }
    public List<Expense> getExpenses() {
        return expenses;
    }
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
    public GeneralResponse getGeneralResponse() {
        return generalResponse;
    }
    public void setGeneralResponse(GeneralResponse generalResponse) {
        this.generalResponse = generalResponse;
    }
}