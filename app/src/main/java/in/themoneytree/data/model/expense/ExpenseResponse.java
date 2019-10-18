package in.themoneytree.data.model.expense;

import in.themoneytree.data.model.GeneralResponse;

/**
 * Created By  Archit
 * on 10/17/2019
 * for TheMoneyTree
 */

public class ExpenseResponse {
    private GeneralResponse generalResponse;
    private Expense expense;

    public ExpenseResponse() {
    }

    public ExpenseResponse(Expense expense, int statusCode, String message) {
        this.generalResponse = new GeneralResponse(statusCode, message);
        this.expense = expense;
    }

    public Expense getexpense() {
        return expense;
    }

    public void setexpense(Expense expense) {
        this.expense = expense;
    }

    public GeneralResponse getGeneralResponse() {
        return generalResponse;
    }

    public void setGeneralResponse(GeneralResponse generalResponse) {
        this.generalResponse = generalResponse;
    }
}

